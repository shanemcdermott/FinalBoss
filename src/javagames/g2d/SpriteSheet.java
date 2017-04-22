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
	private Animation				currentAnimation;

	public SpriteSheet(BufferedImage image, Vector2f topLeft, Vector2f bottomRight, Map<String, Animation> animations) 
	{
		super(image, topLeft, bottomRight);
		this.animations = Collections.synchronizedMap(new HashMap<String, Animation>(animations));
	}

	public void addAnimation(String name, Animation anim)
	{
		animations.put(name, anim);
	}
	
	public void startAnimation(String name)
	{
		currentAnimation = animations.get(name);
		currentAnimation.start();
	}
	
	public void update(float deltaTime)
	{
		currentAnimation.update(deltaTime);
		image = currentAnimation.getCurrentImage();
		scaled = currentAnimation.getCurrentImage();
	}

}
