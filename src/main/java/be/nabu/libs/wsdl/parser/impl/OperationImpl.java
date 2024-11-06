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

import be.nabu.libs.wsdl.api.Message;
import be.nabu.libs.wsdl.api.Operation;

public class OperationImpl extends FragmentImpl implements Operation {

	private Message input, output;
	private List<Message> faults = new ArrayList<Message>();
	
	@Override
	public Message getInput() {
		return input;
	}
	public void setInput(Message input) {
		this.input = input;
	}

	@Override
	public Message getOutput() {
		return output;
	}
	public void setOutput(Message output) {
		this.output = output;
	}
	
	@Override
	public List<Message> getFaults() {
		return faults;
	}
	public void setFaults(List<Message> faults) {
		this.faults = faults;
	}

}
