package javagames.combat;

import java.awt.Color;

import javagames.g2d.SpriteSheet;
import javagames.game.MultiStateObject;
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
	public void takeDamage(Object source, int amount)
	{
		System.out.printf("%s took %d damage!\n", name, amount);
		currentHealth -= amount;
		sprite.setColor(Color.RED);
		if(currentHealth <= 0)
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

}
