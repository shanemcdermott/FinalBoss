package javagames.combat;

public class CombatArchetype 
{
	protected float healthScale;
	protected float healthBonus;
	
	public float getMaxHealth(float healthBase)
	{
		return healthBase * healthScale + healthBonus;
	}
}
