package javagames.combat.buffs;

import java.awt.Graphics2D;

import javagames.combat.Pawn;
import javagames.g2d.Sprite;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;

public class Buff {
	private BuffType buffType;
	private String name;
	private int value;
	private float duration;
	private float timeElapsed;
	private float interval;
	private float intervalTimeElapsed;
	private boolean takeEffect;
	private Sprite icon;

	public Buff( String name, BuffType buffType, int value, float duration, float interval) {
		this.name = name;
		this.buffType = buffType;
		this.value = value;
		this.duration = duration;
		this.interval = interval;

		if (interval == 0)
			takeEffect = true;
		else
			takeEffect = false;

		timeElapsed = 0f;
		intervalTimeElapsed = 0f;
	}
	
	public void render (Graphics2D g, Matrix3x3f view, Vector2f position, float angle) {
		if ( icon != null )
			icon.render(g, view, position, angle);
	}
	
	public void setIcon ( Sprite icon ) {
		this.icon = icon;
	}
	
	public float getTimeRemaining () {
		return duration - timeElapsed;
	}

	private int getTicks() {
		if (interval != 0.0)
			return (int) (duration / interval);
		else
			return 1;
	}

	public boolean expired() {
		return timeElapsed >= duration;
	}

	public void update( float deltaTime ) {
		timeElapsed += deltaTime;
		intervalTimeElapsed += deltaTime;

		if (interval != 0.0) {
			if (intervalTimeElapsed >= interval) {
				takeEffect = true;
				intervalTimeElapsed -= interval;
			}
		}
	}

	/*
	 * All this does is properly add or subtract the right stats.
	 * The code here makes sure that no stat falls into the negatives.
	 * Everything except maxHealth can fall to 0 ( maxHealth goes to 1 ).
	 * Any time a value goes over the limit, it's kept track of and properly
	 * dealt with to ensure no stat get permanently buffed.
	 */
	public void processBuff( Pawn pawn ) {
		if ( !takeEffect )
			return;
		
		switch (buffType) {
		case ADD_MAX_HEALTH:
			adjustMaxHealth( pawn, true, false );
			break;

		case ADD_MAX_MANA:
			adjustMaxMana( pawn, true, false );
			break;

		case ADD_HEALTH:
			adjustHealth( pawn, true );
			break;

		case ADD_MANA:
			adjustMana( pawn, true );
			break;

		case ADD_STRENGTH:
			adjustStrength( pawn, true, false );
			break;

		case ADD_SPEED:
			adjustSpeed( pawn, true, false );
			break;

		case ADD_MAGIC:
			adjustMagic( pawn, true, false );
			break;

		case SUB_MAX_HEALTH:
			adjustMaxHealth( pawn, false, false );
			break;

		case SUB_MAX_MANA:
			adjustMaxMana( pawn, false, false );
			break;

		case SUB_HEALTH:
			adjustHealth( pawn, false );
			break;

		case SUB_MANA:
			adjustMana( pawn, false );
			break;

		case SUB_STRENGTH:
			adjustStrength( pawn, false, false );
			break;

		case SUB_SPEED:
			adjustSpeed( pawn, false, false );
			break;

		case SUB_MAGIC:
			adjustMagic( pawn, false, false );
			break;

		}

		takeEffect = false;
	}
	
	/*
	 * Almost the same as processBuff. Only difference is that it removes the
	 * effect it had on the player's stats, so if they lost 10 strength from a
	 * debuff, they get it back when it expires.
	 * DOES NOT GIVE HEALTH AND MANA BACK
	 */
	public void removeBuff ( Pawn pawn ) {
		switch (buffType) {
		case ADD_MAX_HEALTH:
			adjustMaxHealth( pawn, false, true );
			break;

		case ADD_MAX_MANA:
			adjustMaxMana( pawn, false, true );
			break;

		case ADD_STRENGTH:
			adjustStrength( pawn, false, true );
			break;

		case ADD_SPEED:
			adjustSpeed( pawn, false, true );
			break;

		case ADD_MAGIC:
			adjustMagic( pawn, false, true );
			break;

		case SUB_MAX_HEALTH:
			adjustMaxHealth( pawn, true, true );
			break;

		case SUB_MAX_MANA:
			adjustMaxMana( pawn, true, true );
			break;

		case SUB_STRENGTH:
			adjustStrength( pawn, true, true );
			break;

		case SUB_SPEED:
			adjustSpeed( pawn, true, true );
			break;

		case SUB_MAGIC:
			adjustMagic( pawn, true, true );
			break;
			
		default:
			break;

		}
	}
	
	private void adjustMaxHealth ( Pawn pawn, boolean add, boolean remove ) {
		int excessMaxHealth = pawn.getExcessMaxHealth();
		int maxHealth = pawn.getMaxHealth();
		int health = pawn.getCurrentHealth();
		
		int value = this.value;
		if ( remove )
			value *= getTicks();
		
		if ( add ) {
			if (excessMaxHealth < 0) {
				excessMaxHealth += value;
				
				if (excessMaxHealth > 0) {
					maxHealth = excessMaxHealth + 1;
					if ( buffType == BuffType.ADD_MAX_HEALTH )
						health = excessMaxHealth + 1;
					excessMaxHealth = 0;
				}
				
			} else {
				maxHealth = maxHealth + value;
				if ( buffType == BuffType.ADD_MAX_HEALTH )
					health = health + value;
			}
		}
		
		else {
			maxHealth -= value;

			if (maxHealth < 1) {
				excessMaxHealth += maxHealth - 1;
				maxHealth = 1;
			}

			health = health > maxHealth ? maxHealth : health;
		}
		
		pawn.setExcessMaxHealth( excessMaxHealth );
		pawn.setMaxHealth( maxHealth );
		pawn.setCurrentHealth( health );
	}
	
	private void adjustHealth ( Pawn pawn, boolean add ) {
		int maxHealth = pawn.getMaxHealth();
		int health = pawn.getCurrentHealth();
		
		if ( add ) {
			health += value;
			health = health > maxHealth ? maxHealth : health;
		}
		else {
			health -= value;
		}
		
		pawn.setCurrentHealth( health );
	}
	
	private void adjustMaxMana ( Pawn pawn, boolean add, boolean remove ) {
		int excessMaxMana = pawn.getExcessMaxMana();
		int maxMana = pawn.getMaxMana();
		int mana = pawn.getCurrentMana();
		
		if ( add ) {
			if (excessMaxMana < 0) {
				excessMaxMana += value;
				
				if (excessMaxMana > 0) {
					maxMana = excessMaxMana + 1;
					if ( buffType == BuffType.ADD_MAX_MANA )
						mana = excessMaxMana + 1;
					excessMaxMana = 0;
				}
				
			} else {
				maxMana = maxMana + value;
				if ( buffType == BuffType.ADD_MAX_MANA )
					mana = mana + value;
			}
		}
		
		else {
			maxMana -= value;

			if (maxMana < 1) {
				excessMaxMana += maxMana - 1;
				maxMana = 1;
			}

			mana = mana > maxMana ? maxMana : mana;
		}
		
		pawn.setExcessMaxMana( excessMaxMana );
		pawn.setMaxMana( maxMana );
		pawn.setMana( mana );
	}
	
	private void adjustMana ( Pawn pawn, boolean add ) {
		int maxMana = pawn.getMaxMana();
		int mana = pawn.getCurrentMana();
		
		if ( add ) {
			mana += value;
			mana = mana > maxMana ? maxMana : mana;
		}
		else {
			mana -= value;
		}
		
		pawn.setMana( mana );
	}
	
	private void adjustStrength ( Pawn pawn, boolean add, boolean remove ) {
		int excessStrength = pawn.getExcessStrength();
		int strength = pawn.getStrength();
		
		if ( add ) {
			if (excessStrength < 0) {
				excessStrength += value;
				if (excessStrength > 0) {
					strength = excessStrength;
					excessStrength = 0;
				}
			} else {
				strength += value;
			}
		}
		
		else {
			strength -= value;

			if (strength < 0) {
				excessStrength += strength;
				strength = 0;
			}
		}
		
		pawn.setExcessStrength( excessStrength );
		pawn.setStrength( strength );
	}
	
	private void adjustSpeed ( Pawn pawn, boolean add, boolean remove ) {
		int excessSpeed = pawn.getExcessSpeed();
		int speed = pawn.getSpeed();
		
		if ( add ) {
			if (excessSpeed < 0) {
				excessSpeed += value;
				if (excessSpeed > 0) {
					speed = excessSpeed;
					excessSpeed = 0;
				}
			} else {
				speed += value;
			}
		}
		
		else {
			speed -= value;

			if (speed < 0) {
				excessSpeed += speed;
				speed = 0;
			}
		}
		
		pawn.setExcessSpeed( excessSpeed );
		pawn.setSpeed( speed );
	}
	
	private void adjustMagic ( Pawn pawn, boolean add, boolean remove ) {
		int excessMagic = pawn.getExcessMagic();
		int Magic = pawn.getMagic();
		
		if ( add ) {
			if (excessMagic < 0) {
				excessMagic += value;
				if (excessMagic > 0) {
					Magic = excessMagic;
					excessMagic = 0;
				}
			} else {
				Magic += value;
			}
		}
		
		else {
			Magic -= value;

			if (Magic < 0) {
				excessMagic += Magic;
				Magic = 0;
			}
		}
		
		pawn.setExcessMagic( excessMagic );
		pawn.setMagic( Magic );
	}

	public void reset() {
		timeElapsed = 0;
		takeEffect = false;
	}

	@Override
	public String toString() {
		if (interval == 0) {
			return String.format("%s: %s by %d for %.3f second(s)\n", name,
					buffType.name(), value, duration);
		}

		if (duration == 0) {
			return String
					.format("%s: %s by %d\n", name, buffType.name(), value);
		}

		return String.format(
				"%s: %s by %d every %.3f second(s) for %.3f second(s)\n", name,
				buffType.name(), value, interval, duration);
	}
	
	public String getName () {
		return name;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Buff))
			return false;

		Buff o = (Buff) other;

		if (name.equals(o.name))
			return true;

		return false;
	}
}
