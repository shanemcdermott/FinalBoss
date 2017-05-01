package javagames.game;

import javagames.util.Vector2f;

public class PhysicsState implements ObjectState 
{

	protected String name;
	protected Vector2f direction;
	protected Physics physics;
	
	
	public PhysicsState(String name)
	{
		this.name = name;
		direction = new Vector2f();
		physics = null;
	}
	
	public void setDirection(Vector2f direction)
	{
		this.direction=direction;
	}
	
	@Override
	public void update(float deltaTime) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() 
	{
		physics.stopMotion();
	}

	@Override
	public void setOwner(MultiStateObject owner) 
	{
		if(physics == null)
		{
			physics = new Physics(owner);
		}
		else
		{
			physics.owner = owner;
		}		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
