package javagames.g2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.awt.Color;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class SpriteSheet extends Sprite 
{
	
	private Map<String, Animation> 	animations;
	private String					currentAnimation;
	private Color					color;
	
	public SpriteSheet(BufferedImage image, Vector2f topLeft, Vector2f bottomRight, Map<String, Animation> animations) 
	{
		super(image, topLeft, bottomRight);
		this.animations = Collections.synchronizedMap(new HashMap<String, Animation>(animations));
		currentAnimation = "None";
		color = Color.WHITE;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void addAnimation(String name, Animation anim)
	{
		animations.put(name, anim);
	}
	
	public void startAnimation(String name)
	{
		currentAnimation = name;
		if(animations.containsKey(name))
			animations.get(name).start();
	}
	
	public String getCurrentAnimation()
	{
		return currentAnimation;
	}
	
	public void update(float deltaTime)
	{
		if(animations.containsKey(currentAnimation))
		{
			animations.get(currentAnimation).update(deltaTime);
			image = ImageUtility.colorImage(animations.get(currentAnimation).getCurrentImage(), color);
			scaled = ImageUtility.colorImage(animations.get(currentAnimation).getCurrentImage(), color);
		}
	}

}
