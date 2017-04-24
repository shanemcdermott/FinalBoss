package javagames.game;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;

public class PhysicsObject extends GameObject
{

	protected float rotationRate;
	protected Vector2f velocity;
	
	public PhysicsObject(String name, Sprite sprite) 
	{
		super(name, sprite);
		rotationRate = 0.f;
		velocity = new Vector2f();
	}
	
	@Override
	public void update(float deltaTime)
	{
		position = position.add(velocity.mul(deltaTime));
		rotation += rotationRate * deltaTime;
		
		super.update(deltaTime);
	}
	
}
