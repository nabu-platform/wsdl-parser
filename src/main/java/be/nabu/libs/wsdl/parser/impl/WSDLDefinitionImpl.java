package be.nabu.libs.wsdl.parser.impl;

import java.util.ArrayList;
import java.util.List;

import be.nabu.libs.types.api.TypeRegistry;
import be.nabu.libs.wsdl.api.Binding;
import be.nabu.libs.wsdl.api.Message;
import be.nabu.libs.wsdl.api.PolicyProvider;
import be.nabu.libs.wsdl.api.PortType;
import be.nabu.libs.wsdl.api.Service;
import be.nabu.libs.wsdl.api.WSDLDefinition;

public class WSDLDefinitionImpl implements WSDLDefinition {

	private String targetNamespace;
	private List<Message> messages = new ArrayList<Message>();
	private List<WSDLDefinition> imports = new ArrayList<WSDLDefinition>();
	private List<Binding> bindings = new ArrayList<Binding>();
	private List<PortType> portTypes = new ArrayList<PortType>();
	private List<Service> services = new ArrayList<Service>();
	private List<PolicyProvider> policies = new ArrayList<PolicyProvider>();
	private TypeRegistry registry;
	private Double soapVersion;
	
	@Override
	public String getTargetNamespace() {
		return targetNamespace;
	}
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public List<PortType> getPortTypes() {
		return portTypes;
	}
	public void setPortTypes(List<PortType> portTypes) {
		this.portTypes = portTypes;
	}

	@Override
	public List<Binding> getBindings() {
		return bindings;
	}
	public void setBindings(List<Binding> bindings) {
		this.bindings = bindings;
	}

	@Override
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}

	@Override
	public List<PolicyProvider> getPolicies() {
		return policies;
	}
	public void setPolicies(List<PolicyProvider> policies) {
		this.policies = policies;
	}

	@Override
	public List<WSDLDefinition> getImports() {
		return imports;
	}
	public void setImports(List<WSDLDefinition> imports) {
		this.imports = imports;
	}

	@Override
	public TypeRegistry getRegistry() {
		return registry;
	}
	public void setRegistry(TypeRegistry registry) {
		this.registry = registry;
	}
	
	@Override
	public Double getSoapVersion() {
		return soapVersion;
	}
	public void setSoapVersion(Double soapVersion) {
		this.soapVersion = soapVersion;
	}

}
