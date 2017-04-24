package javagames.g2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class SpriteSheet extends Sprite 
{
	
	private Map<String, Animation> 	animations;
	private String					currentAnimation;

	public SpriteSheet(BufferedImage image, Vector2f topLeft, Vector2f bottomRight, Map<String, Animation> animations) 
	{
		super(image, topLeft, bottomRight);
		this.animations = Collections.synchronizedMap(new HashMap<String, Animation>(animations));
		currentAnimation = "None";
	}

	public void addAnimation(String name, Animation anim)
	{
		animations.put(name, anim);
	}
	
	public void startAnimation(String name)
	{
		currentAnimation = name;
		animations.get(name).start();
	}
	
	public String getCurrentAnimation()
	{
		return currentAnimation;
	}
	
	public void update(float deltaTime)
	{
		animations.get(currentAnimation).update(deltaTime);
		image = animations.get(currentAnimation).getCurrentImage();
		scaled = animations.get(currentAnimation).getCurrentImage();
	}

}
