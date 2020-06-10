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

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = DDMStructureInfoItemFieldSetProvider.class)
public class DDMStructureInfoItemFieldSetProviderImpl
	implements DDMStructureInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoItemFieldSet(long ddmStructureId)
		throws NoSuchStructureException {

		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			InfoFieldSet infoFieldSet = new InfoFieldSet(
				InfoLocalizedValue.builder(
				).addValues(
					ddmStructure.getNameMap()
				).build(),
				ddmStructure.getStructureKey());

			List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(
				false);

			for (DDMFormField ddmFormField : ddmFormFields) {
				if (Validator.isNull(ddmFormField.getIndexType()) ||
					!ArrayUtil.contains(
						_SELECTABLE_DDM_STRUCTURE_FIELDS,
						ddmFormField.getType())) {

					continue;
				}

				LocalizedValue label = ddmFormField.getLabel();

				InfoLocalizedValue<String> labelInfoLocalizedValue =
					InfoLocalizedValue.builder(
					).addValues(
						label.getValues()
					).defaultLocale(
						label.getDefaultLocale()
					).build();

				infoFieldSet.add(
					new InfoField(
						_getInfoFieldType(ddmFormField),
						labelInfoLocalizedValue, ddmFormField.isLocalizable(),
						ddmFormField.getName()));
			}

			return infoFieldSet;
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Caught unexpected exception", portalException);
		}
	}

	private InfoFieldType _getInfoFieldType(DDMFormField ddmFormField) {
		String ddmFormFieldType = ddmFormField.getType();

		if (Objects.equals(ddmFormFieldType, "ddm-image") ||
			Objects.equals(ddmFormFieldType, "image")) {

			return ImageInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private static final String[] _SELECTABLE_DDM_STRUCTURE_FIELDS = {
		"checkbox", "ddm-date", "ddm-decimal", "ddm-image", "ddm-integer",
		"ddm-number", "ddm-text-html", "radio", "select", "text", "textarea"
	};

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}