package be.nabu.libs.wsdl.parser.impl;

import java.util.ArrayList;
import java.util.List;

import be.nabu.libs.wsdl.api.Service;
import be.nabu.libs.wsdl.api.ServicePort;

public class ServiceImpl extends FragmentImpl implements Service {

	private List<ServicePort> ports = new ArrayList<ServicePort>();
	
	@Override
	public List<ServicePort> getPorts() {
		return ports;
	}
	public void setPorts(List<ServicePort> ports) {
		this.ports = ports;
	}

}
