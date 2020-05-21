/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {cleanup, render, wait} from '@testing-library/react';
import React from 'react';

import Navigation from '../../../src/main/resources/META-INF/resources/js/components/Navigation';
import ConnectionContext from '../../../src/main/resources/META-INF/resources/js/context/ConnectionContext';
import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/store';

import '@testing-library/jest-dom/extend-expect';

const mockApi = {
	getHistoricalReads: jest.fn(() =>
		Promise.resolve({
			analyticsReportsHistoricalReads: {
				histogram: [
					{
						key: '2020-01-27T00:00',
						previousKey: '2020-01-20T00:00',
						previousValue: 22.0,
						value: 33.0,
					},
					{
						key: '2020-01-28T00:00',
						previousKey: '2020-01-21T00:00',
						previousValue: 23.0,
						value: 33.0,
					},
					{
						key: '2020-01-29T00:00',
						previousKey: '2020-01-22T00:00',
						previousValue: 24.0,
						value: 34.0,
					},
					{
						key: '2020-01-30T00:00',
						previousKey: '2020-01-23T00:00',
						previousValue: 25.0,
						value: 33.0,
					},
					{
						key: '2020-01-31T00:00',
						previousKey: '2020-01-24T00:00',
						previousValue: 26.0,
						value: 32.0,
					},
					{
						key: '2020-02-01T00:00',
						previousKey: '2020-01-25T00:00',
						previousValue: 27.0,
						value: 31.0,
					},
					{
						key: '2020-02-02T00:00',
						previousKey: '2020-01-26T00:00',
						previousValue: 28.0,
						value: 30.0,
					},
				],
				previousValue: 175.0,
				value: 226.0,
			},
		})
	),
	getHistoricalViews: jest.fn(() =>
		Promise.resolve({
			analyticsReportsHistoricalViews: {
				histogram: [
					{
						key: '2020-01-27T00:00',
						previousKey: '2020-01-20T00:00',
						previousValue: 22.0,
						value: 32.0,
					},
					{
						key: '2020-01-28T00:00',
						previousKey: '2020-01-21T00:00',
						previousValue: 23.0,
						value: 33.0,
					},
					{
						key: '2020-01-29T00:00',
						previousKey: '2020-01-22T00:00',
						previousValue: 24.0,
						value: 34.0,
					},
					{
						key: '2020-01-30T00:00',
						previousKey: '2020-01-23T00:00',
						previousValue: 25.0,
						value: 33.0,
					},
					{
						key: '2020-01-31T00:00',
						previousKey: '2020-01-24T00:00',
						previousValue: 26.0,
						value: 32.0,
					},
					{
						key: '2020-02-01T00:00',
						previousKey: '2020-01-25T00:00',
						previousValue: 27.0,
						value: 31.0,
					},
					{
						key: '2020-02-02T00:00',
						previousKey: '2020-01-26T00:00',
						previousValue: 28.0,
						value: 30.0,
					},
				],
				previousValue: 175.0,
				value: 225.0,
			},
		})
	),
	getTotalReads: jest.fn(() => {
		return Promise.resolve(999);
	}),
	getTotalViews: jest.fn(() => {
		return Promise.resolve(12345);
	}),
	getTrafficSourceDetails: jest.fn(() => {
		return Promise.resolve({
			data: {
				helpMessage:
					'This number refers to the volume of people that find your page through a search engine.',
				keywords: [
					{
						keyword: 'commerce',
						position: 1,
						searchVolume: 12300,
						traffic: 90000,
					},
					{
						keyword: 'e-commerce',
						position: 2,
						searchVolume: 9800,
						traffic: 14800,
					},
					{
						keyword: 'what is commerce',
						position: 3,
						searchVolume: 9500,
						traffic: 14000,
					},
					{
						keyword: 'what is e-commerce',
						position: 4,
						searchVolume: 8700,
						traffic: 12100,
					},
					{
						keyword:
							'commerce definition for new business strategy',
						position: 5,
						searchVolume: 7100,
						traffic: 10100,
					},
				],
				name: 'organic',
				share: 0,
				title: 'Organic',
				value: 0,
			},
			view: 'traffic-source-detail',
		});
	}),
};

const mockTimeSpanOptions = [
	{
		key: 'last-30-days',
		label: 'Last 30 Days',
	},
	{
		key: 'last-7-days',
		label: 'Last 7 Days',
	},
	{
		key: 'last-24-hours',
		label: 'Last 24 Hours',
	},
];

const mockTrafficSources = [
	{
		helpMessage: 'Testing Help Message',
		name: 'testing',
		title: 'Testing',
		value: 32178,
	},
	{
		helpMessage: 'Second Testing Help Message',
		name: 'second-testing',
		title: 'Second Testing',
	},
];

describe('Navigation', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('displays an alert error message if there is no valid connection', () => {
		const testProps = {
			authorName: 'John Tester',
			defaultTimeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			defaultTimeSpanKey: 'last-7-days',
			languageTag: 'en-US',
			pagePublishDate: 1581957977840,
			pageTitle: 'A testing page',
		};

		const {getByText} = render(
			<ConnectionContext.Provider
				value={{
					validAnalyticsConnection: false,
				}}
			>
				<Navigation
					api={{}}
					authorName={testProps.authorName}
					defaultTimeRange={testProps.defaultTimeRange}
					defaultTimeSpanKey={testProps.defaultTimeSpanKey}
					languageTag={testProps.languageTag}
					pagePublishDate={testProps.pagePublishDate}
					pageTitle={testProps.pageTitle}
					timeSpanOptions={mockTimeSpanOptions}
					trafficSources={mockTrafficSources}
				/>
			</ConnectionContext.Provider>
		);

		expect(getByText('an-unexpected-error-occurred')).toBeInTheDocument();
	});

	it('displays an alert warning message if some data is temporarily unavailable', async () => {
		const testProps = {
			authorName: 'John Tester',
			defaultTimeRange: {endDate: '2020-01-27', startDate: '2020-02-02'},
			defaultTimeSpanKey: 'last-7-days',
			languageTag: 'en-US',
			pagePublishDate: 1581957977840,
			pageTitle: 'A testing page',
		};

		const {getByText} = render(
			<StoreContextProvider>
				<Navigation
					api={mockApi}
					authorName={testProps.authorName}
					defaultTimeRange={testProps.defaultTimeRange}
					defaultTimeSpanKey={testProps.defaultTimeSpanKey}
					languageTag={testProps.languageTag}
					pagePublishDate={testProps.pagePublishDate}
					pageTitle={testProps.pageTitle}
					timeSpanOptions={mockTimeSpanOptions}
					trafficSources={mockTrafficSources}
				/>
			</StoreContextProvider>
		);

		await wait(() => expect(mockApi.getTotalReads).toHaveBeenCalled());
		await wait(() => expect(mockApi.getTotalViews).toHaveBeenCalled());

		await wait(() => expect(mockApi.getHistoricalReads).toHaveBeenCalled());
		await wait(() => expect(mockApi.getHistoricalViews).toHaveBeenCalled());

		expect(
			getByText('some-data-is-temporarily-unavailable')
		).toBeInTheDocument();
	});
});
