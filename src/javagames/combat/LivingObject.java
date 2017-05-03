package javagames.combat;

import java.awt.Color;

import javagames.g2d.SpriteSheet;
import javagames.game.MultiStateObject;
import javagames.game.ObjectState;
import javagames.util.geom.BoundingShape;

public class LivingObject extends MultiStateObject implements Damageable
{
	protected float healthBase;
	protected float healthCurrent;
	protected CombatArchetype job;
	
	public LivingObject(String name, SpriteSheet sprite)
	{
		super(name, sprite);
		healthBase = 10.f;
		job = new CombatArchetype();
	}
	
	public LivingObject(String name, BoundingShape bounds, SpriteSheet sprite) 
	{
		super(name, bounds, sprite);
		healthBase = 10.f;
		job = new CombatArchetype();
		
	}

	@Override
	public void die(Object source)
	{
		
	}
	
	@Override
	public void reset()
	{
		super.reset();
		resetHealth();
	}
	
	@Override
	public void resetHealth()
	{
		healthCurrent = getMaxHealth();
	}
	
	@Override
	public float getCurrentHealth()
	{
		return healthCurrent;
	}
	
	@Override
	public float getMaxHealth()
	{
		return job.getMaxHealth(healthBase);
	}
	
	@Override
	public void takeDamage(Object source, float amount)
	{
		System.out.printf("%s took %f damage!\n", name, amount);
		healthCurrent -= amount;
		sprite.setColor(Color.RED);
		if(healthCurrent <= 0.f)
			die(source);
	}

	@Override
	public boolean isDead()
	{
		return currentState.equals("Dead");
	}

	@Override
	public boolean canTakeDamage() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addStates(ObjectState... inStates)
	{
		super.addStates(inStates);
		for(ObjectState s : inStates)
		{
			if(s instanceof CombatAction)
			{
				job.actions.add(s.getName());
			}
		}
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		for(String s : job.actions)
		{
			states.get(s).update(deltaTime);
		}
	}
}
