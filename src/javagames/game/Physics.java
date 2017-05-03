package javagames.game;

import javagames.g2d.SpriteSheet;
import javagames.util.Vector2f;

public class Physics 
{
	protected GameObject 	owner;
	protected float 		rotationRate = 0.f;
	protected Vector2f 		acceleration = new Vector2f();
	protected Vector2f 		velocity = new Vector2f();
	protected Vector2f		maxVelocity;
	
	public Physics(GameObject owner)
	{
		this.owner = owner;
		rotationRate = 0.f;
		velocity = new Vector2f();
		acceleration = new Vector2f();
		maxVelocity = new Vector2f(5.f,5.f);
	}
	
	public void update(float deltaTime)
	{
		velocity = velocity.add(acceleration.mul(deltaTime));
		owner.setPosition(owner.position.add(velocity.mul(deltaTime)));
		owner.setRotation(owner.rotation + rotationRate * deltaTime);
	}
	
	public void setMaxVelocity(Vector2f newMax)
	{
		maxVelocity = new Vector2f(newMax);
	}
	
	public void move(Vector2f direction)
	{
		velocity = new Vector2f(direction);
	}
	
	public void stopMotion()
	{
		velocity = new Vector2f();
		acceleration = new Vector2f();
	}
	
	public boolean isMoving()
	{
		return !velocity.equals(new Vector2f());
	}

	@Override
	public String toString()
	{
		return String.format("Velocity: %s\tAccel: %s\n", velocity.toString(), acceleration.toString());
	}
}
