package javagames.combat;

import javagames.game.GameObject;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class MeleeAction extends CombatAction {

	public MeleeAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime, Element effectTemplate) 
	{
		super(name, effect, range, chargeTime, cooldownTime, effectTemplate);
		// TODO Auto-generated constructor stub
	}


}
