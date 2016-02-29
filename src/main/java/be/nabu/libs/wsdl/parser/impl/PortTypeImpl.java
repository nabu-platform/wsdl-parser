package be.nabu.libs.wsdl.parser.impl;

import java.util.ArrayList;
import java.util.List;

import be.nabu.libs.wsdl.api.Operation;
import be.nabu.libs.wsdl.api.PortType;

public class PortTypeImpl extends FragmentImpl implements PortType {

	private List<Operation> operations = new ArrayList<Operation>();
	
	@Override
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

}
