package be.nabu.libs.wsdl.parser.impl;

import be.nabu.libs.wsdl.api.BindingOperationMessage;
import be.nabu.libs.wsdl.api.Use;

public class BindingOperationMessageImpl extends MessageImpl implements BindingOperationMessage {

	private Use use;
	
	@Override
	public Use getUse() {
		return use;
	}
	public void setUse(Use use) {
		this.use = use;
	}
	
}
