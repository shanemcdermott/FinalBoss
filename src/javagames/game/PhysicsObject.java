package javagames.game;

import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class PhysicsObject extends GameObject
{
	protected Vector2f forward;
	protected Vector2f lastPosition;

	public PhysicsObject(String name) 
	{
		super(name);
		lastPosition = getPosition();
		forward = Vector2f.down();
	}
	
	public PhysicsObject(String name, BoundingShape bounds) 
	{
		super(name,bounds);
		forward = Vector2f.down();
	}
	
	@Override
	public void update(float deltaTime)
	{
		lastPosition=getPosition();
		physics.update(deltaTime);
		if(!physics.velocity.equals(new Vector2f()))
				forward = new Vector2f(physics.velocity);
		
		super.update(deltaTime);
	}

	public Physics getPhysics()
	{
		return physics;
	}
	
	public void setVelocity(Vector2f velocity)
	{
		physics.velocity = velocity;
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
		return forward;
	}
	
	@Override
	public void onBeginOverlap(GameObject other)
	{
		if(other == null || this.equals(other)) return;
		
		if(other.getCollisionResponseTo(getCollisionChannel()).equals("BLOCK"))
		{
			stopMotion();
			setPosition(lastPosition);
			updateTransform();
		}
	}

}
