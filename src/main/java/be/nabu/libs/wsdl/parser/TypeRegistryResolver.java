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
