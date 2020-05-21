/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRendererRegistry.class)
public class CTDisplayRendererRegistry {

	public <T extends BaseModel<T>> T fetchCTModel(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		CTService<?> ctService = _ctServiceServiceTrackerMap.getService(
			modelClassNameId);

		if (ctService == null) {
			return null;
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			return (T)ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKey(modelClassPK));
		}
	}

	public <T extends CTModel<T>> String getEditURL(
		HttpServletRequest httpServletRequest, CTEntry ctEntry) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(
				ctEntry.getModelClassNameId());

		if (ctDisplayRenderer == null) {
			return null;
		}

		T ctModel = fetchCTModel(
			ctEntry.getCtCollectionId(), ctEntry.getModelClassNameId(),
			ctEntry.getModelClassPK());

		if (ctModel == null) {
			return null;
		}

		try {
			return ctDisplayRenderer.getEditURL(httpServletRequest, ctModel);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	public String getEntryDescription(
		HttpServletRequest httpServletRequest, CTEntry ctEntry) {

		String languageKey = "x-modified-a-x-x-ago";

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_ADDITION) {
			languageKey = "x-added-a-x-x-ago";
		}
		else if (ctEntry.getChangeType() ==
					CTConstants.CT_CHANGE_TYPE_DELETION) {

			languageKey = "x-deleted-a-x-x-ago";
		}

		Date modifiedDate = ctEntry.getModifiedDate();

		return _language.format(
			httpServletRequest, languageKey,
			new Object[] {
				ctEntry.getUserName(),
				getTypeName(
					httpServletRequest.getLocale(),
					ctEntry.getModelClassNameId()),
				_language.getTimeDescription(
					httpServletRequest.getLocale(),
					System.currentTimeMillis() - modifiedDate.getTime(), true)
			},
			false);
	}

	public <T extends BaseModel<T>> String getTitle(
		CTEntry ctEntry, Locale locale) {

		long ctCollectionId = ctEntry.getCtCollectionId();

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) {
			ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
		}

		T model = fetchCTModel(
			ctCollectionId, ctEntry.getModelClassNameId(),
			ctEntry.getModelClassPK());

		if (model == null) {
			return StringBundler.concat(
				getTypeName(locale, ctEntry.getModelClassNameId()),
				StringPool.SPACE, model.getPrimaryKeyObj());
		}

		return getTitle(locale, model, ctEntry.getModelClassNameId());
	}

	public <T extends BaseModel<T>> String getTitle(
		Locale locale, T model, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(modelClassNameId);

		String name = null;

		if (ctDisplayRenderer != null) {
			try {
				name = ctDisplayRenderer.getTitle(locale, model);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}

				String typeName = ctDisplayRenderer.getTypeName(locale);

				if (Validator.isNotNull(typeName)) {
					return StringBundler.concat(
						typeName, StringPool.SPACE, model.getPrimaryKeyObj());
				}
			}
		}

		if (Validator.isNotNull(name)) {
			return name;
		}

		return StringBundler.concat(
			getTypeName(locale, modelClassNameId), StringPool.SPACE,
			model.getPrimaryKeyObj());
	}

	public <T extends BaseModel<T>> String getTypeName(
		Locale locale, long modelClassNameId) {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(modelClassNameId);

		String name = null;

		if (ctDisplayRenderer != null) {
			name = ctDisplayRenderer.getTypeName(locale);
		}

		if (Validator.isNull(name)) {
			ClassName className = _classNameLocalService.fetchClassName(
				modelClassNameId);

			if (className != null) {
				name = _resourceActions.getModelResource(
					locale, className.getClassName());

				if (name.startsWith(
						_resourceActions.getModelResourceNamePrefix())) {

					name = className.getClassName();
				}
			}
		}

		return name;
	}

	public String getViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, CTEntry ctEntry,
		boolean viewDiff) {

		String title = getEntryDescription(
			liferayPortletRequest.getHttpServletRequest(), ctEntry);

		if (!viewDiff) {
			return getViewURL(
				liferayPortletResponse, ctEntry.getCtCollectionId(),
				ctEntry.getModelClassNameId(), ctEntry.getModelClassPK(),
				title);
		}

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/change_lists/view_diff");
		portletURL.setParameter(
			"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		return StringBundler.concat(
			"javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: ",
			"true}, title: '", HtmlUtil.escapeJS(title), "', uri: '",
			portletURL.toString(), "'});");
	}

	public String getViewURL(
		LiferayPortletResponse liferayPortletResponse, long ctCollectionId,
		long modelClassNameId, long modelClassPK, String title) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/change_lists/view_entry");
		portletURL.setParameter(
			"ctCollectionId", String.valueOf(ctCollectionId));
		portletURL.setParameter(
			"modelClassNameId", String.valueOf(modelClassNameId));
		portletURL.setParameter("modelClassPK", String.valueOf(modelClassPK));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		return StringBundler.concat(
			"javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: ",
			"true}, title: '", HtmlUtil.escapeJS(title), "', uri: '",
			portletURL.toString(), "'});");
	}

	public <T extends BaseModel<T>> void renderCTEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ctCollectionId,
			CTEntry ctEntry)
		throws Exception {

		T model = fetchCTModel(
			ctCollectionId, ctEntry.getModelClassNameId(),
			ctEntry.getModelClassPK());

		if (model == null) {
			return;
		}

		renderCTEntry(
			httpServletRequest, httpServletResponse, model,
			ctEntry.getModelClassNameId());
	}

	public <T extends BaseModel<T>> void renderCTEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T model,
			long modelClassNameId)
		throws Exception {

		CTDisplayRenderer<T> ctDisplayRenderer =
			_ctDisplayServiceTrackerMap.getService(modelClassNameId);

		if (ctDisplayRenderer == null) {
			ctDisplayRenderer = CTModelDisplayRendererAdapter.getInstance();

			ctDisplayRenderer.render(
				httpServletRequest, httpServletResponse, model);

			return;
		}

		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {
			PipingServletResponse pipingServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			ctDisplayRenderer.render(
				httpServletRequest, pipingServletResponse, model);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(httpServletResponse.getWriter());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			ctDisplayRenderer = CTModelDisplayRendererAdapter.getInstance();

			ctDisplayRenderer.render(
				httpServletRequest, httpServletResponse, model);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ctDisplayServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CTDisplayRenderer.class, null,
				(serviceReference, emitter) -> {
					CTDisplayRenderer<?> ctDisplayRenderer =
						bundleContext.getService(serviceReference);

					try {
						emitter.emit(
							_classNameLocalService.getClassNameId(
								ctDisplayRenderer.getModelClass()));
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});
		_ctServiceServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CTService.class, null,
				(serviceReference, emitter) -> {
					CTService<?> ctService = bundleContext.getService(
						serviceReference);

					emitter.emit(
						_classNameLocalService.getClassNameId(
							ctService.getModelClass()));
				});
	}

	@Deactivate
	protected void deactivate() {
		_ctDisplayServiceTrackerMap.close();
		_ctServiceServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDisplayRendererRegistry.class);

	@Reference
	private BasePersistenceRegistry _basePersistenceRegistry;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, CTDisplayRenderer>
		_ctDisplayServiceTrackerMap;
	private ServiceTrackerMap<Long, CTService> _ctServiceServiceTrackerMap;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private ResourceActions _resourceActions;

}