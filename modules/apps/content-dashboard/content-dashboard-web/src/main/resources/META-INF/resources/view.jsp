<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
ContentDashboardAdminDisplayContext contentDashboardAdminDisplayContext = (ContentDashboardAdminDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT);

ContentDashboardAdminManagementToolbarDisplayContext contentDashboardAdminManagementToolbarDisplayContext = (ContentDashboardAdminManagementToolbarDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT);
%>

<clay:container
	cssClass="main-content-body"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<%= LanguageUtil.format(request, "content-per-audience-and-stage-x", 0, false) %>
		</h2>

		<div id="audit-graph">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				module="js/AuditGraphApp"
			/>
		</div>
	</div>
</clay:container>

<clay:container
	cssClass="main-content-body"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<%= LanguageUtil.format(request, "content-x", contentDashboardAdminDisplayContext.getSearchContainer().getTotal(), false) %>
		</h2>

		<clay:management-toolbar
			displayContext="<%= contentDashboardAdminManagementToolbarDisplayContext %>"
			elementClasses="content-dashboard-management-toolbar"
		/>

		<div class="sheet-section">
			<liferay-ui:search-container
				id="content"
				searchContainer="<%= contentDashboardAdminDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.content.dashboard.web.internal.item.ContentDashboardItem"
					keyProperty="id"
					modelVar="contentDashboardItem"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-title"
						name="title"
					>
						<span class="lfr-portal-tooltip text-truncate-inline" title="<%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %>">
							<span class="text-truncate"><%= HtmlUtil.escape(contentDashboardItem.getTitle(locale)) %></span>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="text-center"
						name="author"
					>
						<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(contentDashboardItem.getUserName()) %>">
							<liferay-ui:user-portrait
								userId="<%= contentDashboardItem.getUserId() %>"
							/>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="subtype"
						value="<%= HtmlUtil.escape(contentDashboardItem.getSubtype(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="status"
					>
						<clay:label
							label="<%= StringUtil.toUpperCase(contentDashboardItem.getStatusLabel(locale)) %>"
							style="<%= contentDashboardItem.getStatusStyle() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-date
						name="publish-date"
						value="<%= contentDashboardItem.getPublishDate() %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= contentDashboardItem.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-date
						name="expiration-date"
						value="<%= contentDashboardItem.getExpirationDate() %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</div>
</clay:container>