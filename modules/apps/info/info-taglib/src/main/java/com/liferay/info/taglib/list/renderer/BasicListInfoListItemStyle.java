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

package com.liferay.info.taglib.list.renderer;

import com.liferay.info.list.renderer.InfoListItemStyle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pavel Savinov
 */
public enum BasicListInfoListItemStyle {

	BORDERED("bordered"), BULLETED("bulleted"), INLINE("inline"),
	NUMBERED("numbered"), UNSTYLED("unstyled");

	public InfoListItemStyle getInfoListItemStyle() {
		return _infoListItemStyle;
	}

	public String getKey() {
		return _key;
	}

	private BasicListInfoListItemStyle(String key) {
		Bundle bundle = FrameworkUtil.getBundle(
			BasicListInfoListItemStyle.class);

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		_infoListItemStyle = new InfoListItemStyle() {

			@Override
			public String getKey() {
				return key;
			}

			@Override
			public String getLabel(Locale locale) {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(locale);

				return LanguageUtil.get(resourceBundle, key);
			}

		};

		_key = key;
	}

	private final InfoListItemStyle _infoListItemStyle;
	private final String _key;

}