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

import React from 'react';

import Card from './components/card/Card.es';
import PieChart from './components/chart/pie/PieChart.es';
import toDataArray, {sumTotalEntries} from './utils/data.es';

const chartFactory = (type, values, totalEntries) => {
	if (type === 'radio') {
		return (
			<PieChart data={toDataArray(values)} totalEntries={totalEntries} />
		);
	}

	return null;
};

export default ({data, fields}) =>
	fields.map(({name, type}, index) => {
		const {values = {}} = data[name] || {};
		const totalEntries = sumTotalEntries(values);
		const chart = chartFactory(type, values, totalEntries);

		if (chart === null) {
			return null;
		}

		return (
			<Card
				fieldName={name}
				key={index}
				totalEntries={totalEntries}
				type={type}
			>
				{chart}
			</Card>
		);
	});
