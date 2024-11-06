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
