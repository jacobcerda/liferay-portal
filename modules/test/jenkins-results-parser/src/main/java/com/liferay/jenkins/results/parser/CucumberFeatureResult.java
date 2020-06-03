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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Michael Hashimoto
 */
public class CucumberFeatureResult {

	public List<CucumberScenarioResult> getCucumberScenarioResults() {
		if (_cucumberScenarioResults != null) {
			return _cucumberScenarioResults;
		}

		_cucumberScenarioResults = new ArrayList<>();

		List<Node> nodes = Dom4JUtil.getNodesByXPath(
			_getDocument(),
			"//div[contains(@class,'elements')]/div[@class='element']");

		Document backgroundDocument = null;
		Document scenarioDocument = null;

		for (Node node : nodes) {
			Document document;

			try {
				document = Dom4JUtil.parse(node.asXML());
			}
			catch (DocumentException documentException) {
				continue;
			}

			Node backgroundNameNode = Dom4JUtil.getNodeByXPath(
				document, "//span[contains(.,'Background')]");

			if (backgroundNameNode != null) {
				backgroundDocument = document;
			}

			Node scenarioNameNode = Dom4JUtil.getNodeByXPath(
				document, "//span[contains(.,'Scenario')]");

			if (scenarioNameNode != null) {
				scenarioDocument = document;
			}

			if (scenarioDocument == null) {
				continue;
			}

			CucumberScenarioResult cucumberScenarioResult =
				new CucumberScenarioResult(
					this, scenarioDocument, backgroundDocument);

			backgroundDocument = null;
			scenarioDocument = null;

			_cucumberScenarioResults.add(cucumberScenarioResult);
		}

		return _cucumberScenarioResults;
	}

	public String getDescription() {
		Node node = Dom4JUtil.getNodeByXPath(
			_getDocument(),
			"//div[@class='feature']/div[contains(@class,'description')]");

		if (node == null) {
			return "";
		}

		return "Description: " + node.getText();
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();

		sb.append("//span[contains(@class,'keyword') and ");
		sb.append("contains(.,'Feature')]/parent::div/span[@class='name']");

		Node node = Dom4JUtil.getNodeByXPath(_getDocument(), sb.toString());

		return "Feature: " + node.getText();
	}

	public List<String> getTags() {
		List<Node> nodes = Dom4JUtil.getNodesByXPath(
			_getDocument(),
			"//div[@class='feature']/div[contains(@class,'tags')]/a");

		List<String> tags = new ArrayList<>();

		for (Node node : nodes) {
			tags.add(node.getText());
		}

		return tags;
	}

	public String getURL() {
		Element element = (Element)_node;

		Element anchorElement = element.element("a");

		return JenkinsResultsParserUtil.combine(
			_build.getBuildURL(), "cucumber-html-reports/",
			anchorElement.attributeValue("href"));
	}

	protected CucumberFeatureResult(Build build, Node node) {
		if (build == null) {
			throw new IllegalArgumentException("Build is null");
		}

		if (node == null) {
			throw new IllegalArgumentException("Node is null");
		}

		_build = build;
		_node = node;
	}

	private Document _getDocument() {
		if (_document != null) {
			return _document;
		}

		try {
			String content = JenkinsResultsParserUtil.toString(getURL());

			content = content.replaceAll("&nbsp;", " ");
			content = content.replaceAll("<br>", "<br />");

			_document = Dom4JUtil.parse(content);

			return _document;
		}
		catch (DocumentException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private final Build _build;
	private List<CucumberScenarioResult> _cucumberScenarioResults;
	private Document _document;
	private final Node _node;

}