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

package com.liferay.dynamic.data.mapping.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReportTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceReportPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMFormInstanceReportTableReferenceDefinition
	implements TableReferenceDefinition<DDMFormInstanceReportTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMFormInstanceReportTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			DDMFormInstanceReportTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			DDMFormInstanceReportTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			DDMFormInstanceReportTable.INSTANCE.createDate,
			DDMFormInstanceReportTable.INSTANCE.modifiedDate
		).singleColumnReference(
			DDMFormInstanceReportTable.INSTANCE.formInstanceId,
			DDMFormInstanceTable.INSTANCE.formInstanceId
		).nonreferenceColumn(
			DDMFormInstanceReportTable.INSTANCE.data
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstanceReportPersistence;
	}

	@Override
	public DDMFormInstanceReportTable getTable() {
		return DDMFormInstanceReportTable.INSTANCE;
	}

	@Reference
	private DDMFormInstanceReportPersistence _ddmFormInstanceReportPersistence;

}