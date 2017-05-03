package javagames.combat.buffs;

import java.io.Serializable;

import javagames.combat.Pawn;
import javagames.g2d.Sprite;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;

public class BuffFile implements Serializable {
	
	protected BuffType buffType;
	protected String name;
	protected String iconFileName;
	
	protected int baseValue;
	protected float magicMod;
	protected float speedMod;
	protected float strengthMod;
	protected float levelMod;
	protected float maxHealthMod;
	protected float healthMod;
	protected float maxManaMod;
	protected float manaMod;
	
	protected float duration;
	protected float interval;
	
	protected Buff extractBuff ( Pawn pawn ) {
		Buff buff = null;
            
            int value = (int) ( baseValue + 
            		pawn.getMagic() * magicMod + 
            		pawn.getSpeed() * speedMod + 
            		pawn.getStrength() * strengthMod + 
            		pawn.getLevel() * levelMod +
            		pawn.getMaxHealth() * maxHealthMod +
            		pawn.getCurrentHealth() * healthMod +
            		pawn.getMaxMana() * maxManaMod +
            		pawn.getCurrentMana() * manaMod
            );
            
            buff = new Buff( name, buffType, value, duration, interval );
            
            try {
            	buff.setIcon( new Sprite(ResourceLoader.loadImage(this.getClass(), "icons/" + iconFileName), new Vector2f(-0.1f, 0.1f), new Vector2f(0.1f, -0.1f)) );
            } catch ( Exception E ) {
            	E.printStackTrace();
            }
		
		return buff;
	}
	
	public String toString () {
		return String.format( "Name: %s\nType: %s\nBase Value: %d\nMax Health Modifier: %.3f\nHealth Modifier: %.3f\n"
				+ "Max Mana Modifier: %.3f\nMana Modifier: %.3f\nStrength Modifier: %.3f\nSpeed Modifier: %.3f\n"
				+ "Magic Modifier: %.3f\nLevel Modifier: %.3f\nDuration: %.3f\nInterval: %.3f\nIcon File Name: %s\n", name, buffType, 
				baseValue, maxHealthMod, healthMod, maxManaMod, manaMod, strengthMod, speedMod, magicMod, levelMod, 
				duration, interval, iconFileName);
	}

	public BuffType getBuffType() {
		return buffType;
	}

	public void setBuffType(BuffType buffType) {
		this.buffType = buffType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
	}

	public float getMagicMod() {
		return magicMod;
	}

	public void setMagicMod(float magicMod) {
		this.magicMod = magicMod;
	}

	public float getSpeedMod() {
		return speedMod;
	}

	public void setSpeedMod(float speedMod) {
		this.speedMod = speedMod;
	}

	public float getStrengthMod() {
		return strengthMod;
	}

	public void setStrengthMod(float strengthMod) {
		this.strengthMod = strengthMod;
	}

	public float getLevelMod() {
		return levelMod;
	}

	public void setLevelMod(float levelMod) {
		this.levelMod = levelMod;
	}

	public float getMaxHealthMod() {
		return maxHealthMod;
	}

	public void setMaxHealthMod(float maxHealthMod) {
		this.maxHealthMod = maxHealthMod;
	}

	public float getHealthMod() {
		return healthMod;
	}

	public void setHealthMod(float healthMod) {
		this.healthMod = healthMod;
	}

	public float getMaxManaMod() {
		return maxManaMod;
	}

	public void setMaxManaMod(float maxManaMod) {
		this.maxManaMod = maxManaMod;
	}

	public float getManaMod() {
		return manaMod;
	}

	public void setManaMod(float manaMod) {
		this.manaMod = manaMod;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getInterval() {
		return interval;
	}

	public void setInterval(float interval) {
		this.interval = interval;
	}
	
	public String getIconFileName () {
		return iconFileName;
	}
	
	public void setIconFileName ( String name ) {
		iconFileName = name;
	}
	
}
