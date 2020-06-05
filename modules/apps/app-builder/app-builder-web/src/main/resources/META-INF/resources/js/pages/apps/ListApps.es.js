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

import ClayLabel from '@clayui/label';
import {compile} from 'path-to-regexp';
import React, {useContext} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import useDeployApp from '../../hooks/useDeployApp.es';
import {confirmDelete} from '../../utils/client.es';
import {fromNow} from '../../utils/time.es';
import {concatValues} from '../../utils/utils.es';
import {
	COLUMNS,
	DEPLOYMENT_ACTION,
	DEPLOYMENT_TYPES,
	STATUSES,
} from './constants.es';

export default ({
	editPath = [
		`/:objectType/:dataDefinitionId(\\d+)/apps/deploy`,
		`/:objectType/:dataDefinitionId(\\d+)/apps/:appId(\\d+)`,
	],
	listViewProps = {},
	match: {
		params: {dataDefinitionId, objectType},
	},
}) => {
	const {getStandaloneURL} = useContext(AppContext);
	const {deployApp, undeployApp} = useDeployApp();

	const ACTIONS = [
		{
			action: (app) => (app.active ? undeployApp(app) : deployApp(app)),
			name: ({active}) =>
				DEPLOYMENT_ACTION[active ? 'undeploy' : 'deploy'],
		},
		{
			action: ({id}) =>
				Promise.resolve(window.open(getStandaloneURL(id), '_blank')),
			name: Liferay.Language.get('open-standalone-app'),
			show: ({appDeployments}) =>
				appDeployments.some(({type}) => type === 'standalone'),
		},
		{
			action: confirmDelete('/o/app-builder/v1.0/apps/'),
			name: Liferay.Language.get('delete'),
		},
	];

	const newAppLink = compile(editPath[0])({dataDefinitionId, objectType});

	const ADD_BUTTON = () => (
		<Button
			className="nav-btn nav-btn-monospaced"
			href={newAppLink}
			symbol="plus"
			tooltip={Liferay.Language.get('new-app')}
		/>
	);

	const EMPTY_STATE = {
		button: () => (
			<Button displayType="secondary" href={newAppLink}>
				{Liferay.Language.get('new-app')}
			</Button>
		),
		description: Liferay.Language.get(
			'select-the-form-and-table-view-you-want-and-deploy-your-app-as-a-widget-standalone-or-place-it-in-the-product-menu'
		),
		title: Liferay.Language.get('there-are-no-apps-yet'),
	};

	const ENDPOINT = `/o/app-builder/v1.0/data-definitions/${dataDefinitionId}/apps`;

	return (
		<ListView
			actions={ACTIONS}
			addButton={ADD_BUTTON}
			columns={COLUMNS}
			emptyState={EMPTY_STATE}
			endpoint={ENDPOINT}
			{...listViewProps}
		>
			{(app) => ({
				...app,
				dateCreated: fromNow(app.dateCreated),
				dateModified: fromNow(app.dateModified),
				name: (
					<Link
						to={compile(editPath[1])({
							appId: app.id,
							dataDefinitionId,
							objectType,
						})}
					>
						{app.name.en_US}
					</Link>
				),
				nameText: app.name.en_US,
				status: (
					<ClayLabel
						displayType={app.active ? 'success' : 'secondary'}
					>
						{STATUSES[app.active ? 'active' : 'inactive']}
					</ClayLabel>
				),
				type: concatValues(
					app.appDeployments.map(({type}) => DEPLOYMENT_TYPES[type])
				),
			})}
		</ListView>
	);
};
