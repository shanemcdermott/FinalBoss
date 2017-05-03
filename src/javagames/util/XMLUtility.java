package javagames.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javagames.combat.Avatar;
import javagames.combat.CombatAction;
import javagames.combat.CombatArchetype;
import javagames.combat.CombatState;
import javagames.combat.DamageObject;
import javagames.combat.Enemy;
import javagames.combat.LivingObject;
import javagames.combat.MeleeAction;
import javagames.combat.Pawn;
import javagames.combat.Projectile;
import javagames.combat.RangedAction;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.GameObject;
import javagames.game.MultiStateObject;
import javagames.game.ObjectState;
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
		try
		{
			switch(element.getAttribute("type"))
			{
			case "enemy":
					return loadEnemy(clazz,element);
				case "pawn":
					return loadPawn(clazz,element);
				case "living":
					return loadLivingObject(clazz, element);
				case "damage":
					return loadDamageObject(clazz, element);
				case "projectile":
					return loadProjectile(clazz, element);
				default:
					return loadStationaryObject(clazz, element);
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
			System.err.println(e);
		}
		
		return null;
	}
	
	public static LivingObject loadLivingObject(Class<?> clazz, Element element) throws Exception
	{
		LivingObject object = null;
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		SpriteSheet spr = ResourceLoader.loadSpriteSheet(clazz,XMLUtility.getElement(element, "sprite"));
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			object = new LivingObject(element.getAttribute("name"), bounds, spr);
		}
		else
		{
			object = new LivingObject(element.getAttribute("name"), spr);
		}
		
		object.setPosition(XMLUtility.getVector2f(element));
		
		for(Element ele : XMLUtility.getElements(element, "state"))
		{
			object.addStates(XMLUtility.loadObjectState(clazz, ele));
		}
		
		return object;
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
	
	public static Enemy loadEnemy(Class<?> clazz, Element element) throws Exception
	{
		String name = element.getAttribute("name");
		Enemy object = null;	
		SpriteSheet spr = ResourceLoader.loadSpriteSheet(clazz,XMLUtility.getElement(element, "sprite"));
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			object = new Enemy(name, spr, bounds);
		}
		else
		{
			object = new Enemy(name, spr);
		}
		
		for(Element ele : XMLUtility.getElements(element, "state"))
		{
			object.addStates(XMLUtility.loadObjectState(clazz, ele));
		}
		
		object.setPosition(XMLUtility.getVector2f(element));
		
		Element job = XMLUtility.getElement(element, "job");
		CombatArchetype com = XMLUtility.loadCombatClass(clazz, element.getAttribute("range"), element.getAttribute("speed"));
		if(com == null)
		{
			System.err.println("Failed to load combat class for " +name + ": "+ job.toString());
		}
		else
			object.setJob(com);
		return object;
	}
	
	public static Avatar loadAvatar(Class<?> clazz, String name) throws Exception
	{
		
		Element element = ResourceLoader.loadXML(clazz, name+".xml");
		Avatar object = null;
		
		SpriteSheet spr = ResourceLoader.loadSpriteSheet(clazz,XMLUtility.getElement(element, "sprite"));
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			object = new Avatar(name, spr, bounds);
		}
		else
		{
			object = new Avatar(name, spr);
		}
		
		for(Element ele : XMLUtility.getElements(element, "state"))
		{
			object.addStates(XMLUtility.loadObjectState(clazz, ele));
		}
				
		return object;
	}
	
	public static CombatArchetype loadCombatClass(Class<?> clazz, String range, String speed) throws Exception
	{
		Element jobXML = ResourceLoader.loadXML(clazz, "Jobs.xml");
		CombatArchetype job = null;
		List<Element> stats = XMLUtility.getElements(jobXML, "job");
		for(Element e : stats)
		{
			if(e.getAttribute("range").equals(range) && e.getAttribute("speed").equals(speed))
			{
				job = new CombatArchetype();
				Element hp = XMLUtility.getElement(e, "health");
				job.setHealthValues(Float.parseFloat(hp.getAttribute("baseBonus")),Float.parseFloat(hp.getAttribute("scale")), Float.parseFloat(hp.getAttribute("bonus")));
				Element statMods = XMLUtility.getElement(e, "stats");
				job.setStrength(Float.parseFloat(statMods.getAttribute("strength")));
				job.setSpeed(Float.parseFloat(statMods.getAttribute("speed")));
				job.setMagic(Float.parseFloat(statMods.getAttribute("magic")));
			}
		}
		
		return job;
	}
	
	public static ObjectState loadObjectState(Class<?> clazz, Element element) throws Exception
	{
		float range = 0.f;
		float chargeTime = 0.f;
		float cooldownTime = 0.f;
		DamageObject effect = null;
		Element effectElement = null;
		switch(element.getAttribute("type"))
		{
			
			case "combat":
			{
				CombatState state = new CombatState(element.getAttribute("name"));
				return state;
			}
			case "ranged":
				
				range = Float.parseFloat(element.getAttribute("range"));
				chargeTime = Float.parseFloat(element.getAttribute("chargeTime"));
				cooldownTime = Float.parseFloat(element.getAttribute("cooldownTime"));
				
				effectElement = XMLUtility.getElement(element, "effect");
				
				if(effectElement != null)
				{
					effect = (DamageObject)XMLUtility.loadGameObject(clazz, effectElement);
				}
				return new RangedAction(element.getAttribute("name"), effect, range,chargeTime,cooldownTime, effectElement);
				
			case "melee":
				range = Float.parseFloat(element.getAttribute("range"));
				chargeTime = Float.parseFloat(element.getAttribute("chargeTime"));
				cooldownTime = Float.parseFloat(element.getAttribute("cooldownTime"));
				effectElement = XMLUtility.getElement(element, "effect");
				
				if(effectElement != null)
				{
					effect = (DamageObject)XMLUtility.loadGameObject(clazz, effectElement);
				}
				return new MeleeAction(element.getAttribute("name"), effect, range,chargeTime,cooldownTime, effectElement);				
			case "action":
			{
				range = Float.parseFloat(element.getAttribute("range"));
				chargeTime = Float.parseFloat(element.getAttribute("chargeTime"));
				cooldownTime = Float.parseFloat(element.getAttribute("cooldownTime"));
				effectElement = XMLUtility.getElement(element, "effect");
				
				if(effectElement != null)
				{
					effect = (DamageObject)XMLUtility.loadGameObject(clazz, effectElement);
				}
				
				return new CombatAction(element.getAttribute("name"), effect, range,chargeTime,cooldownTime, effectElement);
			}
		}
		
		return null;
	}
	
	public static DamageObject loadDamageObject(Class<?> clazz, Element element) throws Exception
	{
		DamageObject object = null;
		SpriteSheet spr = ResourceLoader.loadSpriteSheet(clazz,XMLUtility.getElement(element, "sprite"));	
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			object = new DamageObject(element.getAttribute("name"), bounds, null, spr);
		}
		else
		{
			object = new DamageObject(element.getAttribute("name"), null, spr);
		}
		
		if(element.hasAttribute("lifespan"))
		{
			object.setLifespan(Float.parseFloat(element.getAttribute("lifespan")));
		}
		if(element.hasAttribute("dps"))
		{
			object.setDPS(Float.parseFloat(element.getAttribute("dps")));
		}
		if(element.hasAttribute("canDamageOwner"))
		{
			object.setCanDamageOwner(Boolean.parseBoolean(element.getAttribute("canDamageOwner")));
		}
		
		return object;
	}
	
	public static DamageObject loadProjectile(Class<?> clazz, Element element) throws Exception
	{
		DamageObject object = null;
		SpriteSheet spr = ResourceLoader.loadSpriteSheet(clazz,XMLUtility.getElement(element, "sprite"));	
		float speed = Float.parseFloat(element.getAttribute("speed"));
		
		Element boundsXML = XMLUtility.getElement(element, "bounds");
		if(boundsXML != null)
		{
			BoundingShape bounds = XMLUtility.getBoundingShape(boundsXML);
			object = new Projectile(element.getAttribute("name"), bounds, null, spr, speed);
		}
		else
		{
			object = new Projectile(element.getAttribute("name"), null, spr, speed);
		}
		
		if(element.hasAttribute("lifespan"))
		{
			object.setLifespan(Float.parseFloat(element.getAttribute("lifespan")));
		}
		if(element.hasAttribute("dps"))
		{
			object.setDPS(Float.parseFloat(element.getAttribute("dps")));
		}
		if(element.hasAttribute("canDamageOwner"))
		{
			object.setCanDamageOwner(Boolean.parseBoolean(element.getAttribute("canDamageOwner")));
		}
		
		
		return object;
	}
}