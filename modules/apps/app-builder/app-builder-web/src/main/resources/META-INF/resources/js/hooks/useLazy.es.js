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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, lazy} from 'react';

import useLoader from './useLoader.es';

export default function useLazy() {
	const load = useLoader();

	return ({module, props, ...otherProps}) => {
		const Component = lazy(() => load(module));

		return (
			<ErrorBoundary {...otherProps}>
				<Suspense fallback={<ClayLoadingIndicator />}>
					<Component {...props} />
				</Suspense>
			</ErrorBoundary>
		);
	};
}

export class ErrorBoundary extends React.Component {
	constructor(props) {
		super(props);
		this.state = {error: null, hasError: false};
	}

	componentDidCatch(error) {
		this.setState({hasError: true});

		const {onError} = this.props;

		if (onError && typeof onError === 'function') {
			onError(error);
		}
	}

	handleReload() {
		const {onReload} = this.props;

		if (onReload && typeof onReload === 'function') {
			onReload();
		}
		else {
			window.location.reload();
		}
	}

	render() {
		if (this.state.hasError) {
			return (
				<ClayButton
					block
					displayType="secondary"
					onClick={this.handleReload.bind(this)}
				>
					{Liferay.Language.get('refresh')}
				</ClayButton>
			);
		}

		return this.props.children;
	}
}
