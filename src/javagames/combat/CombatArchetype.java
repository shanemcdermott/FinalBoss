package javagames.combat;

public class CombatArchetype 
{
	protected float healthBaseBonus;
	protected float healthScale;
	protected float healthBonus;
	
	
	public CombatArchetype()
	{
		healthScale = 1.f;
		healthBonus = 0.f;
	}
	
	public float getMaxHealth(float healthBase)
	{
		return (healthBase +healthBaseBonus) * healthScale + healthBonus;
	}
}
