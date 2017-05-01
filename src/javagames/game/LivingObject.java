package javagames.game;

import javagames.g2d.SpriteSheet;
import javagames.util.geom.BoundingShape;

public class LivingObject extends MultiStateObject implements Damageable
{
	protected float healthBase;
	protected float healthScale;
	protected float healthBonus;
	protected float healthCurrent;
	
	
	public LivingObject(String name, SpriteSheet sprite)
	{
		super(name, sprite);
		healthBase = 10.f;
		healthScale = 1.f;
		healthBonus = 0.f;
	}
	
	public LivingObject(String name, BoundingShape bounds, SpriteSheet sprite) 
	{
		super(name, bounds, sprite);
		healthBase = 10.f;
		healthScale = 1.f;
		healthBonus = 0.f;
		
	}

	@Override
	public void die(Object source)
	{
		
	}
	
	@Override
	public void reset()
	{
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
		return healthBase * healthScale + healthBonus;
	}
	
	@Override
	public void takeDamage(Object source, float amount)
	{
		healthCurrent -= amount;
		
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

}
