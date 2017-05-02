package javagames.combat;

import javagames.g2d.SpriteSheet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class Pawn extends LivingObject 
{

	
	protected float speedScale;
	
	public Pawn(String name, SpriteSheet sprite)
	{
		super(name, sprite);
		speedScale = 1.f;
		startAnimation("WalkDown");
		
	}
	
	public Pawn(String name, SpriteSheet sprite, BoundingShape bounds) 
	{
		super(name, bounds, sprite);

		speedScale = 1.f;		
		startAnimation("WalkDown");
		
	}

	
	
	public void startAction(String actionName)
	{
		if(actionName.contains("Walk"))
		{
			move(actionName);
		}
		else if(states.containsKey(actionName))
		{
			setState(actionName);
		}
		else
		{
			System.out.printf("%s is not a recognized action!\n", actionName);
		}
	}
	
	protected void stopMoving()
	{
		if(currentState.equals("Idle"))
		{
			String oldAnim = ((SpriteSheet)sprite).getCurrentAnimation();
			((SpriteSheet)sprite).startAnimation(oldAnim.replaceAll("Walk", "Stand"));
		}
		
		physics.stopMotion();
	}
	
	protected void move(String direction)
	{
		if(currentState.equals("Idle"))
		{
			((SpriteSheet)sprite).startAnimation(direction);
		}
		
		physics.move(Vector2f.parse(direction.replace("Move", "")).mul(speedScale));
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	
}
