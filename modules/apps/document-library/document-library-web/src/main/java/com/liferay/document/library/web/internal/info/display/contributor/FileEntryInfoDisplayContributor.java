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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.info.display.field.AssetEntryInfoDisplayFieldProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.dynamic.data.mapping.info.display.field.DDMFormValuesInfoDisplayFieldProvider;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.field.ClassTypesInfoDisplayFieldProvider;
import com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryDDMFormValuesReader;
import com.liferay.portlet.documentlibrary.asset.model.DLFileEntryClassTypeReader;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class FileEntryInfoDisplayContributor
	implements InfoDisplayContributor<FileEntry> {

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	@Override
	public List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		return _classTypesInfoDisplayFieldProvider.getClassTypes(
			groupId, new DLFileEntryClassTypeReader(), locale);
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<InfoDisplayField> infoDisplayFields =
			_infoDisplayFieldProvider.getContributorInfoDisplayFields(
				locale, AssetEntry.class.getName(), getClassName());

		DLFileEntryClassTypeReader dlFileEntryClassTypeReader =
			new DLFileEntryClassTypeReader();

		ClassType classType = dlFileEntryClassTypeReader.getClassType(
			classTypeId, locale);

		if (classType != null) {
			infoDisplayFields.addAll(
				_classTypesInfoDisplayFieldProvider.
					getClassTypeInfoDisplayFields(classType, locale));
		}

		infoDisplayFields.addAll(
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFields(getClassName(), locale));

		return infoDisplayFields;
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			FileEntry fileEntry, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldValues =
			HashMapBuilder.<String, Object>putAll(
				_infoDisplayFieldProvider.getContributorInfoDisplayFieldsValues(
					getClassName(), fileEntry, locale)
			).build();

		if (fileEntry.getModel() instanceof DLFileEntry) {
			infoDisplayFieldValues.putAll(
				_assetEntryInfoDisplayFieldProvider.
					getAssetEntryInfoDisplayFieldsValues(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId(), locale));
			infoDisplayFieldValues.putAll(
				_expandoInfoDisplayFieldProvider.
					getContributorExpandoInfoDisplayFieldsValues(
						DLFileEntryConstants.getClassName(), fileEntry,
						locale));

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DLFileEntryDDMFormValuesReader dlFileEntryDDMFormValuesReader =
				new DLFileEntryDDMFormValuesReader(
					fileEntry, fileEntry.getFileVersion());

			infoDisplayFieldValues.putAll(
				_ddmFormValuesInfoDisplayFieldProvider.
					getInfoDisplayFieldsValues(
						dlFileEntry,
						dlFileEntryDDMFormValuesReader.getDDMFormValues(),
						locale));
		}

		return infoDisplayFieldValues;
	}

	@Override
	public long getInfoDisplayObjectClassPK(FileEntry fileEntry) {
		return fileEntry.getFileEntryId();
	}

	@Override
	public InfoDisplayObjectProvider<FileEntry> getInfoDisplayObjectProvider(
			long classPK)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.fetchFileEntryLocalRepository(classPK);

		if (localRepository == null) {
			return null;
		}

		FileEntry fileEntry = localRepository.getFileEntry(classPK);

		if (fileEntry.isInTrash()) {
			return null;
		}

		return new FileEntryInfoDisplayObjectProvider(fileEntry);
	}

	@Override
	public InfoDisplayObjectProvider<FileEntry> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		return getInfoDisplayObjectProvider(Long.valueOf(urlTitle));
	}

	@Override
	public String getInfoURLSeparator() {
		return "/d/";
	}

	@Reference
	private AssetEntryInfoDisplayFieldProvider
		_assetEntryInfoDisplayFieldProvider;

	@Reference
	private ClassTypesInfoDisplayFieldProvider
		_classTypesInfoDisplayFieldProvider;

	@Reference
	private DDMFormValuesInfoDisplayFieldProvider<DLFileEntry>
		_ddmFormValuesInfoDisplayFieldProvider;

	@Reference
	private ExpandoInfoDisplayFieldProvider _expandoInfoDisplayFieldProvider;

	@Reference
	private InfoDisplayFieldProvider _infoDisplayFieldProvider;

	@Reference
	private RepositoryProvider _repositoryProvider;

}