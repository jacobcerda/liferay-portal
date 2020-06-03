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

import ClayButton from '@clayui/button';
import React, {useState} from 'react';

import Popover from '../../components/popover/Popover.es';
import {useNavigation} from '../../hooks/useNavigation.es';
import SelectObjects from './SelectObjectsDropDown.es';

const NewAppPopover = (
	{alignElement, history, onCancel, visible},
	forwardRef
) => {
	const [selectedObject, setSelectedObject] = useState({});

	const navigation = useNavigation(history);

	const onClick = () => {
		navigation.push(`/standard/${selectedObject.id}/deploy`);
	};

	return (
		<>
			<Popover
				alignElement={alignElement}
				className="apps-popover mw-100"
				content={() => (
					<>
						<label>{Liferay.Language.get('object')}</label>

						<SelectObjects
							alignElement={alignElement}
							onSelect={setSelectedObject}
							selectedValue={selectedObject.name}
							visible={visible}
						/>
					</>
				)}
				footer={() => (
					<div className="border-top mt-3 p-3" style={{width: 450}}>
						<div className="d-flex justify-content-end">
							<ClayButton
								className="mr-3"
								displayType="secondary"
								onClick={() => {
									setSelectedObject({});

									onCancel();
								}}
								small
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!selectedObject.id}
								onClick={onClick}
								small
							>
								{Liferay.Language.get('continue')}
							</ClayButton>
						</div>
					</div>
				)}
				ref={forwardRef}
				showArrow={false}
				title={() => (
					<>
						<h4 className="mb-3">
							{Liferay.Language.get('new-app')}
						</h4>

						<span className="font-weight-light text-secondary">
							{Liferay.Language.get(
								'create-an-app-to-manage-the-data-of-an-object'
							)}
						</span>
					</>
				)}
				visible={visible}
			/>
		</>
	);
};

export default React.forwardRef(NewAppPopover);
