package be.nabu.libs.wsdl.parser.impl;

import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.wsdl.api.MessagePart;

public class MessagePartImpl extends FragmentImpl implements MessagePart {
	
	private Element<?> element;
	private Type type;

	@Override
	public Element<?> getElement() {
		return element;
	}
	public void setElement(Element<?> element) {
		this.element = element;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
