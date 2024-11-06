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
