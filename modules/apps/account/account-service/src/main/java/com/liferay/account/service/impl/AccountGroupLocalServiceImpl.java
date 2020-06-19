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

package com.liferay.account.service.impl;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.base.AccountGroupLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroup",
	service = AopService.class
)
public class AccountGroupLocalServiceImpl
	extends AccountGroupLocalServiceBaseImpl {

	@Override
	public AccountGroup addAccountGroup(
			long userId, String name, String description)
		throws PortalException {

		long accountGroupId = counterLocalService.increment();

		AccountGroup accountGroup = accountGroupPersistence.create(
			accountGroupId);

		User user = userLocalService.getUser(userId);

		accountGroup.setCompanyId(user.getCompanyId());
		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setName(name);
		accountGroup.setDescription(description);

		return accountGroupPersistence.update(accountGroup);
	}

	@Override
	public List<AccountGroup> getAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> obc) {

		return accountGroupPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	@Override
	public BaseModelSearchResult<AccountGroup> searchAccountGroups(
		long companyId, String keywords, int start, int end,
		OrderByComparator<AccountGroup> obc) {

		DynamicQuery dynamicQuery = _getDynamicQuery(companyId, keywords, obc);

		return new BaseModelSearchResult<>(
			accountGroupLocalService.dynamicQuery(
				dynamicQuery, start, end, obc),
			(int)accountGroupLocalService.dynamicQueryCount(dynamicQuery));
	}

	@Override
	public AccountGroup updateAccountGroup(
			long accountGroupId, String name, String description)
		throws PortalException {

		AccountGroup accountGroup = accountGroupPersistence.fetchByPrimaryKey(
			accountGroupId);

		accountGroup.setName(name);
		accountGroup.setDescription(description);

		return accountGroupPersistence.update(accountGroup);
	}

	private DynamicQuery _getDynamicQuery(
		long companyId, String keywords, OrderByComparator<AccountGroup> obc) {

		DynamicQuery dynamicQuery = accountGroupLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(keywords, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description",
					StringUtil.quote(keywords, StringPool.PERCENT)));

			dynamicQuery.add(disjunction);
		}

		OrderFactoryUtil.addOrderByComparator(dynamicQuery, obc);

		return dynamicQuery;
	}

}