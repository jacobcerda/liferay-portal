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

package com.liferay.app.builder.workflow.model.impl;

import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AppBuilderWorkflowTaskLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderWorkflowTaskLinkCacheModel
	implements CacheModel<AppBuilderWorkflowTaskLink>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AppBuilderWorkflowTaskLinkCacheModel)) {
			return false;
		}

		AppBuilderWorkflowTaskLinkCacheModel
			appBuilderWorkflowTaskLinkCacheModel =
				(AppBuilderWorkflowTaskLinkCacheModel)obj;

		if ((appBuilderWorkflowTaskLinkId ==
				appBuilderWorkflowTaskLinkCacheModel.
					appBuilderWorkflowTaskLinkId) &&
			(mvccVersion == appBuilderWorkflowTaskLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, appBuilderWorkflowTaskLinkId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", appBuilderWorkflowTaskLinkId=");
		sb.append(appBuilderWorkflowTaskLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", ddmStructureLayoutId=");
		sb.append(ddmStructureLayoutId);
		sb.append(", workflowTaskName=");
		sb.append(workflowTaskName);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderWorkflowTaskLink toEntityModel() {
		AppBuilderWorkflowTaskLinkImpl appBuilderWorkflowTaskLinkImpl =
			new AppBuilderWorkflowTaskLinkImpl();

		appBuilderWorkflowTaskLinkImpl.setMvccVersion(mvccVersion);
		appBuilderWorkflowTaskLinkImpl.setAppBuilderWorkflowTaskLinkId(
			appBuilderWorkflowTaskLinkId);
		appBuilderWorkflowTaskLinkImpl.setCompanyId(companyId);
		appBuilderWorkflowTaskLinkImpl.setAppBuilderAppId(appBuilderAppId);
		appBuilderWorkflowTaskLinkImpl.setDdmStructureLayoutId(
			ddmStructureLayoutId);

		if (workflowTaskName == null) {
			appBuilderWorkflowTaskLinkImpl.setWorkflowTaskName("");
		}
		else {
			appBuilderWorkflowTaskLinkImpl.setWorkflowTaskName(
				workflowTaskName);
		}

		appBuilderWorkflowTaskLinkImpl.resetOriginalValues();

		return appBuilderWorkflowTaskLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		appBuilderWorkflowTaskLinkId = objectInput.readLong();

		companyId = objectInput.readLong();

		appBuilderAppId = objectInput.readLong();

		ddmStructureLayoutId = objectInput.readLong();
		workflowTaskName = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(appBuilderWorkflowTaskLinkId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(appBuilderAppId);

		objectOutput.writeLong(ddmStructureLayoutId);

		if (workflowTaskName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(workflowTaskName);
		}
	}

	public long mvccVersion;
	public long appBuilderWorkflowTaskLinkId;
	public long companyId;
	public long appBuilderAppId;
	public long ddmStructureLayoutId;
	public String workflowTaskName;

}