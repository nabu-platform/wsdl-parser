package be.nabu.libs.wsdl.parser.impl;

import java.util.ArrayList;
import java.util.List;

import be.nabu.libs.wsdl.api.Message;
import be.nabu.libs.wsdl.api.MessagePart;

public class MessageImpl extends FragmentImpl implements Message {

	private List<MessagePart> parts = new ArrayList<MessagePart>();
	
	@Override
	public List<MessagePart> getParts() {
		return parts;
	}
	public void setParts(List<MessagePart> parts) {
		this.parts = parts;
	}

}
