package javagames.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.Avatar;
import javagames.game.GameObject;
import javagames.game.Pawn;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingShape;

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
	
	public static Vector2f getNestedVector2f(Element element, String tagName)
	{
		return XMLUtility.getVector2f(XMLUtility.getElement(element, tagName));
	}
	
	public static BoundingShape getBoundingShape(Element element)
	{
		BoundingShape shape = null;
		
		Element type = XMLUtility.getElement(element, "box");
		if(type == null)
		{
			type = XMLUtility.getElement(element,"circle");
			shape = new BoundingCircle(XMLUtility.getNestedVector2f(type, "center"), Float.parseFloat(type.getAttribute("radius")));
		}
		else
		{
			shape = new BoundingBox(XMLUtility.getNestedVector2f(type, "min"), XMLUtility.getNestedVector2f(type, "max"));
		}
		if(element.hasAttribute("collisionChannel"))
			shape.setCollisionChannel(element.getAttribute("collisionChannel"));
		return shape;
	}
	
	public static GameObject loadGameObject(Class<?> clazz, Element element)
	{
		GameObject gameObject = null;
		try
		{
			switch(element.getAttribute("type"))
			{
				case "PAWN":
		
				default:
					gameObject = loadStationaryObject(clazz, element);
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
			System.err.println(e);
		}
		
		return gameObject;
	}
	
	public static GameObject loadStationaryObject(Class<?> clazz, Element element) throws Exception
	{
		GameObject gameObject = null;
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			gameObject = new GameObject(element.getAttribute("name"), bounds);
		}
		else
		{
			gameObject = new GameObject(element.getAttribute("name"));
		}
		gameObject.setPosition(XMLUtility.getVector2f(XMLUtility.getElement(element, "position")));
		
		for(Element tag : XMLUtility.getElements(element, "tag"))
		{
			gameObject.addTag(tag.getAttribute("name"));
		}
		return gameObject;
	}
	
	public static Pawn loadPawn(Class<?> clazz, Element element) throws Exception
	{
		SpriteSheet spr = (SpriteSheet)ResourceLoader.loadSprite(clazz,XMLUtility.getElement(element, "sprite"));
		
		return new Pawn(element.getAttribute("name"), spr);
	}
}