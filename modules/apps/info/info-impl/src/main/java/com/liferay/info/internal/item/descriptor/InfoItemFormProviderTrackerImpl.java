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

package com.liferay.info.internal.item.descriptor;

import com.liferay.info.internal.util.ItemClassNameServiceReferenceMapper;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemFormProviderTracker.class)
public class InfoItemFormProviderTrackerImpl
	implements InfoItemFormProviderTracker {

	@Override
	public List<String> getInfoItemClassNames() {
		return new ArrayList<>(_infoItemFormProviderServiceTrackerMap.keySet());
	}

	@Override
	public InfoItemFormProvider<?> getInfoItemFormProvider(
		String itemClassName) {

		return _infoItemFormProviderServiceTrackerMap.getService(itemClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoItemFormProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<InfoItemFormProvider<?>>)
					(Class<?>)InfoItemFormProvider.class,
				null, new ItemClassNameServiceReferenceMapper(bundleContext));
	}

	private ServiceTrackerMap<String, InfoItemFormProvider<?>>
		_infoItemFormProviderServiceTrackerMap;

}