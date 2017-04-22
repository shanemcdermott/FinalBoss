package javagames.game;

import javagames.g2d.SpriteSheet;
import javagames.g2d.Sprite;
import javagames.util.Vector2f;

public abstract class Pawn extends PhysicsObject 
{
	protected float healthBase;
	protected float healthScale;
	protected float healthBonus;
	protected float healthCurrent;
	
	protected float speedScale;

	public Pawn(String name, SpriteSheet sprite) 
	{
		super(name, sprite);
		healthBase = 10.f;
		healthScale = 1.f;
		healthBonus = 0.f;
		speedScale = 1.f;
		((SpriteSheet)sprite).startAnimation("WalkLeft");
	}


	protected abstract void fastAction();
	protected abstract void powerAction();
	protected abstract void specialAction();
	protected abstract void die(Object source);
	
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

	protected void move(Vector2f direction)
	{
		velocity = direction.mul(speedScale);
	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		((SpriteSheet)sprite).update(deltaTime);
	}
	
}
