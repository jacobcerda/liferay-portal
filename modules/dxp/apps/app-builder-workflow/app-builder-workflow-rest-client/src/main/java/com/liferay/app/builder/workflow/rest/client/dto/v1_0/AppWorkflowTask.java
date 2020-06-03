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

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTask implements Cloneable {

	public static AppWorkflowTask toDTO(String json) {
		return AppWorkflowTaskSerDes.toDTO(json);
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppId(UnsafeSupplier<Long, Exception> appIdUnsafeSupplier) {
		try {
			appId = appIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long appId;

	public AppWorkflowAction[] getAppWorkflowActions() {
		return appWorkflowActions;
	}

	public void setAppWorkflowActions(AppWorkflowAction[] appWorkflowActions) {
		this.appWorkflowActions = appWorkflowActions;
	}

	public void setAppWorkflowActions(
		UnsafeSupplier<AppWorkflowAction[], Exception>
			appWorkflowActionsUnsafeSupplier) {

		try {
			appWorkflowActions = appWorkflowActionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowAction[] appWorkflowActions;

	public Long[] getDataLayoutIds() {
		return dataLayoutIds;
	}

	public void setDataLayoutIds(Long[] dataLayoutIds) {
		this.dataLayoutIds = dataLayoutIds;
	}

	public void setDataLayoutIds(
		UnsafeSupplier<Long[], Exception> dataLayoutIdsUnsafeSupplier) {

		try {
			dataLayoutIds = dataLayoutIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] dataLayoutIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	@Override
	public AppWorkflowTask clone() throws CloneNotSupportedException {
		return (AppWorkflowTask)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTask)) {
			return false;
		}

		AppWorkflowTask appWorkflowTask = (AppWorkflowTask)object;

		return Objects.equals(toString(), appWorkflowTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowTaskSerDes.toJSON(this);
	}

}