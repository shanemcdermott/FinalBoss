package javagames.combat;

import java.awt.Graphics2D;

import javagames.game.GameObject;
import javagames.game.MultiStateObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
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
	
	public CombatAction(String name, GameObject effect)
	{
		this(name,effect, 1.f, 1.f, 0.f);
	}
	
	public CombatAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime)
	{
		super(name);
		this.range = range;
		currentTime = 0.f;
		this.chargeTime = chargeTime;
		this.cooldownTime = cooldownTime;
		bIsCharging=false;
		this.effect = effect;
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
		if(shouldChangeState())
		{
			owner.setState(getNextState());
		}
	}
	
	protected void onFinishedCharging()
	{
		bIsCharging=false;
		spawnEffect();
		currentTime -=chargeTime;
	}
	
	protected void spawnEffect()
	{	
		if(effect!=null)
			effect.reset();
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
		return super.shouldChangeState() || isActionFinished();
	}
	
	public boolean isActionFinished()
	{
		return !isCharging();
	}
	
	protected String getNextState()
	{
		if(owner.getCurrentHealth() <= 0.f)
		{
			return "Dead";
		}
		if(isActionFinished())
			return "Idle";
		
		return name;
	}
	
	@Override
	public GameObject getEffect()
	{
		return effect;
	}
	
	@Override
	public void setOwner(MultiStateObject owner)
	{
		super.setOwner(owner);
		
	}
}
