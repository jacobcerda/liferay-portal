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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.WebDAVProps;
import com.liferay.portal.kernel.model.WebDAVPropsModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the WebDAVProps service. Represents a row in the &quot;WebDAVProps&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>WebDAVPropsModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WebDAVPropsImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsImpl
 * @generated
 */
public class WebDAVPropsModelImpl
	extends BaseModelImpl<WebDAVProps> implements WebDAVPropsModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a web dav props model instance should use the <code>WebDAVProps</code> interface instead.
	 */
	public static final String TABLE_NAME = "WebDAVProps";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"webDavPropsId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"props", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("webDavPropsId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("props", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table WebDAVProps (mvccVersion LONG default 0 not null,webDavPropsId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,props TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table WebDAVProps";

	public static final String ORDER_BY_JPQL =
		" ORDER BY webDAVProps.webDavPropsId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY WebDAVProps.webDavPropsId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.WebDAVProps"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.WebDAVProps"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.WebDAVProps"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long WEBDAVPROPSID_COLUMN_BITMASK = 4L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.WebDAVProps"));

	public WebDAVPropsModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _webDavPropsId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setWebDavPropsId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _webDavPropsId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return WebDAVProps.class;
	}

	@Override
	public String getModelClassName() {
		return WebDAVProps.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<WebDAVProps, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<WebDAVProps, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WebDAVProps, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((WebDAVProps)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<WebDAVProps, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<WebDAVProps, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(WebDAVProps)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<WebDAVProps, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<WebDAVProps, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, WebDAVProps>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			WebDAVProps.class.getClassLoader(), WebDAVProps.class,
			ModelWrapper.class);

		try {
			Constructor<WebDAVProps> constructor =
				(Constructor<WebDAVProps>)proxyClass.getConstructor(
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

	private static final Map<String, Function<WebDAVProps, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<WebDAVProps, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<WebDAVProps, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<WebDAVProps, Object>>();
		Map<String, BiConsumer<WebDAVProps, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<WebDAVProps, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", WebDAVProps::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<WebDAVProps, Long>)WebDAVProps::setMvccVersion);
		attributeGetterFunctions.put(
			"webDavPropsId", WebDAVProps::getWebDavPropsId);
		attributeSetterBiConsumers.put(
			"webDavPropsId",
			(BiConsumer<WebDAVProps, Long>)WebDAVProps::setWebDavPropsId);
		attributeGetterFunctions.put("companyId", WebDAVProps::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<WebDAVProps, Long>)WebDAVProps::setCompanyId);
		attributeGetterFunctions.put("createDate", WebDAVProps::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<WebDAVProps, Date>)WebDAVProps::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", WebDAVProps::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<WebDAVProps, Date>)WebDAVProps::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", WebDAVProps::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<WebDAVProps, Long>)WebDAVProps::setClassNameId);
		attributeGetterFunctions.put("classPK", WebDAVProps::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK", (BiConsumer<WebDAVProps, Long>)WebDAVProps::setClassPK);
		attributeGetterFunctions.put("props", WebDAVProps::getProps);
		attributeSetterBiConsumers.put(
			"props", (BiConsumer<WebDAVProps, String>)WebDAVProps::setProps);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getWebDavPropsId() {
		return _webDavPropsId;
	}

	@Override
	public void setWebDavPropsId(long webDavPropsId) {
		_webDavPropsId = webDavPropsId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@Override
	public String getProps() {
		if (_props == null) {
			return "";
		}
		else {
			return _props;
		}
	}

	@Override
	public void setProps(String props) {
		_props = props;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), WebDAVProps.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public WebDAVProps toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, WebDAVProps>
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
		WebDAVPropsImpl webDAVPropsImpl = new WebDAVPropsImpl();

		webDAVPropsImpl.setMvccVersion(getMvccVersion());
		webDAVPropsImpl.setWebDavPropsId(getWebDavPropsId());
		webDAVPropsImpl.setCompanyId(getCompanyId());
		webDAVPropsImpl.setCreateDate(getCreateDate());
		webDAVPropsImpl.setModifiedDate(getModifiedDate());
		webDAVPropsImpl.setClassNameId(getClassNameId());
		webDAVPropsImpl.setClassPK(getClassPK());
		webDAVPropsImpl.setProps(getProps());

		webDAVPropsImpl.resetOriginalValues();

		return webDAVPropsImpl;
	}

	@Override
	public int compareTo(WebDAVProps webDAVProps) {
		long primaryKey = webDAVProps.getPrimaryKey();

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

		if (!(object instanceof WebDAVProps)) {
			return false;
		}

		WebDAVProps webDAVProps = (WebDAVProps)object;

		long primaryKey = webDAVProps.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		WebDAVPropsModelImpl webDAVPropsModelImpl = this;

		webDAVPropsModelImpl._setModifiedDate = false;

		webDAVPropsModelImpl._originalClassNameId =
			webDAVPropsModelImpl._classNameId;

		webDAVPropsModelImpl._setOriginalClassNameId = false;

		webDAVPropsModelImpl._originalClassPK = webDAVPropsModelImpl._classPK;

		webDAVPropsModelImpl._setOriginalClassPK = false;

		webDAVPropsModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<WebDAVProps> toCacheModel() {
		WebDAVPropsCacheModel webDAVPropsCacheModel =
			new WebDAVPropsCacheModel();

		webDAVPropsCacheModel.mvccVersion = getMvccVersion();

		webDAVPropsCacheModel.webDavPropsId = getWebDavPropsId();

		webDAVPropsCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			webDAVPropsCacheModel.createDate = createDate.getTime();
		}
		else {
			webDAVPropsCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			webDAVPropsCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			webDAVPropsCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		webDAVPropsCacheModel.classNameId = getClassNameId();

		webDAVPropsCacheModel.classPK = getClassPK();

		webDAVPropsCacheModel.props = getProps();

		String props = webDAVPropsCacheModel.props;

		if ((props != null) && (props.length() == 0)) {
			webDAVPropsCacheModel.props = null;
		}

		return webDAVPropsCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<WebDAVProps, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<WebDAVProps, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WebDAVProps, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((WebDAVProps)this));
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
		Map<String, Function<WebDAVProps, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<WebDAVProps, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WebDAVProps, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((WebDAVProps)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, WebDAVProps>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _webDavPropsId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _props;
	private long _columnBitmask;
	private WebDAVProps _escapedModel;

}