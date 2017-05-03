package javagames.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javagames.combat.Avatar;
import javagames.g2d.Animation;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.state.LoadingState;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class GameObjectFactory 
{
	
	public static Avatar createAvatar(String name) throws Exception
	{
		switch(name)
		{
		case "Draaknar":
			return createDraaknar();
		case "Queen Zeal":
			return createQueenZeal();
		case "Goonthoro":
			return createGoonthoro();
		case "Nihil":
			return createNihil();
		}
		
		return null;
	}
	
	private static Avatar createDraaknar() throws Exception
	{
		BufferedImage image = ResourceLoader.loadImage(GameObjectFactory.class, "DragonDown.png");
		//image = image.getSubimage(0,0,100,100);
			
		HashMap<String,Animation> animations = new HashMap<String, Animation>();
		Animation anim = new Animation(image, 2, 0.5f, 75, 0, 75, 63);
		animations.put("WalkDown", anim);
		anim = new Animation(image, 1, 0.5f, 0, 0, 75, 63);
		animations.put("StandDown", anim);
		SpriteSheet sprite = new SpriteSheet( image, Vector2f.topLeft(0.5f), Vector2f.bottomRight(0.5f), animations);
		
		//Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
		//sprite.scaleImage( viewport );	
		return new Avatar("Draaknar, the Malevolent", sprite);
	}
	
	private static Avatar createQueenZeal() throws Exception
	{
		BufferedImage image = ResourceLoader.loadImage(GameObjectFactory.class, "DragonDown.png");
		//image = image.getSubimage(0,0,100,100);
		
		HashMap<String,Animation> animations = new HashMap<String, Animation>();
		Animation anim = new Animation(image, 2, 0.5f, 75, 0, 75, 63);
		animations.put("WalkDown", anim);
		anim = new Animation(image, 1, 0.5f, 0, 0, 75, 63);
		animations.put("StandDown", anim);
		SpriteSheet sprite = new SpriteSheet( image, Vector2f.topLeft(0.5f), Vector2f.bottomRight(0.5f), animations);
		
		//Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
		//sprite.scaleImage( viewport );
					
		return new Avatar("Queen Zeal", sprite);
	}

	private static Avatar createGoonthoro() throws Exception
	{
		BufferedImage image = ResourceLoader.loadImage(GameObjectFactory.class, "DragonDown.png");
		//image = image.getSubimage(0,0,100,100);
		
		HashMap<String,Animation> animations = new HashMap<String, Animation>();
		Animation anim = new Animation(image, 2, 0.5f, 75, 0, 75, 63);
		animations.put("WalkDown", anim);
		anim = new Animation(image, 1, 0.5f, 0, 0, 75, 63);
		animations.put("StandDown", anim);
		SpriteSheet sprite = new SpriteSheet( image, Vector2f.topLeft(0.5f), Vector2f.bottomRight(0.5f), animations);
		
		//Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
		//sprite.scaleImage( viewport );
					
		return new Avatar("Goonthoro, the Undead", sprite);
	}

	private static Avatar createNihil() throws Exception
	{
		BufferedImage image = ResourceLoader.loadImage(GameObjectFactory.class, "nihil.png");
		//image = image.getSubimage(0,0,100,100);
				
		HashMap<String,Animation> animations = new HashMap<String, Animation>();
		Animation anim = new Animation(image, 4, 0.4f, 0, 0, 23, 36);
		animations.put("WalkLeft", anim);
		anim = new Animation(image, 1, 0.5f, 0, 0, 23, 36);
		animations.put("StandLeft", anim);
		anim = new Animation(image, 4, 0.4f, 0, 36, 23, 36);
		animations.put("WalkDown", anim);
		anim = new Animation(image, 1, 0.5f, 0, 36, 23, 36);
		animations.put("StandDown", anim);
		
		anim = new Animation(image, 4, 0.4f, 0, 72, 23, 36);
		animations.put("WalkRight", anim);
		anim = new Animation(image, 1, 0.5f, 0, 72, 23, 36);
		animations.put("StandRight", anim);
		anim = new Animation(image, 4, 0.4f, 0, 108, 23, 36);
		animations.put("WalkUp", anim);
		anim = new Animation(image, 1, 0.5f, 0, 108, 23, 36);
		animations.put("StandUp", anim);
		
		SpriteSheet sprite = new SpriteSheet( image, Vector2f.topLeft(0.5f), Vector2f.bottomRight(0.5f), animations);
		
		//Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
		//sprite.scaleImage( viewport );
					
		return new Avatar("Nihil, the Dead Priest", sprite);
	}
}
