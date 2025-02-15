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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountEntryUserRelModel;
import com.liferay.account.model.AccountEntryUserRelSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the AccountEntryUserRel service. Represents a row in the &quot;AccountEntryUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AccountEntryUserRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AccountEntryUserRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelImpl
 * @generated
 */
@JSON(strict = true)
public class AccountEntryUserRelModelImpl
	extends BaseModelImpl<AccountEntryUserRel>
	implements AccountEntryUserRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account entry user rel model instance should use the <code>AccountEntryUserRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "AccountEntryUserRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"accountEntryUserRelId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"accountEntryId", Types.BIGINT},
		{"accountUserId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("accountEntryUserRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("accountEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("accountUserId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AccountEntryUserRel (mvccVersion LONG default 0 not null,accountEntryUserRelId LONG not null primary key,companyId LONG,accountEntryId LONG,accountUserId LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table AccountEntryUserRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY accountEntryUserRel.accountEntryUserRelId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AccountEntryUserRel.accountEntryUserRelId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long ACCOUNTENTRYID_COLUMN_BITMASK = 1L;

	public static final long ACCOUNTUSERID_COLUMN_BITMASK = 2L;

	public static final long ACCOUNTENTRYUSERRELID_COLUMN_BITMASK = 4L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static AccountEntryUserRel toModel(
		AccountEntryUserRelSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		AccountEntryUserRel model = new AccountEntryUserRelImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setAccountEntryUserRelId(soapModel.getAccountEntryUserRelId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setAccountEntryId(soapModel.getAccountEntryId());
		model.setAccountUserId(soapModel.getAccountUserId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<AccountEntryUserRel> toModels(
		AccountEntryUserRelSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<AccountEntryUserRel> models = new ArrayList<AccountEntryUserRel>(
			soapModels.length);

		for (AccountEntryUserRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public AccountEntryUserRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _accountEntryUserRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAccountEntryUserRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _accountEntryUserRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AccountEntryUserRel.class;
	}

	@Override
	public String getModelClassName() {
		return AccountEntryUserRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AccountEntryUserRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AccountEntryUserRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountEntryUserRel, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((AccountEntryUserRel)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AccountEntryUserRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AccountEntryUserRel, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AccountEntryUserRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AccountEntryUserRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AccountEntryUserRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AccountEntryUserRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AccountEntryUserRel.class.getClassLoader(),
			AccountEntryUserRel.class, ModelWrapper.class);

		try {
			Constructor<AccountEntryUserRel> constructor =
				(Constructor<AccountEntryUserRel>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<AccountEntryUserRel, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AccountEntryUserRel, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AccountEntryUserRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<AccountEntryUserRel, Object>>();
		Map<String, BiConsumer<AccountEntryUserRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<AccountEntryUserRel, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", AccountEntryUserRel::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AccountEntryUserRel, Long>)
				AccountEntryUserRel::setMvccVersion);
		attributeGetterFunctions.put(
			"accountEntryUserRelId",
			AccountEntryUserRel::getAccountEntryUserRelId);
		attributeSetterBiConsumers.put(
			"accountEntryUserRelId",
			(BiConsumer<AccountEntryUserRel, Long>)
				AccountEntryUserRel::setAccountEntryUserRelId);
		attributeGetterFunctions.put(
			"companyId", AccountEntryUserRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<AccountEntryUserRel, Long>)
				AccountEntryUserRel::setCompanyId);
		attributeGetterFunctions.put(
			"accountEntryId", AccountEntryUserRel::getAccountEntryId);
		attributeSetterBiConsumers.put(
			"accountEntryId",
			(BiConsumer<AccountEntryUserRel, Long>)
				AccountEntryUserRel::setAccountEntryId);
		attributeGetterFunctions.put(
			"accountUserId", AccountEntryUserRel::getAccountUserId);
		attributeSetterBiConsumers.put(
			"accountUserId",
			(BiConsumer<AccountEntryUserRel, Long>)
				AccountEntryUserRel::setAccountUserId);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getAccountEntryUserRelId() {
		return _accountEntryUserRelId;
	}

	@Override
	public void setAccountEntryUserRelId(long accountEntryUserRelId) {
		_accountEntryUserRelId = accountEntryUserRelId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	@Override
	public long getAccountEntryId() {
		return _accountEntryId;
	}

	@Override
	public void setAccountEntryId(long accountEntryId) {
		_columnBitmask |= ACCOUNTENTRYID_COLUMN_BITMASK;

		if (!_setOriginalAccountEntryId) {
			_setOriginalAccountEntryId = true;

			_originalAccountEntryId = _accountEntryId;
		}

		_accountEntryId = accountEntryId;
	}

	public long getOriginalAccountEntryId() {
		return _originalAccountEntryId;
	}

	@JSON
	@Override
	public long getAccountUserId() {
		return _accountUserId;
	}

	@Override
	public void setAccountUserId(long accountUserId) {
		_columnBitmask |= ACCOUNTUSERID_COLUMN_BITMASK;

		if (!_setOriginalAccountUserId) {
			_setOriginalAccountUserId = true;

			_originalAccountUserId = _accountUserId;
		}

		_accountUserId = accountUserId;
	}

	@Override
	public String getAccountUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getAccountUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setAccountUserUuid(String accountUserUuid) {
	}

	public long getOriginalAccountUserId() {
		return _originalAccountUserId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AccountEntryUserRel.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AccountEntryUserRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AccountEntryUserRel>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AccountEntryUserRelImpl accountEntryUserRelImpl =
			new AccountEntryUserRelImpl();

		accountEntryUserRelImpl.setMvccVersion(getMvccVersion());
		accountEntryUserRelImpl.setAccountEntryUserRelId(
			getAccountEntryUserRelId());
		accountEntryUserRelImpl.setCompanyId(getCompanyId());
		accountEntryUserRelImpl.setAccountEntryId(getAccountEntryId());
		accountEntryUserRelImpl.setAccountUserId(getAccountUserId());

		accountEntryUserRelImpl.resetOriginalValues();

		return accountEntryUserRelImpl;
	}

	@Override
	public int compareTo(AccountEntryUserRel accountEntryUserRel) {
		long primaryKey = accountEntryUserRel.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountEntryUserRel)) {
			return false;
		}

		AccountEntryUserRel accountEntryUserRel = (AccountEntryUserRel)object;

		long primaryKey = accountEntryUserRel.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		AccountEntryUserRelModelImpl accountEntryUserRelModelImpl = this;

		accountEntryUserRelModelImpl._originalAccountEntryId =
			accountEntryUserRelModelImpl._accountEntryId;

		accountEntryUserRelModelImpl._setOriginalAccountEntryId = false;

		accountEntryUserRelModelImpl._originalAccountUserId =
			accountEntryUserRelModelImpl._accountUserId;

		accountEntryUserRelModelImpl._setOriginalAccountUserId = false;

		accountEntryUserRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AccountEntryUserRel> toCacheModel() {
		AccountEntryUserRelCacheModel accountEntryUserRelCacheModel =
			new AccountEntryUserRelCacheModel();

		accountEntryUserRelCacheModel.mvccVersion = getMvccVersion();

		accountEntryUserRelCacheModel.accountEntryUserRelId =
			getAccountEntryUserRelId();

		accountEntryUserRelCacheModel.companyId = getCompanyId();

		accountEntryUserRelCacheModel.accountEntryId = getAccountEntryId();

		accountEntryUserRelCacheModel.accountUserId = getAccountUserId();

		return accountEntryUserRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AccountEntryUserRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AccountEntryUserRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountEntryUserRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AccountEntryUserRel)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<AccountEntryUserRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AccountEntryUserRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountEntryUserRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AccountEntryUserRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AccountEntryUserRel>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _accountEntryUserRelId;
	private long _companyId;
	private long _accountEntryId;
	private long _originalAccountEntryId;
	private boolean _setOriginalAccountEntryId;
	private long _accountUserId;
	private long _originalAccountUserId;
	private boolean _setOriginalAccountUserId;
	private long _columnBitmask;
	private AccountEntryUserRel _escapedModel;

}