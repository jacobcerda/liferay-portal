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
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

String refererWebDAVToken = WebDAVUtil.getStorageToken(portlet);

String redirect = ParamUtil.getString(request, "redirect");

DLEditDDMStructureDisplayContext dlEditDDMStructureDisplayContext = new DLEditDDMStructureDisplayContext(request);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = dlEditDDMStructureDisplayContext.getDDMStructure();

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

String title = LanguageUtil.format(request, "new-x", LanguageUtil.get(resourceBundle, "metadata-set"), false);

if (ddmStructure != null) {
	title = LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<portlet:actionURL name="/document_library/ddm/add_data_definition" var="addDataDefinitionURL" />

<portlet:actionURL name="/document_library/ddm/update_data_definition" var="updateDataDefinitionURL" />

<aui:form action="<%= (ddmStructure == null) ? addDataDefinitionURL : updateDataDefinitionURL %>" cssClass="edit-metadata-type-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveDDMStructure();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="dataDefinitionId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="dataDefinition" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />
	<aui:input name="status" type="hidden" />

	<aui:model-context bean="<%= ddmStructure %>" model="<%= com.liferay.dynamic.data.mapping.model.DDMStructure.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="form-control-inline" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled", "metadata-set") %>' wrapperCssClass="mb-0" />
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" onClick='<%= renderResponse.getNamespace() + "saveDDMStructure();" %>' primary="<%= true %>" value='<%= LanguageUtil.get(request, "save") %>' />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper>
					<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(ddmStructure.getPrimaryKey()) > 0) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
						</div>
					</c:if>

					<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
						</div>
					</c:if>
				</aui:field-wrapper>

				<liferay-ui:panel-container
					cssClass="lfr-structure-entry-details-container"
					extended="<%= false %>"
					id="structureDetailsPanelContainer"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						defaultState="closed"
						extended="<%= false %>"
						id="structureDetailsSectionPanel"
						markupView="lexicon"
						persistState="<%= true %>"
						title='<%= LanguageUtil.get(request, "details") %>'
					>
						<clay:row
							cssClass="lfr-ddm-types-form-column"
						>
							<aui:input name="storageType" type="hidden" value="<%= StorageType.JSON.getValue() %>" />
						</clay:row>

						<aui:input name="description" />

						<c:if test="<%= ddmStructure != null %>">
							<portlet:resourceURL id="getStructure" var="getStructureURL">
								<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
							</portlet:resourceURL>

							<aui:input name="url" type="resource" value="<%= getStructureURL.toString() %>" />

							<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
								<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
							</c:if>
						</c:if>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<liferay-data-engine:data-layout-builder
					componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
					contentType="document-library"
					dataDefinitionId="<%= ddmStructureId %>"
					dataLayoutInputId="dataLayout"
					groupId="<%= groupId %>"
					localizable="<%= true %>"
					namespace="<%= renderResponse.getNamespace() %>"
				/>
			</aui:fieldset>
		</aui:fieldset-group>
	</clay:container-fluid>
</aui:form>

<aui:script>
	function <portlet:namespace />getInputLocalizedValues(field) {
		var inputLocalized = Liferay.component('<portlet:namespace />' + field);
		var localizedValues = {};

		if (inputLocalized) {
			var translatedLanguages = inputLocalized
				.get('translatedLanguages')
				.values();

			translatedLanguages.forEach(function (languageId) {
				localizedValues[languageId] = inputLocalized.getValue(languageId);
			});
		}

		return localizedValues;
	}

	function <portlet:namespace />saveDDMStructure() {
		Liferay.componentReady('<portlet:namespace />dataLayoutBuilder').then(
			function (dataLayoutBuilder) {
				var name = <portlet:namespace />getInputLocalizedValues('name');

				var description = <portlet:namespace />getInputLocalizedValues(
					'description'
				);

				var formData = dataLayoutBuilder.getFormData();

				var dataDefinition = formData.definition;

				dataDefinition.description = description;
				dataDefinition.name = name;

				var dataLayout = formData.layout;

				dataLayout.description = description;
				dataLayout.name = name;

				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						dataDefinition: JSON.stringify(dataDefinition),
						dataLayout: JSON.stringify(dataLayout),
					},
				});
			}
		);
	}
</aui:script>