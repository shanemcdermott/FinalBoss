package javagames.combat;

import javagames.game.GameObject;

public class RangedAction extends CombatAction
{
	public RangedAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime) 
	{
		super(name, effect, range, chargeTime, cooldownTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishedCharging() {
		// TODO Auto-generated method stub
		
	}
}
