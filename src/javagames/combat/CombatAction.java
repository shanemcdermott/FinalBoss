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
	
	protected BoundingShape effectBounds;
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
		effectBounds = new BoundingBox(1,1);
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
			onFinishedCharging();
		}
		if(effect != null)
		{
			effect.update(deltaTime);
		}
		if(shouldChangeState())
		{
			owner.setState(getNextState());
		}
	}
	
	public void onFinishedCharging()
	{
		bIsCharging=false;
		if(effect == null)
		{
			effect = new DamageObject(getName(), effectBounds, owner);
		}
		
		effect.reset();
		currentTime -=chargeTime;
	}
	
	public boolean isFinishedCharging()
	{
		return currentTime >= chargeTime;
	}
	
	public boolean isCharging()
	{
		return bIsCharging;
	}
	
	public boolean canEnter()
	{
		return currentTime >= cooldownTime;
	}
		
	public boolean shouldChangeState()
	{
		return super.shouldChangeState() && isActionFinished();
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
