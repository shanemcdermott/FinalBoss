package javagames.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.combat.CombatAction;
import javagames.combat.CombatState;
import javagames.g2d.SpriteSheet;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class MultiStateObject extends PhysicsObject 
{
	protected Map<String, ObjectState> states;
	protected String 		currentState;
	protected SpriteSheet 	sprite;

	
	public MultiStateObject(String name, SpriteSheet sprite)
	{
		super(name);
		this.sprite = sprite;
		states = Collections.synchronizedMap(new HashMap<String, ObjectState>());
	}
	
	public MultiStateObject(String name, BoundingShape bounds, SpriteSheet sprite) 
	{
		super(name, bounds);
		this.sprite=sprite;
		states = Collections.synchronizedMap(new HashMap<String, ObjectState>());
	}

	@Override
	public void reset()
	{
		super.reset();
		if(states.containsKey(currentState))
		{
			states.get(currentState).reset();
		}
	}
	
	public void startAnimation(String animation)
	{
		sprite.startAnimation(animation);
	}
	
	public void setupStates()
	{
		addStates(new CombatState("Idle"));
	}
	
	public void addStates(ObjectState... InStates)
	{
		for(ObjectState os : InStates)
		{
			os.setOwner(this);
			states.put(os.getName(), os);
		}
	}
	
	public void setState(ObjectState newState)
	{
		if(!states.containsKey(newState.getName()))
		{
			addStates(newState);
		}
		
		setState(newState.getName());
	}
	
	public void setState(String newState) 
	{
		if(states.containsKey(currentState)) 
		{
			states.get(currentState).exit();
		}
		
		currentState = newState;
		if (states.containsKey(currentState)) 
		{
			states.get(currentState).enter();
		}
		
	}
	
	
	@Override
	public void update(float deltaTime)
	{
		if(states.containsKey(currentState)) 
			states.get(currentState).update(deltaTime);
		
		super.update(deltaTime);
		sprite.update(deltaTime);
	}
	
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		sprite.render(g, view, position.sub(posOffset), rotation);
	}
	
}
