package javagames.game;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class PhysicsObject extends GameObject
{
	protected Physics physics;

	public PhysicsObject(String name) 
	{
		super(name);
		physics = new Physics(this);
	}
	
	public PhysicsObject(String name, BoundingShape bounds) 
	{
		super(name,bounds);
		physics = new Physics(this);
	}
	
	@Override
	public void update(float deltaTime)
	{
		physics.update(deltaTime);
		super.update(deltaTime);
	}

	public Physics getPhysics()
	{
		return physics;
	}
	
	public void stopMotion()
	{
		physics.stopMotion();
	}
	
	public boolean isMoving()
	{
		return physics.isMoving();
	}
	
	@Override
	public Vector2f getForwardVector()
	{
		return physics.velocity;
	}
}
