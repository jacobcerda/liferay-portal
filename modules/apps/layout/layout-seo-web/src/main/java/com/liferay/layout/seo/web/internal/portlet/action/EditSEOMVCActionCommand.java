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

package com.liferay.layout.seo.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.seo.service.LayoutSEOEntryService;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/edit_seo"
	},
	service = MVCActionCommand.class
)
public class EditSEOMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		Map<Locale, String> titleMap = _getLocalizationMap(
			actionRequest, layout, "title");
		Map<Locale, String> descriptionMap = _getLocalizationMap(
			actionRequest, layout, "description");

		Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "keywords");
		Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "robots");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		if (layout.isTypeAssetDisplay()) {
			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);
		}

		layout = _layoutService.updateLayout(
			groupId, privateLayout, layoutId, layout.getParentLayoutId(),
			layout.getNameMap(), titleMap, descriptionMap, keywordsMap,
			robotsMap, layout.getType(), layout.isHidden(),
			layout.getFriendlyURLMap(), layout.isIconImage(), null,
			serviceContext);

		boolean canonicalURLEnabled = ParamUtil.getBoolean(
			actionRequest, "canonicalURLEnabled");
		Map<Locale, String> canonicalURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "canonicalURL");

		_layoutSEOEntryService.updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			_layoutService.updateLayout(
				groupId, privateLayout, draftLayout.getLayoutId(),
				draftLayout.getParentLayoutId(), draftLayout.getNameMap(),
				titleMap, descriptionMap, keywordsMap, robotsMap,
				draftLayout.getType(), draftLayout.isHidden(),
				draftLayout.getFriendlyURLMap(), draftLayout.isIconImage(),
				null, serviceContext);

			_layoutSEOEntryService.updateLayoutSEOEntry(
				groupId, privateLayout, draftLayout.getLayoutId(),
				canonicalURLEnabled, canonicalURLMap, serviceContext);
		}

		themeDisplay.clearLayoutFriendlyURL(layout);

		UnicodeProperties layoutTypeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		UnicodeProperties formTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String type = layout.getType();

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			layoutTypeSettingsUnicodeProperties.putAll(
				formTypeSettingsUnicodeProperties);

			boolean layoutCustomizable = GetterUtil.getBoolean(
				layoutTypeSettingsUnicodeProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));

			if (!layoutCustomizable) {
				layoutTypePortlet.removeCustomization(
					layoutTypeSettingsUnicodeProperties);
			}

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsUnicodeProperties.toString());
		}
		else {
			layoutTypeSettingsUnicodeProperties.putAll(
				formTypeSettingsUnicodeProperties);

			layoutTypeSettingsUnicodeProperties.putAll(
				layout.getTypeSettingsProperties());

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsUnicodeProperties.toString());
		}

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			uploadPortletRequest,
			_portal.getHttpServletResponse(actionResponse));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = _portal.getLayoutFullURL(layout, themeDisplay);
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutUpdated", layout);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	private Map<Locale, String> _getLocalizationMap(
		ActionRequest actionRequest, Layout layout, String name) {

		if (layout.isTypeAssetDisplay()) {
			return HashMapBuilder.put(
				LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()),
				ParamUtil.getString(actionRequest, name)
			).build();
		}

		return LocalizationUtil.getLocalizationMap(actionRequest, name);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutSEOEntryService _layoutSEOEntryService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}