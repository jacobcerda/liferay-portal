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

import ClayAlert from '@clayui/alert';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {ChildLink} from '../../shared/components/router/routerWrapper.es';
import {sub} from '../../shared/util/lang.es';
import {openErrorToast} from '../../shared/util/toast.es';
import {AppContext} from '../AppContext.es';

const SLAInfo = ({processId}) => {
	const {client, defaultDelta} = useContext(AppContext);
	const [blockedSLACount, setBlockedSLACount] = useState(0);
	const [showDismisible, setShowDismisible] = useState(true);
	const [slaCount, setSlaCount] = useState(null);

	const getSLACount = useCallback(
		blocked => {
			const status = blocked ? '&status=2' : '';
			const url = `/processes/${processId}/slas?page=1&pageSize=1${status}`;

			return client.get(url).then(({data: {totalCount}}) => totalCount);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[processId]
	);

	useEffect(() => {
		Promise.all([getSLACount(true), getSLACount()])
			.then(([blockedSLACount, slaCount]) => {
				setBlockedSLACount(blockedSLACount);
				setSlaCount(slaCount);
			})
			.catch(() => {
				openErrorToast({
					message: Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					),
				});
			});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const blockedSLAText =
		blockedSLACount !== 1
			? Liferay.Language.get('x-slas-are-blocked')
			: Liferay.Language.get('x-sla-is-blocked');

	return (
		<div className="container-fluid-1280">
			{blockedSLACount !== 0 && showDismisible && (
				<ClayAlert
					className="mb-0"
					displayType="warning"
					onClose={() => setShowDismisible(false)}
					title={Liferay.Language.get('warning')}
				>
					{`${sub(blockedSLAText, [
						blockedSLACount,
					])} ${Liferay.Language.get(
						'fix-the-sla-configuration-to-resume-accurate-reporting'
					)} `}

					<ChildLink to={`/slas/${processId}/${defaultDelta}/1`}>
						<strong>{Liferay.Language.get('set-up-slas')}</strong>
					</ChildLink>
				</ClayAlert>
			)}

			{slaCount === 0 && showDismisible && (
				<ClayAlert
					className="mb-0"
					displayType="warning"
					onClose={() => setShowDismisible(false)}
					title={Liferay.Language.get('warning')}
				>
					<>
						{`${Liferay.Language.get(
							'no-slas-are-defined-for-this-process'
						)} `}

						<ChildLink to={`/sla/new/${processId}`}>
							<strong>
								{Liferay.Language.get('add-a-new-sla')}
							</strong>
						</ChildLink>
					</>
				</ClayAlert>
			)}
		</div>
	);
};

export default SLAInfo;
