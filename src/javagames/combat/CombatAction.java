package javagames.combat;

import java.awt.Graphics2D;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javagames.game.GameObject;
import javagames.game.MultiStateObject;
import javagames.game.Ownable;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
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
	protected Element	effectTemplate;
	
	public CombatAction(String name, GameObject effect, Element effectTemplate)
	{
		this(name,effect, 1.f, 1.f, 0.f, effectTemplate);
	}
	
	public CombatAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime, Element effectTemplate)
	{
		super(name);
		this.range = range;
		currentTime = 0.f;
		this.chargeTime = chargeTime;
		this.cooldownTime = cooldownTime;
		bIsCharging=false;
		this.effect = effect;
		this.effectTemplate = effectTemplate;
	
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
			if(shouldChangeState())
			{
				owner.setState(getNextState());
			}
		}
	}
	
	protected void onFinishedCharging()
	{
		bIsCharging=false;
		System.out.println(name + " Finished Charging!");
		spawnEffect();
		currentTime -=chargeTime;
	}
	
	protected void spawnEffect()
	{	
		if(effect!=null)
		{	
			if(effectTemplate !=null)
			{
				GameObject newEffect = XMLUtility.loadGameObject(this.getClass(), effectTemplate);
				if(newEffect !=null)
				{
					if(newEffect instanceof Ownable)
					{
						((Ownable)newEffect).setOwner(getOwner());
					}
					((MultiStateObject)getOwner()).addEffect(newEffect);
					newEffect.reset();
					GameConstants.GAME_STATE.addObject(newEffect);					
				}
				else
				{
					System.out.println("Null new effect!");
				}
			}
			else
			{
				System.out.println("Null template!");
			}
		}
		else
		{
			System.out.println("Null effect!");
		}
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
