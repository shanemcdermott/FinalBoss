package javagames.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javagames.combat.Avatar;
import javagames.g2d.Animation;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.state.LoadingState;

public class ResourceLoader {
	
	public static InputStream load(Class<?> clazz, String filePath)
	{
		InputStream in = null;
		if(!(filePath == null || filePath.isEmpty()))
		{
			in = clazz.getResourceAsStream("/res/assets" + filePath);
		}
			
		if(in == null)
		{
			try 
			{
				in = new FileInputStream("res/assets" + filePath);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		return in;
	}
	
	public static Element loadXML(Class<?> clazz, String fileName) throws IOException, SAXException, ParserConfigurationException
	{
		InputStream model = ResourceLoader.load(clazz, "/xml/" + fileName);
		Document document = XMLUtility.parseDocument(model);
		return document.getDocumentElement();
	}
	
	public static BufferedImage loadImage(Class<?> clazz, String fileName) throws Exception
	{
		InputStream stream = ResourceLoader.load(clazz, "/images/" + fileName);
		return ImageIO.read( stream );
	}
	
	public static Sprite loadSprite(Class<?> clazz, Element node, String tag) throws Exception
	{
		Element child = XMLUtility.getElement(node, tag);
		if(child == null)
			return null;
		
		return ResourceLoader.loadSprite(clazz, child);		
	}
	
	public static Sprite loadSprite(Class<?> clazz, Element node) throws Exception
	{
		List<Element> anims = XMLUtility.getElements(node, "animation");
		if(anims.isEmpty())
		{
			BufferedImage image = ResourceLoader.loadImage(clazz, node.getAttribute("file"));
			float halfWidth = 0.5f * Float.parseFloat(node.getAttribute("width"));
			float halfHeight = 0.5f * Float.parseFloat(node.getAttribute("height"));
			
			Vector2f topLeft = new Vector2f(-halfWidth, halfHeight);
			Vector2f bottomRight = new Vector2f(halfWidth, -halfHeight);
			
			if(node.hasAttribute("startX"))
			{
				int startX = Integer.parseInt(node.getAttribute("startX"));
				int startY = Integer.parseInt(node.getAttribute("startY"));
				int imgW = Integer.parseInt(node.getAttribute("imgW"));
				int imgH = Integer.parseInt(node.getAttribute("imgH"));
				image = image.getSubimage(startX, startY, imgW, imgH);
			}
			
			return new Sprite(image, topLeft, bottomRight);
		}
		
		return loadSpriteSheet(clazz,node);
		
	}
	
	public static SpriteSheet loadSpriteSheet(Class<?> clazz, Element node) throws Exception
	{
		BufferedImage image = ResourceLoader.loadImage(clazz, node.getAttribute("file"));
		float halfWidth = 0.5f * Float.parseFloat(node.getAttribute("width"));
		float halfHeight = 0.5f * Float.parseFloat(node.getAttribute("height"));
		
		Vector2f topLeft = new Vector2f(-halfWidth, halfHeight);
		Vector2f bottomRight = new Vector2f(halfWidth, -halfHeight);
		
		HashMap<String,Animation> animations = new HashMap<String, Animation>();
		
		List<Element> anims = XMLUtility.getElements(node, "animation");
		for(Element anim : anims)
		{
			animations.put(anim.getAttribute("name"), loadAnimation(image,anim));
		}
		
		return new SpriteSheet(image, topLeft, bottomRight, animations);
		
	}
	
	public static Animation loadAnimation( BufferedImage image, Element node)
	{
		int frames = Integer.parseInt(node.getAttribute("frames"));
		float frameTime = Float.parseFloat(node.getAttribute("frameTime"));
		int x = Integer.parseInt(node.getAttribute("x"));
		int y = Integer.parseInt(node.getAttribute("y"));
		int width = Integer.parseInt(node.getAttribute("width"));
		int height = Integer.parseInt(node.getAttribute("height"));
		return new Animation(image,frames,frameTime,x,y,width,height);
	}	

	
	public static byte[] loadSound(Class<?> clazz, String fileName)
	{
		InputStream in = load(clazz, "/sound/" + fileName);
		return readBytes(in);
	}
	
	private static byte[] readBytes(InputStream in) 
	{
		try 
		{
			BufferedInputStream buf = new BufferedInputStream(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read;
			while ((read = buf.read()) != -1) {
				out.write(read);
			}
			in.close();
			return out.toByteArray();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}