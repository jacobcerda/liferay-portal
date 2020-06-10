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

import {PropTypes} from 'prop-types';
import React from 'react';

import MappingField from './components/MappingField';

function SeoMapping({
	description,
	fields,
	portletNamespace,
	selectedSource,
	title,
}) {
	return (
		<>
			<MappingField
				fields={fields}
				label={Liferay.Language.get('html-title')}
				name={`${portletNamespace}title`}
				selectedField={
					fields.find(({key}) => key === title) || fields[0]
				}
				selectedSource={selectedSource}
			/>

			<MappingField
				fields={fields}
				label={Liferay.Language.get('description')}
				name={`${portletNamespace}description`}
				selectedField={
					fields.find(({key}) => key === description) || fields[0]
				}
				selectedSource={selectedSource}
			/>
		</>
	);
}

SeoMapping.propTypes = {
	description: PropTypes.string.isRequired,
	fields: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	title: PropTypes.string.isRequired,
};

export default function (props) {
	return (
		<SeoMapping
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
