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
