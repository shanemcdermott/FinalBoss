package javagames.combat;

import java.util.ArrayList;

public class CombatArchetype 
{
	public float getHealthBaseBonus() {
		return healthBaseBonus;
	}



	public void setHealthBaseBonus(float healthBaseBonus) {
		this.healthBaseBonus = healthBaseBonus;
	}



	public float getHealthScale() {
		return healthScale;
	}



	public void setHealthScale(float healthScale) {
		this.healthScale = healthScale;
	}



	public float getHealthBonus() {
		return healthBonus;
	}



	public void setHealthBonus(float healthBonus) {
		this.healthBonus = healthBonus;
	}



	public float getManaBaseBonus() {
		return manaBaseBonus;
	}



	public void setManaBaseBonus(float manaBaseBonus) {
		this.manaBaseBonus = manaBaseBonus;
	}



	public float getManaScale() {
		return manaScale;
	}



	public void setManaScale(float manaScale) {
		this.manaScale = manaScale;
	}



	public float getManaBonus() {
		return manaBonus;
	}



	public void setManaBonus(float manaBonus) {
		this.manaBonus = manaBonus;
	}



	public float getStrength() {
		return strength;
	}



	public void setStrength(float strength) {
		this.strength = strength;
	}



	public float getSpeed() {
		return speed;
	}



	public void setSpeed(float speed) {
		this.speed = speed;
	}



	public float getMagic() {
		return magic;
	}



	public void setMagic(float magic) {
		this.magic = magic;
	}



	public ArrayList<String> getActions() {
		return actions;
	}



	public void setActions(ArrayList<String> actions) {
		this.actions = actions;
	}


	public float getMaxHealth(float healthBase)
	{
		return (healthBase +healthBaseBonus) * healthScale + healthBonus;
	}

	public void setHealthValues(float baseBonus, float scale, float bonus)
	{
		healthBaseBonus = baseBonus;
		healthScale = scale;
		healthBonus = bonus;
	}
	
	public void setManaValues(float baseBonus, float scale, float bonus)
	{
		manaBaseBonus = baseBonus;
		manaScale = scale;
		manaBonus = bonus;
	}
	
	
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
		actions.add("Idle");
	}
	
	
	

	
}
