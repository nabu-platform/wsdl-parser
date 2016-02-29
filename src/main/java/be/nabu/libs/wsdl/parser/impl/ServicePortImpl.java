package be.nabu.libs.wsdl.parser.impl;

import be.nabu.libs.wsdl.api.Binding;
import be.nabu.libs.wsdl.api.ServicePort;

public class ServicePortImpl extends FragmentImpl implements ServicePort {

	private Binding binding;
	private String endpoint;
	
	@Override
	public Binding getBinding() {
		return binding;
	}
	public void setBinding(Binding binding) {
		this.binding = binding;
	}

	@Override
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
