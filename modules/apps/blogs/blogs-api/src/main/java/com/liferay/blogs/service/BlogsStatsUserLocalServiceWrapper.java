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

package com.liferay.blogs.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BlogsStatsUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceWrapper
	implements BlogsStatsUserLocalService,
			   ServiceWrapper<BlogsStatsUserLocalService> {

	public BlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	/**
	 * Adds the blogs stats user to the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was added
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser addBlogsStatsUser(
		com.liferay.blogs.model.BlogsStatsUser blogsStatsUser) {

		return _blogsStatsUserLocalService.addBlogsStatsUser(blogsStatsUser);
	}

	/**
	 * Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	 *
	 * @param statsUserId the primary key for the new blogs stats user
	 * @return the new blogs stats user
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser createBlogsStatsUser(
		long statsUserId) {

		return _blogsStatsUserLocalService.createBlogsStatsUser(statsUserId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the blogs stats user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was removed
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser deleteBlogsStatsUser(
		com.liferay.blogs.model.BlogsStatsUser blogsStatsUser) {

		return _blogsStatsUserLocalService.deleteBlogsStatsUser(blogsStatsUser);
	}

	/**
	 * Deletes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws PortalException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser deleteBlogsStatsUser(
			long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.deleteBlogsStatsUser(statsUserId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteStatsUser(
		com.liferay.blogs.model.BlogsStatsUser statsUsers) {

		_blogsStatsUserLocalService.deleteStatsUser(statsUsers);
	}

	@Override
	public void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_blogsStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	@Override
	public void deleteStatsUserByGroupId(long groupId) {
		_blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	@Override
	public void deleteStatsUserByUserId(long userId) {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _blogsStatsUserLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _blogsStatsUserLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _blogsStatsUserLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _blogsStatsUserLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _blogsStatsUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _blogsStatsUserLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.blogs.model.BlogsStatsUser fetchBlogsStatsUser(
		long statsUserId) {

		return _blogsStatsUserLocalService.fetchBlogsStatsUser(statsUserId);
	}

	@Override
	public com.liferay.blogs.model.BlogsStatsUser fetchStatsUser(
		long groupId, long userId) {

		return _blogsStatsUserLocalService.fetchStatsUser(groupId, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _blogsStatsUserLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the blogs stats user with the primary key.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws PortalException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser getBlogsStatsUser(
			long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.getBlogsStatsUser(statsUserId);
	}

	/**
	 * Returns a range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.blogs.model.impl.BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of blogs stats users
	 */
	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getBlogsStatsUsers(int start, int end) {

		return _blogsStatsUserLocalService.getBlogsStatsUsers(start, end);
	}

	/**
	 * Returns the number of blogs stats users.
	 *
	 * @return the number of blogs stats users
	 */
	@Override
	public int getBlogsStatsUsersCount() {
		return _blogsStatsUserLocalService.getBlogsStatsUsersCount();
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getCompanyStatsUsers(long companyId, int start, int end) {

		return _blogsStatsUserLocalService.getCompanyStatsUsers(
			companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getCompanyStatsUsers(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.blogs.model.BlogsStatsUser> orderByComparator) {

		return _blogsStatsUserLocalService.getCompanyStatsUsers(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyStatsUsersCount(long companyId) {
		return _blogsStatsUserLocalService.getCompanyStatsUsersCount(companyId);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupsStatsUsers(long companyId, long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupsStatsUsers(
			companyId, groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupStatsUsers(long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupStatsUsers(
			groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getGroupStatsUsers(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.blogs.model.BlogsStatsUser> orderByComparator) {

		return _blogsStatsUserLocalService.getGroupStatsUsers(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getGroupStatsUsersCount(long groupId) {
		return _blogsStatsUserLocalService.getGroupStatsUsersCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _blogsStatsUserLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getOrganizationStatsUsers(long organizationId, int start, int end) {

		return _blogsStatsUserLocalService.getOrganizationStatsUsers(
			organizationId, start, end);
	}

	@Override
	public java.util.List<com.liferay.blogs.model.BlogsStatsUser>
		getOrganizationStatsUsers(
			long organizationId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.blogs.model.BlogsStatsUser> orderByComparator) {

		return _blogsStatsUserLocalService.getOrganizationStatsUsers(
			organizationId, start, end, orderByComparator);
	}

	@Override
	public int getOrganizationStatsUsersCount(long organizationId) {
		return _blogsStatsUserLocalService.getOrganizationStatsUsersCount(
			organizationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _blogsStatsUserLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.blogs.model.BlogsStatsUser getStatsUser(
			long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	/**
	 * Updates the blogs stats user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param blogsStatsUser the blogs stats user
	 * @return the blogs stats user that was updated
	 */
	@Override
	public com.liferay.blogs.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.blogs.model.BlogsStatsUser blogsStatsUser) {

		return _blogsStatsUserLocalService.updateBlogsStatsUser(blogsStatsUser);
	}

	@Override
	public void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_blogsStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	@Override
	public void updateStatsUser(
			long groupId, long userId, java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.PortalException {

		_blogsStatsUserLocalService.updateStatsUser(
			groupId, userId, displayDate);
	}

	@Override
	public com.liferay.blogs.model.BlogsStatsUser updateStatsUser(
			long groupId, long userId, int ratingsTotalEntries,
			double ratingsTotalScore, double ratingsAverageScore)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _blogsStatsUserLocalService.updateStatsUser(
			groupId, userId, ratingsTotalEntries, ratingsTotalScore,
			ratingsAverageScore);
	}

	@Override
	public BlogsStatsUserLocalService getWrappedService() {
		return _blogsStatsUserLocalService;
	}

	@Override
	public void setWrappedService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;

}