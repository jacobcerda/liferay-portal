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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {useEventListener} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const Sites = ({label, sites}) => {
	return (
		<>
			<li className="dropdown-subheader">{label}</li>

			{sites.map(({key, label, logoURL, url}) => (
				<li key={key}>
					<a className="dropdown-item" href={url}>
						<ClaySticker
							className="inline-item-before"
							inline={true}
							size="sm"
						>
							<img className="sticker-img" src={logoURL} />
						</ClaySticker>

						{label}
					</a>
				</li>
			))}
		</>
	);
};

const AppsPanel = ({categories = [], portletNamespace, sites}) => {
	const [activeTab, setActiveTab] = useState(0);

	return (
		<>
			<div className="c-px-md-4 row">
				<ClayTabs modern>
					{categories.map(({key, label}, index) => (
						<ClayTabs.Item
							active={activeTab === index}
							id={`${portletNamespace}tab_${index}`}
							key={key}
							onClick={() => setActiveTab(index)}
						>
							{label}
						</ClayTabs.Item>
					))}
				</ClayTabs>
			</div>

			<ClayTabs.Content activeIndex={activeTab}>
				{categories.map(({childCategories}, index) => (
					<ClayTabs.TabPane
						aria-labelledby={`${portletNamespace}tab_${index}`}
						key={`tabPane-${index}`}
					>
						<div className="c-p-md-3 row">
							{childCategories.map(({key, label, panelApps}) => (
								<div className="col-md" key={key}>
									<ul className="list-unstyled">
										<li className="dropdown-subheader">
											{label}
										</li>

										{panelApps.map(
											({label, portletId, url}) => (
												<li key={portletId}>
													<a
														className="dropdown-item"
														href={url}
													>
														{label}
													</a>
												</li>
											)
										)}
									</ul>
								</div>
							))}

							<div className="col-md">
								<ul className="bg-light list-unstyled rounded">
									<li className="dropdown-subheader">
										{Liferay.Language.get('sites')}
									</li>

									{sites.recentSites &&
										sites.recentSites.length > 0 && (
											<Sites
												label={Liferay.Language.get(
													'recently-visited'
												)}
												sites={sites.recentSites}
											/>
										)}

									{sites.mySites &&
										sites.mySites.length > 0 && (
											<Sites
												label={Liferay.Language.get(
													'my-sites'
												)}
												sites={sites.mySites}
											/>
										)}

									{sites.viewAllURL && (
										<li>
											<ClayButton
												displayType="link"
												onClick={() => {
													Liferay.Util.openModal({
														id: `${portletNamespace}selectSite`,
														onSelect: (
															selectedItem
														) => {
															Liferay.Util.navigate(
																selectedItem.url
															);
														},
														selectEventName: `${portletNamespace}selectSite`,
														title: Liferay.Language.get(
															'select-site-or-asset-library'
														),
														url: sites.viewAllURL,
													});
												}}
												small
											>
												{Liferay.Language.get(
													'view-all'
												)}
											</ClayButton>
										</li>
									)}
								</ul>
							</div>
						</div>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

const GlobalMenu = ({panelAppsURL}) => {
	const [appsPanelData, setAppsPanelData] = useState({});
	const [panelVisible, setPanelVisible] = useState(false);

	const elementRef = useRef();
	const fetchCategoriesPromiseRef = useRef();

	const handleButtonOnClick = () => {
		fetchCategories();
		setPanelVisible(!panelVisible);
	};

	useEventListener(
		'click',
		(event) => {
			if (!elementRef.current?.contains(event.target)) {
				setPanelVisible(false);
			}
		},
		true,
		window
	);

	const fetchCategories = () => {
		if (!fetchCategoriesPromiseRef.current) {
			fetchCategoriesPromiseRef.current = fetch(panelAppsURL)
				.then((response) => response.json())
				.then(({items, portletNamespace, sites}) => {
					setAppsPanelData({
						categories: items,
						portletNamespace,
						sites,
					});
				})
				.catch(() => {
					fetchCategoriesPromiseRef.current = null;
				});
		}
	};

	return (
		<div
			className="dropdown dropdown-full dropdown-global-app nav-item"
			ref={elementRef}
		>
			<ClayButtonWithIcon
				className="dropdown-toggle lfr-portal-tooltip"
				displayType="unstyled"
				onClick={handleButtonOnClick}
				onFocus={fetchCategories}
				onHover={fetchCategories}
				small
				symbol="grid"
				title={Liferay.Language.get('global-menu')}
			/>

			<ul
				className={classNames('c-mt-0 dropdown-menu', {
					show: panelVisible,
				})}
			>
				<AppsPanel {...appsPanelData} />
			</ul>
		</div>
	);
};

GlobalMenu.propTypes = {
	panelAppsURL: PropTypes.string,
};

export default GlobalMenu;
