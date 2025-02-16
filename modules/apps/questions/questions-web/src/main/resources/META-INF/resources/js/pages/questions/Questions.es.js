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
import {ClayResultsBar} from '@clayui/management-toolbar';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Error from '../../components/Error.es';
import PaginatedList from '../../components/PaginatedList.es';
import QuestionRow from '../../components/QuestionRow.es';
import useQueryParams from '../../hooks/useQueryParams.es';
import {getQuestionThreads} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {historyPushWithSlug, slugToText} from '../../utils/utils.es';
import QuestionsNavigationBar from '../QuestionsNavigationBar.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {creatorId, sectionTitle, tag},
		},
	}) => {
		const [currentTag, setCurrentTag] = useState('');
		const [error, setError] = useState({});
		const [filter, setFilter] = useState();
		const [loading, setLoading] = useState(true);
		const [page, setPage] = useState(1);
		const [pageSize, setPageSize] = useState(20);
		const [questions, setQuestions] = useState([]);
		const [search, setSearch] = useState('');
		const [searchCallback, setSearchCallback] = useState();
		const [section, setSection] = useState({});

		const queryParams = useQueryParams(location);

		const context = useContext(AppContext);

		const siteKey = context.siteKey;

		const historyPushParser = historyPushWithSlug(history.push);

		useEffect(() => {
			setCurrentTag(tag ? slugToText(tag) : '');
		}, [tag]);

		useEffect(() => {
			const pageNumber = queryParams.get('page') || 1;
			setPage(isNaN(pageNumber) ? 1 : parseInt(pageNumber, 10));
		}, [queryParams]);

		useEffect(() => {
			setPageSize(queryParams.get('pagesize') || 20);
		}, [queryParams]);

		useEffect(() => {
			setSearch(queryParams.get('search') || '');
		}, [queryParams]);

		useEffect(() => {
			if (section.id == null) {
				return;
			}

			getQuestionThreads(
				creatorId,
				filter,
				currentTag,
				page,
				pageSize,
				search,
				section,
				siteKey
			)
				.then(({data, loading}) => {
					setQuestions(data || []);
					setLoading(loading);
					if (searchCallback) {
						searchCallback(false);
					}
				})
				.catch((error) => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					setLoading(false);
					setError({message: 'Loading Questions', title: 'Error'});
				});
		}, [
			searchCallback,
			creatorId,
			currentTag,
			filter,
			page,
			pageSize,
			search,
			section,
			siteKey,
		]);

		const loadSearch = (search, searchCallback) => {
			historyPushParser(
				`/questions/${sectionTitle}${
					search && search !== '' ? '?search=' + search : ''
				}`
			);
			setSearchCallback(() => searchCallback);
		};

		const changePage = (page, pageSize) => {
			historyPushParser(
				`/questions/${context.section}${tag ? '/tag/' + tag : ''}${
					creatorId ? '/creator/' + creatorId : ''
				}${
					search && search !== '' ? '?search=' + search + '&' : '?'
				}page=${page}&pagesize=${pageSize}`
			);
		};

		const sectionChange = useCallback((section) => setSection(section), []);

		return (
			<section className="questions-section questions-section-list">
				<div className="questions-container">
					<div className="row">
						<div className="c-mt-3 col col-xl-12">
							<QuestionsNavigationBar
								filterChange={setFilter}
								searchChange={loadSearch}
								sectionChange={sectionChange}
							/>
						</div>

						{!!search && (
							<div className="c-mt-5 c-mx-auto c-px-0 col-xl-12">
								<ClayResultsBar className="c-mt-5">
									<ClayResultsBar.Item expand>
										<span className="component-text text-truncate-inline">
											<span className="text-truncate">
												{lang.sub(
													Liferay.Language.get(
														'x-results-for-x'
													),
													[
														questions.totalCount,
														slugToText(search),
													]
												)}
											</span>
										</span>
									</ClayResultsBar.Item>
									<ClayResultsBar.Item>
										<ClayButton
											className="component-link tbar-link"
											displayType="unstyled"
											onClick={() =>
												loadSearch('', searchCallback)
											}
										>
											{Liferay.Language.get('clear')}
										</ClayButton>
									</ClayResultsBar.Item>
								</ClayResultsBar>
							</div>
						)}

						<div className="c-mx-auto c-px-0 col-xl-10">
							<PaginatedList
								activeDelta={pageSize}
								activePage={page}
								changeDelta={(pageSize) =>
									changePage(page, pageSize)
								}
								changePage={(page) =>
									changePage(page, pageSize)
								}
								data={questions}
								loading={loading}
							>
								{(question) => (
									<QuestionRow
										currentSection={sectionTitle}
										key={question.id}
										question={question}
										showSectionLabel={
											!!section.numberOfMessageBoardSections
										}
									/>
								)}
							</PaginatedList>

							<Error error={error} />
						</div>
					</div>
				</div>
			</section>
		);
	}
);
