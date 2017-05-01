package javagames.game;

import javagames.g2d.SpriteSheet;
import javagames.util.Vector2f;

public class Physics 
{
	protected GameObject 	owner;
	protected float 		rotationRate = 0.f;
	protected Vector2f 		acceleration = new Vector2f();
	protected Vector2f 		velocity = new Vector2f();
	
	
	public Physics(GameObject owner)
	{
		this.owner = owner;
		rotationRate = 0.f;
		velocity = new Vector2f();
		acceleration = new Vector2f();
	}
	
	public void update(float deltaTime)
	{
		velocity = velocity.add(acceleration.mul(deltaTime));
		owner.setPosition(owner.position.add(velocity.mul(deltaTime)));
		owner.setRotation(owner.rotation + rotationRate * deltaTime);
	}
	
	public void move(Vector2f direction)
	{
		velocity = direction;
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

}
