package be.nabu.libs.wsdl.parser.impl;

import be.nabu.libs.wsdl.api.BindingOperationMessage;
import be.nabu.libs.wsdl.api.BindingOperationMessageLayout;

public class BindingOperationMessageLayoutImpl implements BindingOperationMessageLayout {

	private BindingOperationMessage body, header;

	@Override
	public BindingOperationMessage getBody() {
		return body;
	}
	public void setBody(BindingOperationMessage body) {
		this.body = body;
	}

	@Override
	public BindingOperationMessage getHeader() {
		return header;
	}
	public void setHeader(BindingOperationMessage header) {
		this.header = header;
	}

}
