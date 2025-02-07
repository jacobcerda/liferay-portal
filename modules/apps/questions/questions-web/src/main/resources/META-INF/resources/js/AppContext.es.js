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

import React, {createContext, useEffect, useState} from 'react';

import {client, hasListPermissionsQuery} from './utils/client.es';

const AppContext = createContext({});

const AppContextProvider = ({children, ...context}) => {
	const [canCreateThread, setCanCreateThread] = useState(false);
	const [section, setSection] = useState({});

	useEffect(() => {
		client
			.query({
				query: hasListPermissionsQuery,
				variables: {
					siteKey: context.siteKey,
				},
			})
			.then(({data: {messageBoardThreads}}) => {
				setCanCreateThread(
					Boolean(messageBoardThreads.actions['create'])
				);
			});
	}, [context.siteKey]);

	return (
		<AppContext.Provider
			value={{
				...context,
				canCreateThread,
				section,
				setSection,
			}}
		>
			{children}
		</AppContext.Provider>
	);
};

export {AppContext, AppContextProvider};
