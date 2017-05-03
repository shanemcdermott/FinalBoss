package javagames.combat;

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
