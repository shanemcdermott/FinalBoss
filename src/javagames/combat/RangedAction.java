package javagames.combat;

import javagames.game.GameObject;
import javagames.game.Ownable;
import javagames.util.GameConstants;
import javagames.util.XMLUtility;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class RangedAction extends CombatAction
{
	public RangedAction(String name, GameObject effect, float range, float chargeTime, float cooldownTime, Element effectTemplate) 
	{
		super(name, effect, range, chargeTime, cooldownTime, effectTemplate);
		// TODO Auto-generated constructor stub
	}

	

}
