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

package com.liferay.info.list.renderer;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public class DefaultInfoListRendererContext implements InfoListRendererContext {

	public DefaultInfoListRendererContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	@Override
	public Optional<String> getListItemStyleKeyOptional() {
		return Optional.ofNullable(_listItemStyleKey);
	}

	public void setListItemStyleKey(String listItemStyleKey) {
		_listItemStyleKey = listItemStyleKey;
	}

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private String _listItemStyleKey;

}