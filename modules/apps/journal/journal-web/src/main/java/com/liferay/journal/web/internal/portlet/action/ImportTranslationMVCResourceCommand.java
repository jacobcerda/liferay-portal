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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.RequiredFileException;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.exception.InvalidXLIFFFileException;
import com.liferay.translation.exporter.TranslationInfoFormValuesExporter;

import java.io.InputStream;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/import_translation"
	},
	service = MVCActionCommand.class
)
public class ImportTranslationMVCResourceCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			_checkExceededSizeLimit(uploadPortletRequest);

			String sourceFileName = uploadPortletRequest.getFileName("file");

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String articleId = ParamUtil.getString(actionRequest, "articleId");

			JournalArticle article = _journalArticleService.getArticle(
				groupId, articleId);

			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file")) {

				String contentType = uploadPortletRequest.getContentType(
					"file");

				if (!Objects.equals("application/xliff+xml", contentType)) {
					throw new InvalidXLIFFFileException(
						"Unsupported content type: " + contentType);
				}

				_translationInfoFormValuesExporter.importXLIFF(
					themeDisplay.getScopeGroupId(),
					new InfoItemClassPKReference(
						JournalArticle.class.getName(), article.getClassPK()),
					inputStream);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				_journalArticleService.updateArticleTranslation(
					article.getGroupId(), article.getArticleId(),
					article.getVersion(), LocaleUtil.getDefault(),
					article.getTitle(), article.getDescription(),
					article.getContent(), null,
					ServiceContextFactory.getInstance(actionRequest));
				actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
			}
			catch (PortalException portalException) {
				if (Validator.isNotNull(sourceFileName)) {
					SessionErrors.add(
						actionRequest, RequiredFileException.class);
				}

				throw portalException;
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}
	}

	private void _checkExceededSizeLimit(HttpServletRequest httpServletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)httpServletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable cause = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(cause);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(cause);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(cause);
			}

			throw new PortalException(cause);
		}
	}

	@Reference
	private Http _http;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationInfoFormValuesExporter
		_translationInfoFormValuesExporter;

}