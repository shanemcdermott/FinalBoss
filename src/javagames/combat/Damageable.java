package javagames.combat;

public interface Damageable 
{
	
	public void resetHealth();
	public float getCurrentHealth();
	public float getMaxHealth();
	
	public boolean canTakeDamage();
	public void takeDamage(Object source, float amount);
	public boolean isDead();
	public void die(Object source);
}
