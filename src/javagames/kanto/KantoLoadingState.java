package javagames.kanto;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.w3c.dom.Element;

import javagames.g2d.Animation;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.Avatar;
import javagames.game.GameObject;
import javagames.state.LoadingState;
import javagames.state.TitleMenuState;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
import javagames.util.geom.BoundingBox;

public class KantoLoadingState extends LoadingState 
{
	

	public KantoLoadingState()
	{
		super();
		displayString = "Kanto";
		loaded = new ArrayList<String>();
	}
	
	
	
	@Override
	protected void enterNextState() 
	{
		
		KantoRoamingState next = new KantoRoamingState(); 
		next.setController(getController());
		next.addObjects(loaded);
		getController().setState(next);
	}



//	@Override
//	public void loadGameObjects() 
//	{
//		Element objectXML = XMLUtility.getElement(xml, "objects");
//		if(objectXML == null) return;
//		//Load Game objects
//		for (Element barrier : XMLUtility.getElements(objectXML, "barrier"))
//		{
//			
//			loadTasks.add( new Callable<Boolean>() 
//			{
//				@Override
//				public Boolean call() throws Exception 
//				{
//					Sprite sprite = ResourceLoader.loadSprite(this.getClass(), XMLUtility.getElement(barrier, "sprite"));
//					
//					GameObject gameObject = new GameObject(barrier.getAttribute("name"),sprite);
//					gameObject.setPosition(XMLUtility.getVector2f(XMLUtility.getElement(barrier, "coord")));
//					for(Element tag : XMLUtility.getElements(barrier, "tag"))
//					{
//						gameObject.addTag(tag.getAttribute("name"));
//					}
//					loaded.add(barrier.getAttribute("name"));
//					controller.setAttribute(barrier.getAttribute("name"), gameObject );
//						
//					return Boolean.TRUE;
//				}
//			});
//		}
//		
//		Element tiledXML = XMLUtility.getElement(xml, "objectgroup");
//		if(tiledXML == null) return;
//		{
//			for (Element object : XMLUtility.getElements(tiledXML, "object"))
//			{
//				
//				loadTasks.add( new Callable<Boolean>() 
//				{
//					@Override
//					public Boolean call() throws Exception 
//					{
//						Element spr = XMLUtility.getElement(object, "sprite");
//						Sprite sprite = ResourceLoader.loadSprite(this.getClass(), spr);
//						Vector2f boxMin = XMLUtility.getVector2f(XMLUtility.getElement(XMLUtility.getElement(object,"bounds"), "min"));
//						Vector2f boxMax = XMLUtility.getVector2f(XMLUtility.getElement(XMLUtility.getElement(object,"bounds"), "max"));
//						BoundingBox b = new BoundingBox(boxMin,boxMax);
//						Vector2f position =XMLUtility.getVector2f(XMLUtility.getElement(object, "coord"));
//						
//	
//						GameObject gameObject = new GameObject(object.getAttribute("name"),sprite, b);
//						gameObject.setPosition(position);
//						
//						for(Element tag : XMLUtility.getElements(object, "property"))
//						{
//							gameObject.addTag(tag.getAttribute("value"));
//						}
//						
//						loaded.add(object.getAttribute("name"));
//						controller.setAttribute(object.getAttribute("name"), gameObject );
//							
//						return Boolean.TRUE;
//					}
//				});
//			}
//		}
//		
//	}

}
