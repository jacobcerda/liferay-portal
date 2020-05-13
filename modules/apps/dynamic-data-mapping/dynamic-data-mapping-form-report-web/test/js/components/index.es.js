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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Index from '../../../src/main/resources/META-INF/resources/js/index.es';

const props = {
	data: JSON.stringify('{field1: {values: {option1: 1}, type:"radio"}'),
	fields: [
		{name: 'field1', type: 'radio'},
		{name: 'field2', type: 'radio'},
		{name: 'field3', type: 'radio'},
	],
};

describe('index', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(<Index {...props} />);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders nothing when there is no data', () => {
		const {asFragment} = render(<Index {...props} data={null} />);
		expect(asFragment()).toMatchSnapshot();
	});
});
