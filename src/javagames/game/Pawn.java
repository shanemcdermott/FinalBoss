package javagames.game;

public abstract class Pawn extends GameObject 
{
	protected float healthBase;
	protected float healthScale;
	protected float healthBonus;
	protected float healthCurrent;
	
	protected float speedScale;
	
	protected abstract void fastAction();
	protected abstract void powerAction();
	protected abstract void specialAction();
	
	protected abstract void die(Object source);
	
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
		return healthBase * healthBonus + healthBonus;
	}
	
	public void takeDamage(Object source, float amount)
	{
		healthCurrent -= amount;
		
		if(healthCurrent <= 0.f)
			die(source);
	}

	
}
