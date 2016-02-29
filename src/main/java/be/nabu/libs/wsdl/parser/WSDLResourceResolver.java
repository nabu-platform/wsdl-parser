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
