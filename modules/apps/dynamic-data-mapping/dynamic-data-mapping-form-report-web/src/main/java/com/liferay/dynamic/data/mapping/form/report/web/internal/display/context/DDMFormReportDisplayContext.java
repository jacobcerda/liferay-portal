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

package com.liferay.dynamic.data.mapping.form.report.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.report.web.internal.portlet.DDMFormReportPortlet;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author Bruno Farache
 */
public class DDMFormReportDisplayContext {

	public DDMFormReportDisplayContext(
		DDMFormInstanceReport ddmFormInstanceReport,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_ddmFormInstanceReport = ddmFormInstanceReport;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public DDMFormInstanceReport getDDMFormInstanceReport() {
		return _ddmFormInstanceReport;
	}

	public JSONArray getFieldsJSONArray() throws PortalException {
		JSONArray fieldsJSONArray = JSONFactoryUtil.createJSONArray();

		if (_ddmFormInstanceReport == null) {
			return fieldsJSONArray;
		}

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceReport.getFormInstance();

		DDMForm ddmForm = ddmFormInstance.getDDMForm();

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		ddmFormFields.forEach(
			ddmFormField -> fieldsJSONArray.put(
				JSONUtil.put(
					"label", _getValue(ddmFormField.getLabel())
				).put(
					"name", ddmFormField.getName()
				).put(
					"options", _getFieldOptions(ddmFormField)
				).put(
					"type", ddmFormField.getType()
				)));

		return fieldsJSONArray;
	}

	public String getFormReportRecordsFieldValuesURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID("/form-report/get_records_field_values");

		return resourceURL.toString();
	}

	public String getLastModifiedDate() {
		if (_ddmFormInstanceReport == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), DDMFormReportPortlet.class);

		String languageKey = "report-was-last-modified-on-x";

		Date modifiedDate = _ddmFormInstanceReport.getModifiedDate();

		int daysBetween = DateUtil.getDaysBetween(
			new Date(modifiedDate.getTime()), new Date(),
			themeDisplay.getTimeZone());

		if (daysBetween < 2) {
			languageKey = "report-was-last-modified-x";
		}

		String relativeTimeDescription = StringUtil.removeSubstring(
			Time.getRelativeTimeDescription(
				modifiedDate, themeDisplay.getLocale(),
				themeDisplay.getTimeZone()),
			StringPool.PERIOD);

		if (daysBetween < 2) {
			relativeTimeDescription = StringUtil.toLowerCase(
				relativeTimeDescription);
		}

		return LanguageUtil.format(
			resourceBundle, languageKey, relativeTimeDescription, false);
	}

	public int getTotalItems() throws PortalException {
		if (_ddmFormInstanceReport == null) {
			return 0;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_ddmFormInstanceReport.getData());

		return jsonObject.getInt("totalItems");
	}

	private JSONObject _getFieldOptions(DDMFormField ddmFormField) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		optionsValues.forEach(
			optionValue -> jsonObject.put(
				optionValue,
				_getValue(ddmFormFieldOptions.getOptionLabels(optionValue))));

		return jsonObject;
	}

	private String _getValue(Value value) {
		return value.getString(value.getDefaultLocale());
	}

	private final DDMFormInstanceReport _ddmFormInstanceReport;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}