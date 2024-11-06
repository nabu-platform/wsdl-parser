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

import be.nabu.libs.types.api.TypeRegistry;
import be.nabu.libs.types.xml.ResourceResolver;
import be.nabu.libs.types.xml.URLResourceResolver;
import be.nabu.libs.wsdl.api.WSDLDefinition;

public class WSDLResourceResolver extends URLResourceResolver {

	private WSDLDefinition definition;
	private ResourceResolver parent;

	public WSDLResourceResolver(WSDLDefinition definition, ResourceResolver parent) {
		this.definition = definition;
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
		if (definition.getRegistry() != null && definition.getRegistry().getNamespaces().contains(namespace)) {
			return definition.getRegistry();
		}
		else if (definition.getImports() != null) {
			for (WSDLDefinition imported : definition.getImports()) {
				if (imported.getRegistry() != null && imported.getRegistry().getNamespaces().contains(namespace)) {
					return imported.getRegistry();
				}
			}
		}
		return null;
	}
}
