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

package com.liferay.product.navigation.global.menu.web.internal.product.navigation.control.menu;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=500"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class GlobalMenuProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/global_menu/global_menu.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		List<PanelCategory> globalMenuPanelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.GLOBAL_MENU);

		for (PanelCategory panelCategory : globalMenuPanelCategories) {
			List<PanelCategory> childPanelCategories =
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategory.getKey());

			if (!childPanelCategories.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@Reference(
		target = "(panel.category.key=" + PanelCategoryKeys.HIDDEN + ")",
		unbind = "-"
	)
	public void setPanelCategory(PanelCategory panelCategory) {
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.global.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

}