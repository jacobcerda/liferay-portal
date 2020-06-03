/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderWorkflowTaskLinkSoap implements Serializable {

	public static AppBuilderWorkflowTaskLinkSoap toSoapModel(
		AppBuilderWorkflowTaskLink model) {

		AppBuilderWorkflowTaskLinkSoap soapModel =
			new AppBuilderWorkflowTaskLinkSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAppBuilderWorkflowTaskLinkId(
			model.getAppBuilderWorkflowTaskLinkId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAppBuilderAppId(model.getAppBuilderAppId());
		soapModel.setDdmStructureLayoutId(model.getDdmStructureLayoutId());
		soapModel.setWorkflowTaskName(model.getWorkflowTaskName());

		return soapModel;
	}

	public static AppBuilderWorkflowTaskLinkSoap[] toSoapModels(
		AppBuilderWorkflowTaskLink[] models) {

		AppBuilderWorkflowTaskLinkSoap[] soapModels =
			new AppBuilderWorkflowTaskLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderWorkflowTaskLinkSoap[][] toSoapModels(
		AppBuilderWorkflowTaskLink[][] models) {

		AppBuilderWorkflowTaskLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AppBuilderWorkflowTaskLinkSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new AppBuilderWorkflowTaskLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderWorkflowTaskLinkSoap[] toSoapModels(
		List<AppBuilderWorkflowTaskLink> models) {

		List<AppBuilderWorkflowTaskLinkSoap> soapModels =
			new ArrayList<AppBuilderWorkflowTaskLinkSoap>(models.size());

		for (AppBuilderWorkflowTaskLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AppBuilderWorkflowTaskLinkSoap[soapModels.size()]);
	}

	public AppBuilderWorkflowTaskLinkSoap() {
	}

	public long getPrimaryKey() {
		return _appBuilderWorkflowTaskLinkId;
	}

	public void setPrimaryKey(long pk) {
		setAppBuilderWorkflowTaskLinkId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAppBuilderWorkflowTaskLinkId() {
		return _appBuilderWorkflowTaskLinkId;
	}

	public void setAppBuilderWorkflowTaskLinkId(
		long appBuilderWorkflowTaskLinkId) {

		_appBuilderWorkflowTaskLinkId = appBuilderWorkflowTaskLinkId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	public void setAppBuilderAppId(long appBuilderAppId) {
		_appBuilderAppId = appBuilderAppId;
	}

	public long getDdmStructureLayoutId() {
		return _ddmStructureLayoutId;
	}

	public void setDdmStructureLayoutId(long ddmStructureLayoutId) {
		_ddmStructureLayoutId = ddmStructureLayoutId;
	}

	public String getWorkflowTaskName() {
		return _workflowTaskName;
	}

	public void setWorkflowTaskName(String workflowTaskName) {
		_workflowTaskName = workflowTaskName;
	}

	private long _mvccVersion;
	private long _appBuilderWorkflowTaskLinkId;
	private long _companyId;
	private long _appBuilderAppId;
	private long _ddmStructureLayoutId;
	private String _workflowTaskName;

}