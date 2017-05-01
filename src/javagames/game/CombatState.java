package javagames.game;

public class CombatState implements ObjectState 
{
	protected String  name;
	protected LivingObject owner;
	
	public CombatState()
	{
		name = "Idle";
	}
	
	public CombatState(String name)
	{
		this.name = name;
	}
	
	public boolean shouldChangeState()
	{
		return owner.getCurrentHealth() <= 0.f;
	}
	
	protected CombatState getNextState()
	{
		if(owner.getCurrentHealth() <= 0.f)
		{
			return new CombatState("Dead");
		}
		
		return this;
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(shouldChangeState())
		{
			owner.setState(getNextState());
		}
	}
	
	
	public void enter()
	{
		owner.startAnimation(name);
	}
	
	@Override
	public void setOwner(MultiStateObject owner)
	{
		this.owner=(LivingObject)owner;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof ObjectState)
		{
			return ((ObjectState)o).getName().equalsIgnoreCase(name);
		}
		else if(o instanceof String)
		{
			return ((String)o).equalsIgnoreCase(name);
		}
		return false;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
	
}
