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
String articleResourceId = ParamUtil.getString(request, "articleResourceId");
String articleTitle = ParamUtil.getString(request, "articleTitle");
String redirect = ParamUtil.getString(request, "redirect");
String title = LanguageUtil.get(resourceBundle, "import-translation");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<liferay-ui:error exception="<%= InvalidXLIFFFileException.class %>" message="the-file-is-invalid" />

<portlet:actionURL name="/journal/import_translation" var="importTranslationURL">
	<portlet:param name="articleResourceId" value="<%= articleResourceId %>" />
</portlet:actionURL>

<aui:form action="<%= importTranslationURL %>" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="pl-2 tbar-section text-left">
						<h2 class="h4 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(articleTitle) %>">
							<span class="text-truncate"><%= HtmlUtil.escape(articleTitle) %></span>
						</h2>
					</div>
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" primary="<%= false %>" type="submit" value='<%= LanguageUtil.get(request, "save-as-draft") %>' />

						<aui:button cssClass="btn-sm mr-3" id="submitBtnId" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "publish") %>' />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<clay:sheet>

			<%
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"articleResourceId", articleResourceId
			).put(
				"saveDraftBtnId", renderResponse.getNamespace() + "saveDraftBtn"
			).put(
				"submitBtnId", renderResponse.getNamespace() + "submitBtnId"
			).build();
			%>

			<react:component
				data="<%= data %>"
				module="js/ImportTranslation.es"
			/>
		</clay:sheet>
	</clay:container-fluid>
</aui:form>

<script>
	var saveDraftBtn = document.getElementById('<portlet:namespace />saveDraftBtn');

	saveDraftBtn.addEventListener('click', function () {
		var workflowActionInput = document.getElementById(
			'<portlet:namespace />workflowAction'
		);

		workflowActionInput.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';
	});
</script>