package javagames.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
	protected List<GameObject> effects;
	protected String 		currentState;
	protected SpriteSheet 	sprite;

	
	public MultiStateObject(String name, SpriteSheet sprite)
	{
		super(name);
		this.sprite = sprite;
		states = Collections.synchronizedMap(new HashMap<String, ObjectState>());
		effects = new Vector<GameObject>();
		setupStates();
		currentState = "Idle";
	}
	
	public MultiStateObject(String name, BoundingShape bounds, SpriteSheet sprite) 
	{
		super(name, bounds);
		this.sprite=sprite;
		states = Collections.synchronizedMap(new HashMap<String, ObjectState>());
		effects = new Vector<GameObject>();
		setupStates();
		currentState = "Idle";
	}

	@Override
	public void reset()
	{
		super.reset();
		setState("Idle");
		for(ObjectState os: states.values())
		{
			os.reset();
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
	
	public void addStates(ObjectState... inStates)
	{
		for(ObjectState os : inStates)
		{
			os.setOwner(this);
			states.put(os.getName(), os);
			if(os.getEffect()!=null)
			effects.add(os.getEffect());
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
		if(!currentState.equals("Idle")) 
		{
			states.get(currentState).exit();
		}
		
		currentState = newState;
		if (states.containsKey(currentState)) 
		{
			states.get(currentState).enter();
		}
		
	}
	
	public ObjectState getCurrentState()
	{
		if(currentState.equals("Idle"))
		{
			return new CombatState("Idle");
		}
		return states.get(currentState);
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(!currentState.equals("Idle")) 
			states.get(currentState).update(deltaTime);
			
		updateEffects(deltaTime);
		super.update(deltaTime);
		sprite.update(deltaTime);
	}
	
	protected void updateEffects(float deltaTime)
	{
		for(GameObject e : effects)
		{
			e.update(deltaTime);
		}
	}
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		sprite.render(g, view, position.sub(posOffset), rotation);
		drawEffect(g,view,posOffset);
	}
	
	public void drawEffect(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		for(GameObject e : effects)
		{
			if(e.isActive())
				e.draw(g, view,posOffset);
		}

	}
	
	public List<GameObject> getEffects()
	{
		return effects;
	}
	
}
