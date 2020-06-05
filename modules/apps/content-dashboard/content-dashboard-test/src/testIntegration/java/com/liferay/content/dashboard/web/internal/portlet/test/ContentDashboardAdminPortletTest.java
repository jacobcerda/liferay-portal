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

package com.liferay.content.dashboard.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class ContentDashboardAdminPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_company.getDefaultUser()));

		_user = _company.getDefaultUser();
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(), 0);
	}

	@Test
	public void testGetSearchContainer() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest();

		SearchContainer searchContainer = _getSearchContainer(
			mockLiferayPortletRenderRequest);

		int initialCount = searchContainer.getTotal();

		JournalTestUtil.addArticle(_user.getUserId(), _group.getGroupId(), 0);

		searchContainer = _getSearchContainer(mockLiferayPortletRenderRequest);

		int actualCount = searchContainer.getTotal();

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetSearchContainerWithDefaultOrder() throws Exception {
		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_user.getUserId(), _group.getGroupId(), 0);

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_user.getUserId(), _group.getGroupId(), 0);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest();

		SearchContainer searchContainer = _getSearchContainer(
			mockLiferayPortletRenderRequest);

		Assert.assertEquals(2, searchContainer.getTotal());

		List<Object> results = searchContainer.getResults();

		Assert.assertEquals(
			journalArticle2.getTitle(LocaleUtil.US),
			ReflectionTestUtil.invoke(
				results.get(0), "getTitle", new Class<?>[] {Locale.class},
				LocaleUtil.US));

		Assert.assertEquals(
			journalArticle1.getTitle(LocaleUtil.US),
			ReflectionTestUtil.invoke(
				results.get(1), "getTitle", new Class<?>[] {Locale.class},
				LocaleUtil.US));
	}

	@Test
	public void testGetSearchContainerWithKeywords() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_user.getUserId(), _group.getGroupId(), 0);

		JournalTestUtil.addArticle(_user.getUserId(), _group.getGroupId(), 0);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setParameter(
			"keywords", journalArticle.getTitle(LocaleUtil.getDefault()));

		SearchContainer searchContainer = _getSearchContainer(
			mockLiferayPortletRenderRequest);

		Assert.assertEquals(1, searchContainer.getTotal());

		List<Object> results = searchContainer.getResults();

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			ReflectionTestUtil.invoke(
				results.get(0), "getTitle", new Class<?>[] {Locale.class},
				LocaleUtil.US));
	}

	@Test
	public void testGetSearchContainerWithPagination() throws Exception {
		for (int i = 0; i <= SearchContainer.DEFAULT_DELTA; i++) {
			JournalTestUtil.addArticle(
				_user.getUserId(), _group.getGroupId(), 0);
		}

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest();

		SearchContainer searchContainer = _getSearchContainer(
			mockLiferayPortletRenderRequest);

		Assert.assertEquals(
			SearchContainer.DEFAULT_DELTA + 1, searchContainer.getTotal());

		List<Object> objects = searchContainer.getResults();

		Assert.assertEquals(
			objects.toString(), SearchContainer.DEFAULT_DELTA, objects.size());
	}

	private MockLiferayPortletRenderRequest
			_getMockLiferayPortletRenderRequest()
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		String path = "/view.jsp";

		mockLiferayPortletRenderRequest.setParameter("mvcPath", path);

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			ProxyUtil.newProxyInstance(
				PortletContext.class.getClassLoader(),
				new Class<?>[] {PortletContext.class},
				(PortletContextProxy, portletContextMethod,
				 portletContextArgs) -> {

					if (Objects.equals(
							portletContextMethod.getName(),
							"getRequestDispatcher") &&
						Objects.equals(portletContextArgs[0], path)) {

						return ProxyUtil.newProxyInstance(
							PortletRequestDispatcher.class.getClassLoader(),
							new Class<?>[] {PortletRequestDispatcher.class},
							(portletRequestDispatcherProxy,
							 portletRequestDispatcherMethod,
							 portletRequestDispatcherArgs) -> null);
					}

					throw new UnsupportedOperationException();
				}));

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletRenderRequest;
	}

	private SearchContainer _getSearchContainer(
			MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest)
		throws Exception {

		MVCPortlet mvcPortlet = (MVCPortlet)_portlet;

		mvcPortlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		return ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT"),
			"getSearchContainer", new Class<?>[0]);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setUser(_company.getDefaultUser());

		return themeDisplay;
	}

	private static Company _company;
	private static PermissionChecker _permissionChecker;
	private static User _user;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.web.internal.portlet.ContentDashboardAdminPortlet"
	)
	private Portlet _portlet;

}