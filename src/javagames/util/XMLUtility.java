package javagames.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLUtility {
	
	public static Document parseDocument(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(inputStream));
		return document;
	}

	public static Document parseDocument(Reader reader)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(reader));
		return document;
	}

	public static List<Element> getAllElements(Element element, String tagName) {
		ArrayList<Element> elements = new ArrayList<Element>();
		NodeList nodes = element.getElementsByTagName(tagName);
		for (int i = 0; i < nodes.getLength(); i++) {
			elements.add((Element) nodes.item(i));
		}
		return elements;
	}

	public static List<Element> getElements(Element element, String tagName) {
		ArrayList<Element> elements = new ArrayList<Element>();
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = node.getNodeName();
				if (nodeName != null && nodeName.equals(tagName)) {
					elements.add((Element) node);
				}
			}
		}
		return elements;
	}
	
	public static Element getElement(Element root, String tagName)
	{
		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = node.getNodeName();
				if (nodeName != null && nodeName.equals(tagName)) {
					return(Element) node;
				}
			}
		}
		return null;
	}
	
	public static Vector2f getVector2f(Element element)
	{
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		return new Vector2f(x,y);
	}
}