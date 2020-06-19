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

import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useCallback, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import {useSelectItem} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import SaveFragmentCompositionModal from '../floating-toolbar/SaveFragmentCompositionModal';
import Section from './Section';
import hasDropZoneChild from './hasDropZoneChild';

const SectionWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const [
			openSaveFragmentCompositionModal,
			setOpenSaveFragmentCompositionModal,
		] = useState(false);
		const {observer, onClose} = useModal({
			onClose: () => {
				if (isMounted()) {
					setOpenSaveFragmentCompositionModal(false);
				}
			},
		});

		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
		const showFloatingToolbar = useSelector(selectShowFloatingToolbar);
		const selectItem = useSelectItem();

		const [setRef, itemElement] = useSetRef(ref);

		const handleButtonClick = useCallback(
			(id) => {
				if (
					id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id
				) {
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
							selectItem,
						})
					);
				}
				else if (
					id ===
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
						.id
				) {
					setOpenSaveFragmentCompositionModal(true);
				}
			},
			[dispatch, item.itemId, segmentsExperienceId, selectItem]
		);

		const buttons = [];

		if (!hasDropZoneChild(item, layoutData)) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem);
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
			);
		}

		buttons.push(
			LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
		);

		return (
			<Topper
				item={item}
				itemElement={itemElement}
				layoutData={layoutData}
			>
				<Section
					className={classNames('page-editor__section', {
						empty: !item.children.length,
					})}
					item={item}
					ref={setRef}
				>
					{showFloatingToolbar && (
						<FloatingToolbar
							buttons={buttons}
							item={item}
							itemElement={itemElement}
							onButtonClick={handleButtonClick}
						/>
					)}

					{children}

					{openSaveFragmentCompositionModal && (
						<SaveFragmentCompositionModal
							errorMessage={''}
							itemId={item.itemId}
							observer={observer}
							onClose={onClose}
							onErrorDismiss={() => true}
						/>
					)}
				</Section>
			</Topper>
		);
	}
);

SectionWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default SectionWithControls;
