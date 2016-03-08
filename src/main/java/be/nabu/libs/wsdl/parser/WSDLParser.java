package be.nabu.libs.wsdl.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import be.nabu.libs.types.TypeRegistryImpl;
import be.nabu.libs.types.api.ModifiableTypeRegistry;
import be.nabu.libs.types.api.TypeRegistry;
import be.nabu.libs.types.xml.ResourceResolver;
import be.nabu.libs.types.xml.XMLSchema;
import be.nabu.libs.wsdl.WSDLUtils;
import be.nabu.libs.wsdl.api.Binding;
import be.nabu.libs.wsdl.api.Message;
import be.nabu.libs.wsdl.api.Operation;
import be.nabu.libs.wsdl.api.PortType;
import be.nabu.libs.wsdl.api.Service;
import be.nabu.libs.wsdl.api.Style;
import be.nabu.libs.wsdl.api.Transport;
import be.nabu.libs.wsdl.api.Use;
import be.nabu.libs.wsdl.api.WSDLDefinition;
import be.nabu.libs.wsdl.parser.impl.BindingImpl;
import be.nabu.libs.wsdl.parser.impl.BindingOperationImpl;
import be.nabu.libs.wsdl.parser.impl.MessageImpl;
import be.nabu.libs.wsdl.parser.impl.MessagePartImpl;
import be.nabu.libs.wsdl.parser.impl.OperationImpl;
import be.nabu.libs.wsdl.parser.impl.PortTypeImpl;
import be.nabu.libs.wsdl.parser.impl.ServiceImpl;
import be.nabu.libs.wsdl.parser.impl.ServicePortImpl;
import be.nabu.libs.wsdl.parser.impl.WSDLDefinitionImpl;
import be.nabu.utils.xml.XMLUtils;

public class WSDLParser {

	public static final String NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
	public static final String SOAP_HTTP_NAMESPACE = "http://schemas.xmlsoap.org/soap/http";
	public static final String SOAP_NAMESPACE_11 = "http://schemas.xmlsoap.org/wsdl/soap/";
	public static final String SOAP_NAMESPACE_12 = "http://schemas.xmlsoap.org/wsdl/soap12/";
	public static final List<String> SOAP_NAMESPACES = Arrays.asList(SOAP_NAMESPACE_11, SOAP_NAMESPACE_12);

	private WSDLDefinitionImpl definition = new WSDLDefinitionImpl();
	private boolean parsed;
	
	private Document document;
		
	private ResourceResolver resolver;

	private boolean stringsOnly;
	
	private String namespace;
	
	private TypeRegistryImpl registry;
	
	private String id;
	
	public WSDLParser(InputStream container, boolean stringsOnly) throws IOException, ParseException, SAXException, ParserConfigurationException {
		this(XMLUtils.toDocument(container, true), stringsOnly);
	}
	
	public WSDLParser(Document document, boolean stringsOnly) {
		this.document = document;
		this.stringsOnly = stringsOnly;
	}

	public WSDLDefinition getDefinition() throws ParseException, IOException {
		if (!parsed) {
			synchronized(this) {
				if (!parsed) {
					parse();
					parsed = true;
				}
			}
		}
		return definition;
	}
	
	private void setSoapNamespace(String soapNamespace) {
		double soapVersion = SOAP_NAMESPACE_11.equals(soapNamespace) ? 1.1 : 1.2;
		if (definition.getSoapVersion() == null) {
			definition.setSoapVersion(soapVersion);
		}
		else if (soapVersion != definition.getSoapVersion()) {
			throw new RuntimeException("Multiple soap namespaces detected");
		}
	}
	
	private List<Element> getChildren(Element element) {
		List<Element> children = new ArrayList<Element>();
		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			if (element.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
				children.add((Element) element.getChildNodes().item(i));
			}
		}
		return children;
	}
	
	private void parse() throws ParseException, IOException {
		try {
			parse(document);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void parse(Document document) throws ParseException, IOException, URISyntaxException, SAXException, ParserConfigurationException {
		definition.setTargetNamespace(document.getDocumentElement().getAttribute("targetNamespace"));
		if (definition.getTargetNamespace() == null) {
			throw new ParseException("No target namespace found in the WSDL", 0);
		}
		for (Element element : getChildren(document.getDocumentElement())) {
			if (element.getNamespaceURI().equals(NAMESPACE)) {
				if (element.getLocalName().equals("import")) {
					definition.getImports().add(parseImport(element));
				}
				else if (element.getLocalName().equals("types")) {
					definition.setRegistry(parseTypes(element));
				}
				else if (element.getLocalName().equals("message")) {
					definition.getMessages().add(parseMessage(element));
				}
				else if (element.getLocalName().equals("portType")) {
					definition.getPortTypes().add(parsePortType(element));
				}
				else if (element.getLocalName().equals("binding")) {
					definition.getBindings().add(parseBinding(element));
				}
				else if (element.getLocalName().equals("service")) {
					definition.getServices().add(parseService(element));
				}
			}
		}
	}
	
	private Service parseService(Element element) throws ParseException {
		ServiceImpl service = new ServiceImpl();
		service.setDefinition(definition);
		if (element.hasAttribute("name")) {
			service.setName(element.getAttribute("name"));
		}
		List<Element> children = getChildren(element);
		for (Element child : children) {
			ServicePortImpl servicePort = new ServicePortImpl();
			servicePort.setDefinition(definition);
			if (child.hasAttribute("name")) {
				servicePort.setName(child.getAttribute("name"));
			}
			String bindingName = children.get(0).getAttribute("binding");
			int separator = bindingName.indexOf(':');
			String bindingNamespace = element.lookupNamespaceURI(separator < 0 ? null : bindingName.substring(0, separator));
			if (separator >= 0) {
				bindingName = bindingName.substring(separator + 1);
			}
			Binding binding = WSDLUtils.getBinding(definition, bindingNamespace, bindingName);
			if (binding == null) {
				throw new ParseException("Could not find binding " + bindingName + " in namespace " + bindingNamespace, 0);
			}
			servicePort.setBinding(binding);
			List<Element> portChildren = getChildren(children.get(0));
			if (portChildren.size() > 1) {
				throw new ParseException("Unexpected elements", 0);
			}
			else if (portChildren.size() == 1) {
				if (SOAP_NAMESPACES.contains(portChildren.get(0).getNamespaceURI())) {
					setSoapNamespace(portChildren.get(0).getNamespaceURI());
					if (portChildren.get(0).getLocalName().equals("address")) {
						if (portChildren.get(0).hasAttribute("location")) {
							servicePort.setEndpoint(portChildren.get(0).getAttribute("location"));
						}
					}
					else {
						throw new ParseException("Only address is supported here", 0);
					}
				}
				else {
					throw new ParseException("Only soap element supported here", 0);
				}
			}
			service.getPorts().add(servicePort);
		}
		return service;
	}

	private Binding parseBinding(Element element) throws ParseException {
		BindingImpl binding = new BindingImpl();
		binding.setDefinition(definition);
		if (element.hasAttribute("name")) {
			binding.setName(element.getAttribute("name"));
		}
		String portTypeName = element.getAttribute("type");
		int separator = portTypeName.indexOf(':');
		String namespace = element.lookupNamespaceURI(separator < 0 ? null : portTypeName.substring(0, separator));
		if (separator >= 0) {
			portTypeName = portTypeName.substring(separator + 1);
		}
		PortType portType = WSDLUtils.getPortType(definition, namespace, portTypeName);
		if (portType == null) {
			throw new ParseException("Could not find portType for namespace " + namespace + " name " + portTypeName, 0);
		}
		binding.setPortType(portType);
		boolean foundBinding = false;
		for (Element child : getChildren(element)) {
			// if there is no style attribute, it is implicitly document (according to spec)
			Style defaultStyle = null;
			if (SOAP_NAMESPACES.contains(child.getNamespaceURI())) {
				setSoapNamespace(child.getNamespaceURI());
				if (child.getLocalName().equals("binding")) {
					foundBinding = true;
					if (child.hasAttribute("style")) {
						defaultStyle = Style.valueOf(child.getAttribute("style").toUpperCase());
					}
					if (!SOAP_HTTP_NAMESPACE.equals(child.getAttribute("transport"))) {
						throw new ParseException("Currently only http binding is supported", 0);
					}
					else {
						binding.setTransport(Transport.HTTP);
					}
				}
				else {
					throw new ParseException("Unexpected soap declaration: " + child.getLocalName(), 0);
				}
			}
			else if (child.getLocalName().equals("operation")) {
				BindingOperationImpl bindingOperation = new BindingOperationImpl();
				bindingOperation.setDefinition(definition);
				Operation operation = WSDLUtils.getOperation(portType, child.getAttribute("name"));
				if (operation == null) {
					throw new ParseException("Could not find the operation " + child.getAttribute("name"), 0);
				}
				bindingOperation.setOperation(operation);
				bindingOperation.setStyle(defaultStyle);
				for (Element operationChild : getChildren(child)) {
					if (SOAP_NAMESPACES.contains(operationChild.getNamespaceURI())) {
						setSoapNamespace(operationChild.getNamespaceURI());
						if (operationChild.getLocalName().equals("operation")) {
							if (operationChild.hasAttribute("soapAction")) {
								bindingOperation.setSoapAction(operationChild.getAttribute("soapAction"));
							}
							if (operationChild.hasAttribute("style")) {
								bindingOperation.setStyle(Style.valueOf(operationChild.getAttribute("style").toUpperCase()));
							}
						}
						else {
							throw new ParseException("Unexpected soap definition", 0);
						}
					}
					else if (operationChild.getLocalName().equals("input") || operationChild.getLocalName().equals("output") || operationChild.getLocalName().equals("fault")) {
						List<Element> children = getChildren(operationChild);
						if (children.size() > 1) {
							throw new ParseException("Unexpected elements", 0);
						}
						if (children.size() > 0) {
							if (!SOAP_NAMESPACES.contains(children.get(0).getNamespaceURI())) {
								throw new ParseException("Unexpected elements", 0);
							}
							if (!children.get(0).getLocalName().equals("body")) {
								throw new ParseException("Unexpected elements", 0);
							}
							setSoapNamespace(children.get(0).getNamespaceURI());
							if (children.get(0).hasAttribute("use")) {
								bindingOperation.setUse(Use.valueOf(children.get(0).getAttribute("use").toUpperCase()));
							}
						}
					}
				}
				binding.getOperations().add(bindingOperation);
			}
		}
		if (!foundBinding) {
			throw new ParseException("Could not find soap binding", 0);
		}
		return binding;
	}

	private PortType parsePortType(Element element) throws ParseException {
		PortTypeImpl portType = new PortTypeImpl();
		portType.setDefinition(definition);
		if (element.hasAttribute("name")) {
			portType.setName(element.getAttribute("name"));
		}
		for (Element child : getChildren(element)) {
			if (!child.getLocalName().equals("operation")) {
				throw new ParseException("Unexpected element in port type: " + child.getNodeName(), 0);
			}
			OperationImpl operation = new OperationImpl();
			operation.setDefinition(definition);
			if (child.hasAttribute("name")) {
				operation.setName(child.getAttribute("name"));
			}
			
			for (Element operationChild : getChildren(child)) {
				String messageName = operationChild.getAttribute("message");
				int separator = messageName.indexOf(':');
				String namespace = operationChild.lookupNamespaceURI(separator < 0 ? null : messageName.substring(0, separator));
				if (separator >= 0) {
					messageName = messageName.substring(separator + 1);
				}
				Message message = WSDLUtils.getMessage(definition, namespace, messageName);
				if (message == null) {
					throw new ParseException("Can not find message " + messageName + " in namespace " +  namespace, 0);
				}
				if (operationChild.getLocalName().equals("input")) {
					operation.setInput(message);
				}
				else if (operationChild.getLocalName().equals("output")) {
					operation.setOutput(message);
				}
				else if (operationChild.getLocalName().equals("fault")) {
					operation.getFaults().add(message);
				}
				else {
					throw new ParseException("Unexpected element in operation " + operationChild.getNodeName(), 0);
				}
			}
			portType.getOperations().add(operation);
		}
		return portType;
	}

	private Message parseMessage(Element element) throws ParseException {
		List<Element> children = getChildren(element);
		MessageImpl message = new MessageImpl();
		message.setDefinition(definition);
		if (element.hasAttribute("name")) {
			message.setName(element.getAttribute("name"));
		}
		for (Element child : children) {
			if (!child.getLocalName().equals("part")) {
				throw new ParseException("Unexpected element in message: " + child.getNodeName(), 0);
			}
			MessagePartImpl messagePart = new MessagePartImpl();
			messagePart.setDefinition(definition);
			if (child.hasAttribute("name")) {
				messagePart.setName(child.getAttribute("name"));
			}
			if (child.hasAttribute("element")) {
				String elementName = child.getAttribute("element");
				int separator = elementName.indexOf(':');
				// if there is no separator, it is in the "xmlns" namespace
				String namespace = element.lookupNamespaceURI(separator < 0 ? this.namespace : elementName.substring(0, separator)); 
				if (namespace == null) {
					throw new ParseException("Can not determine the namespace for the element in message " + children.get(0).getAttribute("name"), 0);
				}
				be.nabu.libs.types.api.Element<?> referencedElement = definition.getRegistry().getElement(namespace, separator < 0 ? elementName : elementName.substring(separator + 1));
				if (referencedElement == null) {
					throw new ParseException("Can not resolve the element " + elementName + " for the message " + element.getAttribute("name"), 0);
				}
				messagePart.setElement(referencedElement);
			}
			message.getParts().add(messagePart);
		}
		return message;
	}

	
	public void register(TypeRegistry...registries) {
		if (registry == null) {
			registry = new TypeRegistryImpl();
		}
		registry.register(registries);
	}

	private TypeRegistry parseTypes(Element element) throws SAXException, ParseException, IOException {
		List<Document> delayed = new ArrayList<Document>();
		ModifiableTypeRegistry registry = new TypeRegistryImpl();
		List<TypeRegistry> schemas = new ArrayList<TypeRegistry>();
		TypeRegistryResolver resolver = new TypeRegistryResolver(schemas, getResolver());
		for (Element child : getChildren(element)) {
			if (!child.getLocalName().equals("schema")) {
				throw new ParseException("Unexpected element in the types element: " + child.getNodeName(), 0);
			}
			// TODO: namespaces might be missing at this level, e.g. the "xsd" namespace might be in the root
			Document document = XMLUtils.newDocument(true);
			document.appendChild(document.importNode(child, true));
			NamedNodeMap attributes = this.document.getDocumentElement().getAttributes();
			// inherit attributes
			for (int i = 0; i < attributes.getLength(); i++) {
				if (!document.getDocumentElement().hasAttribute(attributes.item(i).getNodeName())) {
					document.getDocumentElement().setAttribute(attributes.item(i).getNodeName(), attributes.item(i).getTextContent());
				}
			}
			XMLSchema schema = new XMLSchema(document, stringsOnly);
			if (id != null) {
				schema.setId(id + ".types.schema" + (schemas.size() + 1));
			}
			// if we have added custom registries, set them in the XML parser
			if (this.registry != null) {
				schema.register(this.registry);
				schema.setPrioritizeIncludes(true);
			}
			schema.setResolver(resolver);
			try {
				schema.parse();
				schemas.add(schema);
				registry.register(schema);
			}
			catch (Exception e) {
				// in case of an exception, we need to retry it later, it may reference another schema in this wsdl
				delayed.add(document);
			}
		}
		List<Exception> exceptions = new ArrayList<Exception>();
		while (delayed.size() > 0) {
			int size = delayed.size();
			Iterator<Document> iterator = delayed.iterator();
			exceptions.clear();
			while (iterator.hasNext()) {
				XMLSchema schema = new XMLSchema(iterator.next(), stringsOnly);
				if (id != null) {
					schema.setId(id + ".types.schema" + (schemas.size() + 1));
				}
				if (this.registry != null) {
					schema.register(this.registry);
					schema.setPrioritizeIncludes(true);
				}
				schema.setResolver(resolver);
				try {
					schema.parse();
					registry.register(schema);
					schemas.add(schema);
					iterator.remove();
				}
				catch (Exception e) {
					exceptions.add(e);
				}
			}
			if (delayed.size() == size) {
				for (Exception e : exceptions) {
					e.printStackTrace();
				}
				throw new ParseException("Could not parse all schemas: " + exceptions, 0);
			}
		}
		return registry;
	}

	private WSDLDefinition parseImport(Element element) throws ParseException, IOException, URISyntaxException, SAXException, ParserConfigurationException {
		String namespace = element.getAttribute("namespace");
		String location = element.getAttribute("location");
		WSDLParser parser = new WSDLParser(getResolver().resolve(new URI(location)), stringsOnly);
		WSDLDefinition definition = parser.getDefinition();
		if (!namespace.equals(definition.getTargetNamespace())) {
			throw new ParseException("The namespace of the imported wsdl does not match the defined namespace", 0);
		}
		return definition;
	}
	
	public void setResolver(ResourceResolver resolver) {
		this.resolver = new WSDLResourceResolver(definition, resolver);
	}

	public ResourceResolver getResolver() {
		if (resolver == null) {
			resolver = new WSDLResourceResolver(definition, null);
		}
		return resolver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
