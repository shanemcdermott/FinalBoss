package javagames.game;

import javagames.g2d.SpriteSheet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class Pawn extends PhysicsObject 
{
	protected float healthBase;
	protected float healthScale;
	protected float healthBonus;
	protected float healthCurrent;

	protected float speedScale;

	protected SpriteSheet sprite;
	
	Map<ActionType,GameAction>  actions;
	protected ActionType		currentAction;
	
	public Pawn(String name, SpriteSheet sprite)
	{
		super(name);
		healthBase = 10.f;
		healthScale = 1.f;
		healthBonus = 0.f;
		speedScale = 1.f;
		this.sprite = sprite;
		this.sprite.startAnimation("StandDown");
		
		actions = Collections.synchronizedMap(new HashMap<ActionType, GameAction>());
		currentAction = ActionType.IDLE;
	}
	
	public Pawn(String name, SpriteSheet sprite, BoundingShape bounds) 
	{
		super(name, bounds);
		healthBase = 10.f;
		healthScale = 1.f;
		healthBonus = 0.f;
		speedScale = 1.f;
		
		this.sprite = sprite;
		this.sprite.startAnimation("StandDown");
		
		actions = Collections.synchronizedMap(new HashMap<ActionType, GameAction>());
		currentAction = ActionType.IDLE;
	}

	public void startAnimation(String animName)
	{
		((SpriteSheet)sprite).startAnimation(animName);
	}
	
	protected void startAction(ActionType action)
	{
		if(actions.get(action).canStart())
		{
			actions.get(currentAction).stop();
			currentAction = action;
		}
	}
	
	protected void stopAction(ActionType action)
	{
		currentAction = ActionType.IDLE;
	}

	protected void die(Object source)
	{
		
	}
	
	public void reset()
	{
		resetHealth();
	}
	
	protected void resetHealth()
	{
		healthCurrent = getMaxHealth();
	}
	
	public float getCurrentHealth()
	{
		return healthCurrent;
	}
	
	public float getMaxHealth()
	{
		return healthBase * healthScale + healthBonus;
	}
	
	public void takeDamage(Object source, float amount)
	{
		healthCurrent -= amount;
		
		if(healthCurrent <= 0.f)
			die(source);
	}

	public boolean isDead()
	{
		return getCurrentHealth() <= 0.f;
	}
	
	protected void stopMoving()
	{
		if(currentAction==ActionType.IDLE)
		{
			String oldAnim = ((SpriteSheet)sprite).getCurrentAnimation();
			((SpriteSheet)sprite).startAnimation(oldAnim.replaceAll("Walk", "Stand"));
		}
		
		velocity = new Vector2f();
	}
	
	protected void move(String direction)
	{
		if(currentAction==ActionType.IDLE)
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
		if(currentAction!=ActionType.IDLE)
		{
			
		}
			
		((SpriteSheet)sprite).update(deltaTime);
	}
	
}
