/*
* Copyright (C) 2016 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.wsdl.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import be.nabu.libs.types.api.TypeRegistry;
import be.nabu.libs.types.xml.ResourceResolver;
import be.nabu.libs.types.xml.URLResourceResolver;

public class TypeRegistryResolver extends URLResourceResolver {
	
	private List<TypeRegistry> registries;
	private ResourceResolver parent;

	public TypeRegistryResolver(List<TypeRegistry> registries, ResourceResolver parent) {
		this.registries = registries;
		this.parent = parent;
	}
	
	@Override
	public InputStream resolve(URI uri) throws IOException {
		InputStream input = null;
		if (parent != null) {
			input = parent.resolve(uri);
		}
		if (input == null) {
			input = super.resolve(uri);
		}
		return input;
	}

	@Override
	public TypeRegistry resolve(String namespace) throws IOException {
		for (TypeRegistry registry : registries) {
			if (registry.getNamespaces().contains(namespace)) {
				return registry;
			}
		}
		return parent.resolve(namespace);
	}

}
