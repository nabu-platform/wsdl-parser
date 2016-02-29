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
