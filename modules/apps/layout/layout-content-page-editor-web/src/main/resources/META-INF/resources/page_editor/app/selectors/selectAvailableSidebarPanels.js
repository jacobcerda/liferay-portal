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

import {AVAILABLE_PANELS} from './selectAvailablePanels';

/**
 * @param {{ [panelId: string]: object }} sidebarPanels
 */
export default function selectAvailableSidebarPanels(sidebarPanels) {

	/**
	 * @param {{ permissions: import("../../types/ActionKeys").ActionKeysMap }} state
	 */
	return function ({permissions}) {
		if (permissions.LOCKED_SEGMENTS_EXPERIMENT || !permissions.UPDATE) {
			const availableSidebarPanels = {};

			AVAILABLE_PANELS.forEach((panelId) => {
				availableSidebarPanels[panelId] = sidebarPanels[panelId];
			});

			return availableSidebarPanels;
		}

		return sidebarPanels;
	};
}
