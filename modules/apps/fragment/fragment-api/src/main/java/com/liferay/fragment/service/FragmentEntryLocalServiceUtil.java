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

package com.liferay.fragment.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentEntry. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLocalService
 * @generated
 */
public class FragmentEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fragment entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was added
	 */
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().addFragmentEntry(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, long previewFileEntryId,
			int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name,
			previewFileEntryId, type, status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, boolean cacheable, String configuration,
			long previewFileEntryId, int type, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, cacheable, configuration, previewFileEntryId, type,
			status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, String configuration, long previewFileEntryId, int type,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntry(
			userId, groupId, fragmentCollectionId, fragmentEntryKey, name, css,
			html, js, configuration, previewFileEntryId, type, status,
			serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry checkout(
			com.liferay.fragment.model.FragmentEntry publishedFragmentEntry,
			int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().checkout(publishedFragmentEntry, version);
	}

	public static com.liferay.fragment.model.FragmentEntry copyFragmentEntry(
			long userId, long groupId, long fragmentEntryId,
			long fragmentCollectionId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyFragmentEntry(
			userId, groupId, fragmentEntryId, fragmentCollectionId,
			serviceContext);
	}

	/**
	 * Creates a new fragment entry. Does not add the fragment entry to the database.
	 *
	 * @return the new fragment entry
	 */
	public static com.liferay.fragment.model.FragmentEntry create() {
		return getService().create();
	}

	public static com.liferay.fragment.model.FragmentEntry createFragmentEntry(
		long fragmentEntryId) {

		return getService().createFragmentEntry(fragmentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static com.liferay.fragment.model.FragmentEntry delete(
			com.liferay.fragment.model.FragmentEntry publishedFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().delete(publishedFragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry deleteDraft(
			com.liferay.fragment.model.FragmentEntry draftFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDraft(draftFragmentEntry);
	}

	/**
	 * Deletes the fragment entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
			com.liferay.fragment.model.FragmentEntry fragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntry(fragmentEntry);
	}

	/**
	 * Deletes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws PortalException if a fragment entry with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
			long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntry(fragmentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.fragment.model.FragmentEntryVersion deleteVersion(
			com.liferay.fragment.model.FragmentEntryVersion
				fragmentEntryVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteVersion(fragmentEntryVersion);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchDraft(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().fetchDraft(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchDraft(
		long primaryKey) {

		return getService().fetchDraft(primaryKey);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long fragmentEntryId) {

		return getService().fetchFragmentEntry(fragmentEntryId);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long groupId, String fragmentEntryKey) {

		return getService().fetchFragmentEntry(groupId, fragmentEntryKey);
	}

	public static com.liferay.fragment.model.FragmentEntry
		fetchFragmentEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchFragmentEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.fragment.model.FragmentEntryVersion
		fetchLatestVersion(
			com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().fetchLatestVersion(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchPublished(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().fetchPublished(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchPublished(
		long primaryKey) {

		return getService().fetchPublished(primaryKey);
	}

	public static String generateFragmentEntryKey(long groupId, String name) {
		return getService().generateFragmentEntryKey(groupId, name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.fragment.model.FragmentEntry getDraft(
			com.liferay.fragment.model.FragmentEntry fragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry getDraft(
			long primaryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraft(primaryKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of fragment entries
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(int start, int end) {

		return getService().getFragmentEntries(start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(long fragmentCollectionId) {

		return getService().getFragmentEntries(fragmentCollectionId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(long fragmentCollectionId, int start, int end) {

		return getService().getFragmentEntries(
			fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int status) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, status);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntries(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntries(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getFragmentEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry>
		getFragmentEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntry> orderByComparator) {

		return getService().getFragmentEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of fragment entries.
	 *
	 * @return the number of fragment entries
	 */
	public static int getFragmentEntriesCount() {
		return getService().getFragmentEntriesCount();
	}

	public static int getFragmentEntriesCount(long fragmentCollectionId) {
		return getService().getFragmentEntriesCount(fragmentCollectionId);
	}

	/**
	 * Returns the fragment entry with the primary key.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws PortalException if a fragment entry with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntry getFragmentEntry(
			long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntry(fragmentEntryId);
	}

	public static com.liferay.fragment.model.FragmentEntry
			getFragmentEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntryByUuidAndGroupId(uuid, groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static String[] getTempFileNames(
			long userId, long groupId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempFileNames(userId, groupId, folderName);
	}

	public static com.liferay.fragment.model.FragmentEntryVersion getVersion(
			com.liferay.fragment.model.FragmentEntry fragmentEntry, int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getVersion(fragmentEntry, version);
	}

	public static java.util.List
		<com.liferay.fragment.model.FragmentEntryVersion> getVersions(
			com.liferay.fragment.model.FragmentEntry fragmentEntry) {

		return getService().getVersions(fragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveFragmentEntry(
			fragmentEntryId, fragmentCollectionId);
	}

	public static com.liferay.fragment.model.FragmentEntry publishDraft(
			com.liferay.fragment.model.FragmentEntry draftFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().publishDraft(draftFragmentEntry);
	}

	public static void registerListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.fragment.model.FragmentEntry,
			 com.liferay.fragment.model.FragmentEntryVersion>
				versionServiceListener) {

		getService().registerListener(versionServiceListener);
	}

	public static void unregisterListener(
		com.liferay.portal.kernel.service.version.VersionServiceListener
			<com.liferay.fragment.model.FragmentEntry,
			 com.liferay.fragment.model.FragmentEntryVersion>
				versionServiceListener) {

		getService().unregisterListener(versionServiceListener);
	}

	public static com.liferay.fragment.model.FragmentEntry updateDraft(
			com.liferay.fragment.model.FragmentEntry draftFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDraft(draftFragmentEntry);
	}

	/**
	 * Updates the fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntry the fragment entry
	 * @return the fragment entry that was updated
	 * @throws PortalException
	 */
	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			com.liferay.fragment.model.FragmentEntry draftFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(draftFragmentEntry);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			fragmentEntryId, previewFileEntryId);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, boolean cacheable, String configuration,
			long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, cacheable,
			configuration, previewFileEntryId, status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, configuration,
			status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long userId, long fragmentEntryId, String name, String css,
			String html, String js, String configuration,
			long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(
			userId, fragmentEntryId, name, css, html, js, configuration,
			previewFileEntryId, status);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntry(fragmentEntryId, name);
	}

	public static FragmentEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentEntryLocalService, FragmentEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentEntryLocalService.class);

		ServiceTracker<FragmentEntryLocalService, FragmentEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<FragmentEntryLocalService, FragmentEntryLocalService>(
						bundle.getBundleContext(),
						FragmentEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}