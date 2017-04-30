package javagames.game;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class PhysicsObject extends GameObject
{
	protected float rotationRate;
	protected Vector2f acceleration;
	protected Vector2f velocity;

	public PhysicsObject(String name) 
	{
		super(name);
		rotationRate = 0.f;
		velocity = new Vector2f();
		acceleration = new Vector2f();
	}
	
	public PhysicsObject(String name, BoundingShape bounds) 
	{
		super(name,bounds);
		rotationRate = 0.f;
		velocity = new Vector2f();
		acceleration = new Vector2f();
	}
	
	@Override
	public void update(float deltaTime)
	{
		velocity = velocity.add(acceleration.mul(deltaTime));
		position = position.add(velocity.mul(deltaTime));
		rotation += rotationRate * deltaTime;
		super.update(deltaTime);
	}

	public void stopMotion()
	{
		velocity = new Vector2f();
		acceleration = new Vector2f();
	}
	
	public boolean isMoving()
	{
		return velocity.equals(new Vector2f()) == false;
	}
	
}
