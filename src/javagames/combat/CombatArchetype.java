package javagames.combat;

import java.util.ArrayList;

public class CombatArchetype 
{
	protected float healthBaseBonus;
	protected float healthScale;
	protected float healthBonus;
	
	public ArrayList<String> actions;
	
	public CombatArchetype()
	{
		healthScale = 1.f;
		healthBonus = 0.f;
		actions = new ArrayList<String>();
	}
	
	public float getMaxHealth(float healthBase)
	{
		return (healthBase +healthBaseBonus) * healthScale + healthBonus;
	}
	
}
