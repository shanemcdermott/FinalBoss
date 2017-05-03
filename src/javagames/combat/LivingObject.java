package javagames.combat;

import java.awt.Color;
import java.awt.Graphics2D;

import javagames.g2d.SpriteSheet;
import javagames.game.MultiStateObject;
import javagames.game.ObjectState;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class LivingObject extends MultiStateObject implements Damageable
{
	protected float healthBase;
	protected int currentHealth;
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

	public void setJob(CombatArchetype job)
	{
		this.job = job;
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
		currentHealth = getMaxHealth();
	}
	
	@Override
	public int getCurrentHealth()
	{
		return (int) currentHealth;
	}
	
	@Override
	public int getMaxHealth()
	{
		return (int) job.getMaxHealth(healthBase);
	}
	
	@Override
	public void takeDamage(Object source, float amount)
	{
		System.out.printf("%s took %f damage!\n", name, amount);
		currentHealth -= amount;
		if(currentHealth <= 0)
			die(source);
	}

	@Override
	public boolean isDead()
	{
		return currentHealth <= 0 || currentState.equals("Dead");
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
		updateJobActions(deltaTime);
	}
	
	protected void updateJobActions(float deltaTime)
	{
		for(String s : job.actions)
		{
			if(s.equals(currentState)) continue;
				states.get(s).update(deltaTime);
		}
	}
	
	
	
	protected void drawActiveEffects(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
//		for(String s : job.actions)
//		{
//			((CombatAction)states.get(s)).drawEffect(g, view, posOffset);
//		}
	}
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		super.draw(g, view, posOffset);
		drawActiveEffects(g,view,posOffset);
	}
}
