package javagames.combat;

import java.awt.Graphics2D;

import javagames.game.GameObject;
import javagames.game.MultiStateObject;
import javagames.game.ObjectState;
import javagames.game.Ownable;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

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
	
	protected String getNextState()
	{
		if(owner.getCurrentHealth() <= 0.f)
		{
			return "Dead";
		}
		
		return "Idle";
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(shouldChangeState())
		{
			owner.setState(getNextState());
		}
	}
	
	public boolean canEnter()
	{
		return true;
	}
	
	@Override
	public void reset()
	{
		
	}
	
	@Override
	public void enter()
	{
		owner.startAnimation(name);
	}
	
	@Override
	public void setOwner(MultiStateObject owner)
	{
		this.owner=(LivingObject)owner;
		GameObject effect = getEffect();
		if(effect != null && effect instanceof Ownable)
		{
			((Ownable)effect).setOwner(owner);
		}
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
	public void exit() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameObject getEffect()
	{
		return null;
	}

	@Override
	public GameObject getOwner() 
	{
		return owner;
	}

	@Override
	public void setOwner(GameObject owner) {
		setOwner((MultiStateObject)owner);
	}
	
}
