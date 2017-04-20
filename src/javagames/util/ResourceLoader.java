package javagames.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ResourceLoader {
	
	public static InputStream load(Class<?> clazz, String filePath)
	{
		InputStream in = null;
		if(!(filePath == null || filePath.isEmpty()))
		{
			in = clazz.getResourceAsStream(filePath);
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
		InputStream model = load(clazz, "/xml/" + fileName);
		Document document = XMLUtility.parseDocument(model);
		return document.getDocumentElement();
	}
	
	public static BufferedImage loadImage(Class<?> clazz, String fileName) throws Exception
	{
		InputStream stream = ResourceLoader.load(clazz, "/images/" + fileName);
		return ImageIO.read( stream );
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