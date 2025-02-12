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

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LAYOUT_DATA,
} from '../actions/types';

export const INITIAL_STATE = {
	items: {},
};

export default function layoutDataReducer(layoutData = INITIAL_STATE, action) {
	switch (action.type) {
		case UPDATE_COL_SIZE:
		case UPDATE_LAYOUT_DATA:
		case ADD_FRAGMENT_ENTRY_LINKS:
		case ADD_ITEM:
		case DELETE_ITEM:
		case DUPLICATE_ITEM:
		case MOVE_ITEM:
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
		case UPDATE_ITEM_CONFIG:
			return action.layoutData;

		default:
			return layoutData;
	}
}
