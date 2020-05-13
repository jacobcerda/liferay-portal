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

import Card from '../../../../src/main/resources/META-INF/resources/js/components/card/Card.es';

const props = {
	fieldName: 'name',
	totalEntries: 10,
	type: 'radio',
};

describe('Card', () => {
	afterEach(cleanup);

	it('shows number of entries in the header', () => {
		const {container} = render(<Card {...props} />);
		expect(container.querySelector('.card-text').title).toBe('10 entries');
	});

	it('shows "there are not entries" when totalEntries is zero', () => {
		const {getAllByText} = render(<Card {...props} totalEntries={0} />);
		expect(getAllByText('there-are-no-entries').length).toBe(2);
	});
});
