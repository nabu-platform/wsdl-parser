package be.nabu.libs.wsdl.parser.impl;

import be.nabu.libs.wsdl.api.Fragment;
import be.nabu.libs.wsdl.api.WSDLDefinition;

public class FragmentImpl implements Fragment {

	private WSDLDefinition definition;
	private String name;
	
	@Override
	public WSDLDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(WSDLDefinition definition) {
		this.definition = definition;
	}

	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
