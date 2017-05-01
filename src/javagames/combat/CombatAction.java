package javagames.combat;

import javagames.game.GameObject;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class CombatAction extends CombatState 
{
	
	protected float range;
	protected float currentTime;
	protected float chargeTime;
	protected float cooldownTime;

	protected boolean bIsCharging;
	
	protected GameObject effect;
	
	public CombatAction()
	{
		this("Punch");
	}
	
	public CombatAction(String name)
	{
		super(name);
		range = 1.f;
		currentTime = 0.f;
		chargeTime = 1.f;
		cooldownTime = 0.f;
		bIsCharging=false;
		effect = null;
	}
	
	@Override
	public void reset()
	{
		currentTime = 0.f;
	}
	
	@Override
	public void enter()
	{
		owner.startAnimation(name);
		reset();
		bIsCharging=true;
	}
	
	@Override
	public void update(float deltaTime)
	{
		currentTime += deltaTime;
		if(isCharging() && isFinishedCharging())
		{
			// TODO Auto-generated constructor stub
		}
		else
		{
			
		}
		
		if(shouldChangeState())
		{
			owner.setState(getNextState());
		}
	}
	
	public boolean canEnter()
	{
		return currentTime >= cooldownTime;
	}
	
	public boolean isCharging()
	{
		return bIsCharging;
	}
	
	public boolean shouldChangeState()
	{
		return super.shouldChangeState() && isActionFinished();
	}
	
	public boolean isFinishedCharging()
	{
		return currentTime >= chargeTime;
	}
	
	public boolean isActionFinished()
	{
		return isFinishedCharging();
	}
	
	protected CombatState getNextState()
	{
		if(owner.getCurrentHealth() <= 0.f)
		{
			return new CombatState("Dead");
		}
		if(isActionFinished())
			return new CombatState("Idle");
		return this;
	}
}
