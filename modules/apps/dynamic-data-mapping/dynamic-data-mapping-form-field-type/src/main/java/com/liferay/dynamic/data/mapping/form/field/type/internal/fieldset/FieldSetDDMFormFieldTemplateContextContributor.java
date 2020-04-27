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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Lancha
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=fieldset",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		FieldSetDDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Map<String, Object> nestedFields =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"nestedFields");

		if (nestedFields == null) {
			nestedFields = new HashMap<>();
		}

		JSONArray rowsJSONArray = getJSONArray(
			GetterUtil.getString(ddmFormField.getProperty("rows")));

		if (_needsLoadLayout(ddmFormField)) {
			rowsJSONArray = getRowsJSONArray(
				getDDMStructureLayoutDefinition(
					GetterUtil.getLong(
						ddmFormField.getProperty("ddmStructureLayoutId"))));
		}

		return HashMapBuilder.<String, Object>put(
			"ddmStructureId", ddmFormField.getProperty("ddmStructureId")
		).put(
			"ddmStructureLayoutId",
			ddmFormField.getProperty("ddmStructureLayoutId")
		).put(
			"nestedFields",
			getNestedFields(
				nestedFields,
				getNestedFieldNames(
					GetterUtil.getString(
						ddmFormField.getProperty("nestedFieldNames")),
					nestedFields.keySet()))
		).put(
			"rows", rowsJSONArray
		).build();
	}

	protected String getDDMStructureLayoutDefinition(long structureLayoutId) {
		try {
			DDMStructureLayout ddmStructureLayout =
				ddmStructureLayoutLocalService.getStructureLayout(
					structureLayoutId);

			return ddmStructureLayout.getDefinition();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return StringPool.BLANK;
	}

	protected JSONArray getJSONArray(String rows) {
		try {
			return jsonFactory.createJSONArray(rows);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return jsonFactory.createJSONArray();
	}

	protected String[] getNestedFieldNames(
		String nestedFieldNames, Set<String> defaultNestedFieldNames) {

		if (Validator.isNotNull(nestedFieldNames)) {
			return StringUtil.split(nestedFieldNames);
		}

		return defaultNestedFieldNames.toArray(new String[0]);
	}

	protected List<Object> getNestedFields(
		Map<String, Object> nestedFields, String[] nestedFieldNames) {

		return Stream.of(
			nestedFieldNames
		).map(
			nestedFields::get
		).collect(
			Collectors.toList()
		);
	}

	protected JSONArray getRowsJSONArray(String definition) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.replace(definition, "fieldNames", "fields"));

			JSONArray pagesJSONArray = jsonObject.getJSONArray("pages");

			JSONObject pageJSONObject = pagesJSONArray.getJSONObject(0);

			return pageJSONObject.getJSONArray("rows");
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return jsonFactory.createJSONArray();
	}

	@Reference
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	private boolean _needsLoadLayout(DDMFormField ddmFormField) {
		if (Validator.isNotNull(ddmFormField.getProperty("ddmStructureId")) &&
			Validator.isNotNull(
				ddmFormField.getProperty("ddmStructureLayoutId"))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldSetDDMFormFieldTemplateContextContributor.class);

}