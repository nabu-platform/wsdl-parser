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

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import be.nabu.libs.wsdl.api.BindingOperation;
import be.nabu.libs.wsdl.api.BindingOperationMessage;
import be.nabu.libs.wsdl.api.BindingOperationMessageLayout;
import be.nabu.libs.wsdl.api.Operation;
import be.nabu.libs.wsdl.api.Style;
import be.nabu.libs.wsdl.api.Use;
import be.nabu.libs.wsdl.api.WSDLDefinition;

public class BindingOperationImpl implements BindingOperation {

	private Operation operation;
	private String soapAction;
	private Style style;
	private Use use;
	private WSDLDefinition definition;
	private BindingOperationMessageLayout inputPartLayout, outputPartLayout;
	private List<BindingOperationMessage> faults;
	
	@Override
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Override
	public String getSoapAction() {
		return soapAction;
	}
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	
	@Override
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
	
	@Override
	public Use getUse() {
		return use;
	}
	public void setUse(Use use) {
		this.use = use;
	}
	
	@Override
	public WSDLDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(WSDLDefinition definition) {
		this.definition = definition;
	}
	
	@XmlTransient
	@Override
	public String getName() {
		return operation.getName();
	}
	
	@Override
	public BindingOperationMessageLayout getInputPartLayout() {
		return inputPartLayout;
	}
	public void setInputPartLayout(BindingOperationMessageLayout inputPartLayout) {
		this.inputPartLayout = inputPartLayout;
	}
	
	@Override
	public BindingOperationMessageLayout getOutputPartLayout() {
		return outputPartLayout;
	}
	public void setOutputPartLayout(BindingOperationMessageLayout outputPartLayout) {
		this.outputPartLayout = outputPartLayout;
	}
	
	@Override
	public List<BindingOperationMessage> getFaults() {
		return faults;
	}
	public void setFaults(List<BindingOperationMessage> faults) {
		this.faults = faults;
	}

}
