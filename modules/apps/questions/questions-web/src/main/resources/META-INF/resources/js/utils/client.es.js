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

import {ApolloClient, HttpLink, InMemoryCache, gql} from '@apollo/client';
import {fetch} from 'frontend-js-web';

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'text/plain; charset=utf-8',
};

export const client = new ApolloClient({
	cache: new InMemoryCache(),
	defaultOptions: {
		query: {
			errorPolicy: 'all',
		},
	},
	link: new HttpLink({
		fetch,
		headers: HEADERS,
		uri: '/o/graphql',
	}),
});

export const createAnswerQuery = gql`
	mutation createMessageBoardThreadMessageBoardMessage(
		$articleBody: String!
		$messageBoardThreadId: Long!
	) {
		createMessageBoardThreadMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
				viewableBy: ANYONE
			}
			messageBoardThreadId: $messageBoardThreadId
		) {
			viewableBy
		}
	}
`;

export const createCommentQuery = gql`
	mutation createMessageBoardMessageMessageBoardMessage(
		$articleBody: String!
		$parentMessageBoardMessageId: Long!
	) {
		createMessageBoardMessageMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
				viewableBy: ANYONE
			}
			parentMessageBoardMessageId: $parentMessageBoardMessageId
		) {
			actions
			articleBody
			creator {
				name
			}
			id
		}
	}
`;

export const createQuestionQuery = gql`
	mutation createMessageBoardSectionMessageBoardThread(
		$messageBoardSectionId: Long!
		$articleBody: String!
		$headline: String!
		$keywords: [String]
	) {
		createMessageBoardSectionMessageBoardThread(
			messageBoardSectionId: $messageBoardSectionId
			messageBoardThread: {
				articleBody: $articleBody
				encodingFormat: "html"
				headline: $headline
				showAsQuestion: true
				keywords: $keywords
				viewableBy: ANYONE
			}
		) {
			articleBody
			headline
			keywords
			showAsQuestion
		}
	}
`;

export const createVoteMessageQuery = gql`
	mutation createMessageBoardMessageMyRating(
		$messageBoardMessageId: Long!
		$ratingValue: Float!
	) {
		createMessageBoardMessageMyRating(
			messageBoardMessageId: $messageBoardMessageId
			rating: {ratingValue: $ratingValue}
		) {
			id
			ratingValue
		}
	}
`;

export const createVoteThreadQuery = gql`
	mutation createMessageBoardThreadMyRating(
		$messageBoardThreadId: Long!
		$ratingValue: Float!
	) {
		createMessageBoardThreadMyRating(
			messageBoardThreadId: $messageBoardThreadId
			rating: {ratingValue: $ratingValue}
		) {
			id
			ratingValue
		}
	}
`;

export const deleteMessageQuery = gql`
	mutation deleteMessageBoardMessage($messageBoardMessageId: Long!) {
		deleteMessageBoardMessage(messageBoardMessageId: $messageBoardMessageId)
	}
`;

export const deleteMessageBoardThreadQuery = gql`
	mutation deleteMessageBoardThread($messageBoardThreadId: Long!) {
		deleteMessageBoardThread(messageBoardThreadId: $messageBoardThreadId)
	}
`;

export const getTagsQuery = gql`
	query keywordsRanked(
		$page: Int!
		$pageSize: Int!
		$search: String
		$siteKey: String!
	) {
		keywordsRanked(
			page: $page
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
		) {
			items {
				id
				keywordUsageCount
				name
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getMessageQuery = gql`
	query messageBoardMessageByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardMessageByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			articleBody
			headline
			id
		}
	}
`;

export const getThreadQuery = gql`
	query messageBoardThreadByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardThreadByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			actions
			aggregateRating {
				ratingAverage
				ratingCount
				ratingValue
			}
			articleBody
			creator {
				id
				name
			}
			creatorStatistics {
				joinDate
				lastPostDate
				postsNumber
				rank
			}
			dateCreated
			dateModified
			encodingFormat
			friendlyUrlPath
			headline
			id
			keywords
			messageBoardSection {
				numberOfMessageBoardSections
				title
			}
			myRating {
				ratingValue
			}
			subscribed
			viewCount
		}
	}
`;

export const getMessageBoardThreadByIdQuery = gql`
	query messageBoardThread($messageBoardThreadId: Long!) {
		messageBoardThread(messageBoardThreadId: $messageBoardThreadId) {
			friendlyUrlPath
			id
			messageBoardSection {
				title
			}
		}
	}
`;

export const getThreadContentQuery = gql`
	query messageBoardThreadByFriendlyUrlPath(
		$friendlyUrlPath: String!
		$siteKey: String!
	) {
		messageBoardThreadByFriendlyUrlPath(
			friendlyUrlPath: $friendlyUrlPath
			siteKey: $siteKey
		) {
			articleBody
			headline
			id
			keywords
		}
	}
`;

export const getMessagesQuery = gql`
	query messageBoardThreadMessageBoardMessages(
		$messageBoardThreadId: Long!
		$page: Int!
		$pageSize: Int!
		$sort: String!
	) {
		messageBoardThreadMessageBoardMessages(
			messageBoardThreadId: $messageBoardThreadId
			page: $page
			pageSize: $pageSize
			sort: $sort
		) {
			items {
				actions
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					name
				}
				creatorStatistics {
					joinDate
					lastPostDate
					postsNumber
					rank
				}
				encodingFormat
				friendlyUrlPath
				id
				messageBoardMessages {
					items {
						actions
						articleBody
						creator {
							id
							name
						}
						encodingFormat
						id
						showAsAnswer
					}
				}
				myRating {
					ratingValue
				}
				showAsAnswer
			}
			pageSize
			totalCount
		}
	}
`;

export const hasListPermissionsQuery = gql`
	query messageBoardThreads($siteKey: String!) {
		messageBoardThreads(siteKey: $siteKey) {
			actions
		}
	}
`;

export const getQuestionThreads = (
	creatorId = '',
	filter,
	keywords = '',
	page = 1,
	pageSize = 30,
	search = '',
	section,
	siteKey
) => {
	if (filter === 'latest-edited') {
		return getThreads(
			creatorId,
			keywords,
			page,
			pageSize,
			search,
			section,
			siteKey,
			'dateModified:desc'
		);
	}
	else if (filter === 'week') {
		const date = new Date();
		date.setDate(date.getDate() - 7);

		return getRankedThreads(date, page, pageSize, section);
	}
	else if (filter === 'month') {
		const date = new Date();
		date.setDate(date.getDate() - 31);

		return getRankedThreads(date, page, pageSize, section);
	}

	return getThreads(
		creatorId,
		keywords,
		page,
		pageSize,
		search,
		section,
		siteKey,
		'dateCreated:desc'
	);
};

export const getThreads = (
	creatorId = '',
	keywords = '',
	page = 1,
	pageSize = 30,
	search = '',
	section,
	siteKey,
	sort = 'dateCreated:desc'
) => {
	let filter = `(messageBoardSectionId eq ${section.id} `;

	for (let i = 0; i < section.messageBoardSections.items.length; i++) {
		filter += `or messageBoardSectionId eq ${section.messageBoardSections.items[i].id} `;
	}

	filter += ')';

	if (keywords) {
		filter = `keywords/any(x:x eq '${keywords}')`;
	}
	else if (creatorId) {
		filter = `creator/id eq ${creatorId}`;
	}

	return client
		.query({
			query: getThreadsQuery,
			variables: {
				filter,
				page,
				pageSize,
				search,
				siteKey,
				sort,
			},
		})
		.then((result) => ({...result, data: result.data.messageBoardThreads}));
};

export const getThreadsQuery = gql`
	query messageBoardThreads(
		$filter: String!
		$page: Int!
		$pageSize: Int!
		$search: String!
		$siteKey: String!
		$sort: String!
	) {
		messageBoardThreads(
			filter: $filter
			flatten: true
			page: $page
			pageSize: $pageSize
			search: $search
			siteKey: $siteKey
			sort: $sort
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				messageBoardSection {
					title
				}
				numberOfMessageBoardMessages
				keywords
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionQuery = gql`
	query messageBoardSections($filter: String!, $siteKey: String!) {
		messageBoardSections(
			filter: $filter
			flatten: true
			pageSize: 1
			siteKey: $siteKey
			sort: "title:desc"
		) {
			items {
				actions
				id
				messageBoardSections(sort: "title:asc") {
					items {
						id
						numberOfMessageBoardSections
						parentMessageBoardSectionId
						subscribed
						title
					}
				}
				numberOfMessageBoardSections
				parentMessageBoardSectionId
				subscribed
				title
			}
		}
	}
`;

export const getRankedThreads = (
	dateModified,
	page = 1,
	pageSize = 20,
	section,
	sort = ''
) => {
	return client
		.query({
			query: getRankedThreadsQuery,
			variables: {
				dateModified: dateModified.toISOString(),
				messageBoardSectionId: section.id,
				page,
				pageSize,
				sort,
			},
		})
		.then((result) => ({
			...result,
			data: result.data.messageBoardThreadsRanked,
		}));
};

export const getRankedThreadsQuery = gql`
	query messageBoardThreadsRanked(
		$dateModified: Date!
		$messageBoardSectionId: Long!
		$page: Int!
		$pageSize: Int!
		$sort: String!
	) {
		messageBoardThreadsRanked(
			dateModified: $dateModified
			messageBoardSectionId: $messageBoardSectionId
			page: $page
			pageSize: $pageSize
			sort: $sort
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					image
					name
				}
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				messageBoardSection {
					title
				}
				numberOfMessageBoardMessages
				viewCount
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getRelatedThreadsQuery = gql`
	query messageBoardThreads($search: String!, $siteKey: String!) {
		messageBoardThreads(
			page: 1
			pageSize: 4
			flatten: true
			search: $search
			siteKey: $siteKey
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				creator {
					id
					name
				}
				dateModified
				friendlyUrlPath
				headline
				id
				messageBoardSection {
					title
				}
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const getSectionsQuery = gql`
	query messageBoardSections($siteKey: String!) {
		messageBoardSections(siteKey: $siteKey, sort: "title:desc") {
			items {
				description
				id
				numberOfMessageBoardThreads
				parentMessageBoardSectionId
				subscribed
				title
			}
		}
	}
`;

export const getUserActivityQuery = gql`
	query messageBoardThreads(
		$filter: String
		$page: Int!
		$pageSize: Int!
		$siteKey: String!
	) {
		messageBoardThreads(
			filter: $filter
			flatten: true
			page: $page
			pageSize: $pageSize
			siteKey: $siteKey
			sort: "dateCreated:desc"
		) {
			items {
				aggregateRating {
					ratingAverage
					ratingCount
					ratingValue
				}
				articleBody
				creator {
					id
					name
					image
				}
				creatorStatistics {
					postsNumber
					rank
				}
				dateModified
				friendlyUrlPath
				hasValidAnswer
				headline
				id
				keywords
				messageBoardSection {
					title
				}
				messageBoardSection {
					title
				}
				numberOfMessageBoardMessages
				taxonomyCategoryBriefs {
					taxonomyCategoryId
					taxonomyCategoryName
				}
			}
			page
			pageSize
			totalCount
		}
	}
`;

export const markAsAnswerMessageBoardMessageQuery = gql`
	mutation patchMessageBoardMessage(
		$messageBoardMessageId: Long!
		$showAsAnswer: Boolean!
	) {
		patchMessageBoardMessage(
			messageBoardMessage: {showAsAnswer: $showAsAnswer}
			messageBoardMessageId: $messageBoardMessageId
		) {
			id
			showAsAnswer
		}
	}
`;

export const updateMessageQuery = gql`
	mutation patchMessageBoardMessage(
		$articleBody: String!
		$messageBoardMessageId: Long!
	) {
		patchMessageBoardMessage(
			messageBoardMessage: {
				articleBody: $articleBody
				encodingFormat: "html"
			}
			messageBoardMessageId: $messageBoardMessageId
		) {
			articleBody
		}
	}
`;

export const updateThreadQuery = gql`
	mutation patchMessageBoardThread(
		$articleBody: String!
		$headline: String!
		$keywords: [String]
		$messageBoardThreadId: Long!
	) {
		patchMessageBoardThread(
			messageBoardThread: {
				articleBody: $articleBody
				encodingFormat: "html"
				headline: $headline
				keywords: $keywords
			}
			messageBoardThreadId: $messageBoardThreadId
		) {
			articleBody
			headline
			keywords
		}
	}
`;

export const subscribeQuery = gql`
	mutation updateMessageBoardThreadSubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadSubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const unsubscribeQuery = gql`
	mutation updateMessageBoardThreadUnsubscribe($messageBoardThreadId: Long!) {
		updateMessageBoardThreadUnsubscribe(
			messageBoardThreadId: $messageBoardThreadId
		)
	}
`;

export const subscribeSectionQuery = gql`
	mutation updateMessageBoardSectionSubscribe($messageBoardSectionId: Long!) {
		updateMessageBoardSectionSubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;

export const unsubscribeSectionQuery = gql`
	mutation updateMessageBoardSectionUnsubscribe(
		$messageBoardSectionId: Long!
	) {
		updateMessageBoardSectionUnsubscribe(
			messageBoardSectionId: $messageBoardSectionId
		)
	}
`;
