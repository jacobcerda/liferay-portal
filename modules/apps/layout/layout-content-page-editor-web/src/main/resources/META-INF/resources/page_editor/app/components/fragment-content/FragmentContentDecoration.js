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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import {useLayoutEffect, useMemo} from 'react';

import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {config} from '../../config/index';
import createSelectEditableValue from '../../selectors/selectEditableValue';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import {useToControlsId} from '../CollectionItemContext';
import {
	useHoveredItemId,
	useHoveredItemType,
	useIsActive,
	useIsHovered,
} from '../Controls';
import {useEditableDecoration} from './EditableDecorationContext';
import {EDITABLE_DECORATION_CLASS_NAMES} from './EditableDecorationMask';
import getAllEditables from './getAllEditables';
import getEditableElementId from './getEditableElementId';
import getEditableUniqueId from './getEditableUniqueId';
import isMapped from './isMapped';

export default function FragmentContentDecoration({
	editableElement,
	element,
	fragmentEntryLinkId,
	itemId,
}) {
	const hoveredItemId = useHoveredItemId();
	const isHovered = useIsHovered();
	const hoveredItemType = useHoveredItemType();
	const isActive = useIsActive();
	const toControlsId = useToControlsId();
	const languageId = useSelector(state => state.languageId);
	const prefixedSegmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const editableUniqueId = getEditableUniqueId(
		fragmentEntryLinkId,
		getEditableElementId(editableElement)
	);

	const editableUniqueControlsId = toControlsId(editableUniqueId);

	const editableValue = useSelector(state =>
		createSelectEditableValue(
			state,
			fragmentEntryLinkId,
			getEditableElementId(editableElement)
		)
	);

	const {
		registerElement,
		unregisterElement,
		updateClassName,
	} = useEditableDecoration();

	const className = classNames({
		[EDITABLE_DECORATION_CLASS_NAMES.active]: isActive(editableUniqueId),

		[EDITABLE_DECORATION_CLASS_NAMES.hovered]: useMemo(() => {
			if (hoveredItemType === ITEM_TYPES.editable) {
				return isHovered(editableUniqueId);
			}
			else if (hoveredItemType === ITEM_TYPES.mappedContent) {
				return (
					`${editableValue.classNameId}-${editableValue.classPK}` ===
					hoveredItemId
				);
			}

			return false;
		}, [
			editableUniqueId,
			editableValue.classNameId,
			editableValue.classPK,
			hoveredItemId,
			hoveredItemType,
			isHovered,
		]),

		[EDITABLE_DECORATION_CLASS_NAMES.mapped]: useMemo(
			() => isMapped(editableValue),
			[editableValue]
		),

		[EDITABLE_DECORATION_CLASS_NAMES.highlighted]: useMemo(
			() =>
				[
					itemId,
					...getAllEditables(element).map(editable =>
						getEditableUniqueId(
							fragmentEntryLinkId,
							getEditableElementId(editable.element)
						)
					),
				].some(_itemId => isActive(_itemId)),
			[element, fragmentEntryLinkId, isActive, itemId]
		),

		[EDITABLE_DECORATION_CLASS_NAMES.translated]: useMemo(
			() =>
				config.defaultLanguageId !== languageId &&
				(editableValue[languageId] ||
					(editableValue[prefixedSegmentsExperienceId] &&
						editableValue[prefixedSegmentsExperienceId][
							languageId
						])),
			[editableValue, languageId, prefixedSegmentsExperienceId]
		),
	});

	useLayoutEffect(() => {
		if (className) {
			registerElement(editableUniqueControlsId, editableElement);
			updateClassName(editableUniqueControlsId, className);
		}
		else {
			unregisterElement(editableUniqueControlsId);
		}
	}, [
		className,
		editableElement,
		editableUniqueControlsId,
		registerElement,
		unregisterElement,
		updateClassName,
	]);

	useLayoutEffect(
		() => () => {
			unregisterElement(editableUniqueControlsId);
		},
		[editableUniqueControlsId, unregisterElement]
	);

	return null;
}

FragmentContentDecoration.propTypes = {
	editableElement: PropTypes.instanceOf(HTMLElement).isRequired,
	element: PropTypes.instanceOf(HTMLElement).isRequired,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired,
};
