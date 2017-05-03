package javagames.combat;

import java.util.ArrayList;

public class CombatArchetype 
{
	protected float healthBaseBonus;
	protected float healthScale;
	protected float healthBonus;
	
	protected float manaBaseBonus;
	protected float manaScale;
	protected float manaBonus;
	
	protected float strength;
	protected float speed;
	protected float magic;
	
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
