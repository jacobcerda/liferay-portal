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

package com.liferay.translation.exporter;

import com.liferay.info.field.InfoFormValues;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.translation.exception.InvalidXLIFFFileException;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface TranslationInfoFormValuesExporter<T> {

	public InputStream export(
			InfoFormValues infoFormValues, Locale sourceLocale,
			Locale targetLocale)
		throws IOException;

	public InfoFormValues importXLIFF(
			long groupId, InfoItemClassPKReference infoItemClassPKReference,
			InputStream inputStream)
		throws InvalidXLIFFFileException, IOException;

}