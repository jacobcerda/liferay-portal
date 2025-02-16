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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.application.list.ProductMenuPanelApp;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "app.builder.deploy.type=productMenu",
	service = AppDeployer.class
)
public class ProductMenuAppDeployer implements AppDeployer {

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderAppDeployment appBuilderAppDeployment =
			_appBuilderAppDeploymentLocalService.getAppBuilderAppDeployment(
				appId, "productMenu");

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			appBuilderAppDeployment.getSettings());

		JSONArray scopeJSONArray = jsonObject.getJSONArray("scope");

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setActive(true);

		String appName = appBuilderApp.getName(
			LocaleThreadLocal.getDefaultLocale());

		String portletName = _getPortletName(appId);

		String applicationsMenuLabel = portletName.concat("applications");
		String siteMenuLabel = portletName.concat("site");

		if (scopeJSONArray.length() == 2) {
			_serviceRegistrationsMap.computeIfAbsent(
				appId,
				key -> ArrayUtil.append(
					_deployPortlet(
						appBuilderApp, appName, applicationsMenuLabel),
					_deployPortlet(appBuilderApp, appName, siteMenuLabel),
					new ServiceRegistration<?>[] {
						_deployPanelApp(
							appBuilderApp.getCompanyId(),
							PanelCategoryKeys.GLOBAL_MENU_APPLICATIONS,
							applicationsMenuLabel,
							JSONUtil.toLongArray(
								jsonObject.getJSONArray("siteIds"))),
						_deployPanelApp(
							appBuilderApp.getCompanyId(),
							PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
							siteMenuLabel,
							JSONUtil.toLongArray(
								jsonObject.getJSONArray("siteIds")))
					}));
		}
		else {
			String scope = scopeJSONArray.getString(0);
			String menuLabel;

			if (Objects.equals(
					PanelCategoryKeys.GLOBAL_MENU_APPLICATIONS, scope)) {

				menuLabel = applicationsMenuLabel;
			}
			else {
				menuLabel = siteMenuLabel;
			}

			_serviceRegistrationsMap.computeIfAbsent(
				appId,
				mapKey -> ArrayUtil.append(
					_deployPortlet(appBuilderApp, appName, menuLabel),
					_deployPanelApp(
						appBuilderApp.getCompanyId(), scope, menuLabel,
						JSONUtil.toLongArray(
							jsonObject.getJSONArray("siteIds")))));
		}

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		undeploy(_appBuilderAppLocalService, appId, _serviceRegistrationsMap);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceRegistrationsMap.clear();
	}

	private ServiceRegistration<?> _deployPanelApp(
		long companyId, String panelCategoryKey, String portletName,
		long[] siteIds) {

		return _bundleContext.registerService(
			PanelApp.class,
			new ProductMenuPanelApp(
				companyId, panelCategoryKey, portletName, siteIds),
			new HashMapDictionary<String, Object>() {
				{
					put("panel.app.order:Integer", 100);
					put("panel.category.key", panelCategoryKey);
				}
			});
	}

	private ServiceRegistration<?>[] _deployPortlet(
		AppBuilderApp appBuilderApp, String appName, String portletName) {

		return _appDeployerHelper.deployPortlet(
			new AppPortlet(appBuilderApp, "productMenu", appName, portletName),
			_bundleContext, Collections.emptyMap());
	}

	private String _getPortletName(long appId) {
		return AppBuilderPortletKeys.PRODUCT_MENU_APP + "_" + appId;
	}

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppDeployerHelper _appDeployerHelper;

	private BundleContext _bundleContext;

	@Reference
	private JSONFactory _jsonFactory;

	private final ConcurrentHashMap<Long, ServiceRegistration<?>[]>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

}