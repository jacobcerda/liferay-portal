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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItem
	implements ContentDashboardItem<JournalArticle> {

	public JournalArticleContentDashboardItem(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		InfoEditURLProvider<JournalArticle> infoEditURLProvider,
		JournalArticle journalArticle, Language language,
		ModelResourcePermission<JournalArticle> modelResourcePermission) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_infoEditURLProvider = infoEditURLProvider;
		_journalArticle = journalArticle;
		_language = language;
		_modelResourcePermission = modelResourcePermission;
	}

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest) {
		try {
			return Optional.ofNullable(
				_infoEditURLProvider.getURL(_journalArticle, httpServletRequest)
			).orElse(
				StringPool.BLANK
			);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return StringPool.BLANK;
		}
	}

	@Override
	public Date getExpirationDate() {
		return _journalArticle.getExpirationDate();
	}

	@Override
	public Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	@Override
	public Date getPublishDate() {
		return _journalArticle.getDisplayDate();
	}

	@Override
	public List<Status> getStatuses(Locale locale) {
		List<Status> statuses = new ArrayList<>();

		statuses.add(
			new Status(
				_language.get(
					locale,
					WorkflowConstants.getStatusLabel(
						_journalArticle.getStatus())),
				WorkflowConstants.getStatusStyle(_journalArticle.getStatus())));

		if (_journalArticle.hasApprovedVersion() &&
			!_journalArticle.isApproved()) {

			statuses.add(
				0,
				new Status(
					_language.get(
						locale,
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_APPROVED)),
					WorkflowConstants.getStatusStyle(
						WorkflowConstants.STATUS_APPROVED)));
		}

		return Collections.unmodifiableList(statuses);
	}

	@Override
	public String getSubtype(Locale locale) {
		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		return ddmStructure.getName(locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _journalArticle.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _journalArticle.getUserId();
	}

	@Override
	public String getUserName() {
		return _journalArticle.getUserName();
	}

	@Override
	public String getViewURL(HttpServletRequest httpServletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ThemeDisplay clonedThemeDisplay =
				(ThemeDisplay)themeDisplay.clone();

			clonedThemeDisplay.setScopeGroupId(_journalArticle.getGroupId());

			return Optional.ofNullable(
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					JournalArticle.class.getName(),
					_journalArticle.getResourcePrimKey(), clonedThemeDisplay)
			).orElse(
				StringPool.BLANK
			);
		}
		catch (CloneNotSupportedException cloneNotSupportedException) {
			_log.error(cloneNotSupportedException, cloneNotSupportedException);

			return StringPool.BLANK;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return StringPool.BLANK;
		}
	}

	@Override
	public boolean isEditURLEnabled(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			return _modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), _journalArticle,
				ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	@Override
	public boolean isViewURLEnabled(HttpServletRequest httpServletRequest) {
		if (!_journalArticle.hasApprovedVersion()) {
			return false;
		}

		if (Validator.isNull(getViewURL(httpServletRequest))) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleContentDashboardItem.class);

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final InfoEditURLProvider<JournalArticle> _infoEditURLProvider;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final ModelResourcePermission<JournalArticle>
		_modelResourcePermission;

}