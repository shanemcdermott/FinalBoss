package javagames.combat;

import javagames.game.GameObject;

public class MeleeAction extends CombatAction {

	public MeleeAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime) 
	{
		super(name, effect, range, chargeTime, cooldownTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishedCharging() {
		// TODO Auto-generated method stub

	}

}
