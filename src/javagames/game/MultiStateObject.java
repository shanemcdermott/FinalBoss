package javagames.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.g2d.SpriteSheet;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class MultiStateObject extends PhysicsObject 
{
	protected ObjectState 	currentState;
	protected SpriteSheet 	sprite;

	
	public MultiStateObject(String name, SpriteSheet sprite)
	{
		super(name);
		this.sprite = sprite;
	}
	
	public MultiStateObject(String name, BoundingShape bounds, SpriteSheet sprite) 
	{
		super(name, bounds);
		this.sprite=sprite;
	}

	@Override
	public void reset()
	{
		super.reset();
		if(currentState != null)
		{
			currentState.reset();
		}
	}
	
	public void startAnimation(String animation)
	{
		sprite.startAnimation(animation);
	}
	
	public void setState(ObjectState newState) 
	{
		if (currentState != null) {
			currentState.exit();
		}
		if (newState != null) {
			newState.setOwner(this);
			newState.enter();
		}
		currentState = newState;
	}
	
	
	@Override
	public void update(float deltaTime)
	{
		if(currentState !=null)
			currentState.update(deltaTime);
		
		super.update(deltaTime);
		sprite.update(deltaTime);
	}
	
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		sprite.render(g, view, position.sub(posOffset), rotation);
	}
	
}
