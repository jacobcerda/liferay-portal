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

import {useMutation, useQuery} from '@apollo/client';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';
import {withRouter} from 'react-router-dom';

import QuestionRow from '../../components/QuestionRow.es';
import {
	getSubscriptionsQuery,
	unsubscribeMyUserAccountQuery,
} from '../../utils/client.es';
import {historyPushWithSlug} from '../../utils/utils.es';
import NavigationBar from '../NavigationBar.es';

export default withRouter(({history}) => {
	const {data: threads, refetch: refetchThreads} = useQuery(
		getSubscriptionsQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				contentType: 'MessageBoardThread',
			},
		}
	);

	const {data: topics, refetch: refetchTopics} = useQuery(
		getSubscriptionsQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				contentType: 'MessageBoardSection',
			},
		}
	);

	const [unsubscribe] = useMutation(unsubscribeMyUserAccountQuery, {
		onCompleted() {
			refetchThreads();
			refetchTopics();
		},
	});

	const historyPushParser = historyPushWithSlug(history.push);

	const navigate = (data) => {
		historyPushParser(`/questions/${data.graphQLNode.title}`);
	};

	return (
		<>
			<NavigationBar />
			<section className="questions-section questions-section-list">
				<div className="c-p-5 questions-container row">
					<div className="col-lg-8 offset-lg-2">
						<h2 className="sheet-subtitle">Topics</h2>
						<Topics />
						<h2 className="mt-5 sheet-subtitle">Questions</h2>
						<Questions />
					</div>
				</div>
			</section>
		</>
	);

	function Topics() {
		return (
			<div className="row">
				{topics &&
					topics.myUserAccountSubscriptions.items &&
					topics.myUserAccountSubscriptions.items.map((data) => (
						<div
							className="col-lg-4 question-tags"
							key={data.graphQLNode.id}
						>
							<div className="card card-interactive card-interactive-primary card-type-template template-card-horizontal">
								<div className="card-body">
									<div className="card-row">
										<div
											className="autofit-col autofit-col-expand"
											onClick={() => navigate(data)}
										>
											<div className="autofit-section">
												<div className="card-title">
													<span className="text-truncate-inline">
														{data.graphQLNode.title}
													</span>
												</div>
											</div>
										</div>
										<div className="autofit-col">
											<ClayDropDownWithItems
												items={[
													{
														label: 'Unsubscribe',
														onClick: () => {
															unsubscribe({
																variables: {
																	subscriptionId:
																		data.id,
																},
															});
														},
													},
												]}
												trigger={
													<ClayButtonWithIcon
														displayType="unstyled"
														small
														symbol="ellipsis-v"
													/>
												}
											/>
										</div>
									</div>
								</div>
							</div>
						</div>
					))}
			</div>
		);
	}

	function Questions() {
		return (
			<div>
				{threads &&
					threads.myUserAccountSubscriptions.items &&
					threads.myUserAccountSubscriptions.items.map((data) => (
						<QuestionRow
							key={data.id}
							question={data.graphQLNode}
							showSectionLabel={true}
							unsubscribe={() =>
								unsubscribe({
									variables: {subscriptionId: data.id},
								})
							}
						/>
					))}
			</div>
		);
	}
});
