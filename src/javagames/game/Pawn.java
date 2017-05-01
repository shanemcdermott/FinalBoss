package javagames.game;

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
		setState(new CombatState());
		startAnimation("WalkDown");
	}
	
	public Pawn(String name, SpriteSheet sprite, BoundingShape bounds) 
	{
		super(name, bounds, sprite);

		speedScale = 1.f;		
		setState(new CombatState());
		startAnimation("WalkDown");
	}

	
	
	protected void stopMoving()
	{
		if(currentState.equals("Idle"))
		{
			String oldAnim = ((SpriteSheet)sprite).getCurrentAnimation();
			((SpriteSheet)sprite).startAnimation(oldAnim.replaceAll("Walk", "Stand"));
		}
		
		velocity = new Vector2f();
	}
	
	protected void move(String direction)
	{
		if(currentState.equals("Idle"))
		{
			((SpriteSheet)sprite).startAnimation(direction);
		}
		
		Vector2f dir = new Vector2f();
		switch(direction)
		{
		case "WalkUp":
			dir = Vector2f.up();
			break;
		case "WalkDown":
			dir = Vector2f.down();
			break;
		case "WalkLeft":
			dir = Vector2f.left();
			break;
		case "WalkRight":
			dir = Vector2f.right();
			break;
		}
		velocity = dir.mul(speedScale);
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	
}
