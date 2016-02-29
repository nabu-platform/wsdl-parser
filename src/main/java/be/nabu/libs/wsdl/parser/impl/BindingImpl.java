package be.nabu.libs.wsdl.parser.impl;

import java.util.ArrayList;
import java.util.List;

import be.nabu.libs.wsdl.api.Binding;
import be.nabu.libs.wsdl.api.BindingOperation;
import be.nabu.libs.wsdl.api.PortType;
import be.nabu.libs.wsdl.api.Transport;

public class BindingImpl extends FragmentImpl implements Binding {

	private Transport transport;
	private PortType portType;
	private List<BindingOperation> operations = new ArrayList<BindingOperation>();

	@Override
	public Transport getTransport() {
		return transport;
	}
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	@Override
	public PortType getPortType() {
		return portType;
	}
	public void setPortType(PortType portType) {
		this.portType = portType;
	}

	@Override
	public List<BindingOperation> getOperations() {
		return operations;
	}

	public void setOperations(List<BindingOperation> operations) {
		this.operations = operations;
	}

}
