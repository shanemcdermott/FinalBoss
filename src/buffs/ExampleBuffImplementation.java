package buffs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExampleBuffImplementation implements Buffable {

	// Everything an object needs to be buffable
	private int maxHealth;
	private int health;
	private int maxMana;
	private int mana;
	private int strength;
	private int speed;
	private int magic;
	private int level;
	
	// variables used to keep track of values going into the negatives.
	private int maxHealthOverage;
	private int maxManaOverage;
	private int strengthOverage;
	private int speedOverage;
	private int magicOverage;
	
	private List<Buff> buffs;
	
	public ExampleBuffImplementation ( int maxHealth, int maxMana, int strength, int speed, int magic, int level ) {
		this.maxHealth = health = maxHealth;
		this.maxMana = mana = maxMana;
		this.strength = strength;
		this.speed = speed;
		this.magic = magic;
		this.level = level;
		
		maxHealthOverage = 0;
		maxManaOverage = 0;
		strengthOverage = 0;
		speedOverage = 0;
		magicOverage = 0;
		
		buffs = new ArrayList<Buff>();
	}
	
	@Override
	public void addBuff(Buff buff) {
		// If this object already has a specific buff, just reset it
		for ( Buff b : buffs ) {
			if ( buff.equals( b ) ) {
				b.reset();
				return;
			}
		}
		
		// Otherwise add it
		buffs.add( buff );
	}
	
	@Override
	public void displayBuffs() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateBuffs (float deltaTime) {
		
		// Use an iterator to iterate over buffs. Required since we will be removing while iterating.
		Iterator<Buff> iter = buffs.iterator();
		
		while ( iter.hasNext() ) {
			Buff buff = iter.next();
			
			// Update the buff
			buff.update( deltaTime );
			
			// See if it expired. If it did, remove it and move to the next buff. Regain lost stats or lose bonus stats.
			if ( buff.expired() ) {
				removeBuff( buff );
				iter.remove();
				continue;
			}
			
			// If it's time for the buff to tick, process it
			if ( buff.takeEffect() ) {
				processBuff( buff );
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
	private void processBuff ( Buff buff ) {

        int value = buff.getValue();

        switch ( buff.getBuffType() ) {
            case ADD_MAX_HEALTH:
            	if ( maxHealthOverage < 0 ) {
            		maxHealthOverage += value;
            		if ( maxHealthOverage > 0 ) {
            			maxHealth = maxHealthOverage + 1;
            			health = maxHealthOverage + 1;
            			maxHealthOverage = 0;
            		}
            	}
            	else {
	                maxHealth += value;
	                health += value;
            	}
                break;

            case ADD_MAX_MANA:
            	if ( maxManaOverage < 0 ) {
            		maxManaOverage += value;
            		if ( maxManaOverage > 0 ) {
            			maxMana = maxManaOverage;
            			mana = maxManaOverage;
            			maxManaOverage = 0;
            		}
            	}
            	else {
	                maxMana += value;
	                mana += value;
            	}
                break;

            case ADD_HEALTH:
                health += value;
                health = health > maxHealth ? maxHealth : health;
                break;

            case ADD_MANA:
                mana += value;
                mana = mana > maxMana ? maxMana : mana;
                break;

            case ADD_STRENGTH:
            	if ( strengthOverage < 0 ) {
            		strengthOverage += value;
            		if ( strengthOverage > 0 ) {
            			strength = strengthOverage;
            			strengthOverage = 0;
            		}
            	}
            	else {
	                strength += value;
            	}
                break;

            case ADD_SPEED:
            	if ( speedOverage < 0 ) {
            		speedOverage += value;
            		if ( speedOverage > 0 ) {
            			speed = speedOverage;
            			speedOverage = 0;
            		}
            	}
            	else {
	                speed += value;
            	}
                break;
                
            case ADD_MAGIC:
            	if ( magicOverage < 0 ) {
            		magicOverage += value;
            		if ( magicOverage > 0 ) {
            			magic = magicOverage;
            			magicOverage = 0;
            		}
            	}
            	else {
	                magic += value;
            	}
                break;

            case SUB_MAX_HEALTH:
                maxHealth -= value;
                
                if ( maxHealth < 1 ) {
                	maxHealthOverage += maxHealth - 1;
                	maxHealth = 1;
                }
                
                health = health > maxHealth ? maxHealth : health;
                break;

            case SUB_MAX_MANA:
                maxMana -= value;
                
                if ( maxMana < 0 ) {
                	maxManaOverage += maxMana;
                	maxMana = 0;
                }
                
                mana = mana > maxMana ? maxMana : mana;
                break;

            case SUB_HEALTH:
                health -= value;
                break;

            case SUB_MANA:
                mana -= value;
                break;

            case SUB_STRENGTH:
                strength -= value;
                
                if ( strength < 0 ) {
                	strengthOverage += strength;
                	strength = 0;
                }
                
                break;
                
            case SUB_SPEED:
            	speed -= value;
            	
            	if ( speed < 0 ) {
            		speedOverage += speed;
            		speed = 0;
            	}
            	
            	break;

            case SUB_MAGIC:
                magic -= value;
                
                if ( magic < 0 ) {
                	magicOverage += magic;
                	magic = 0;
                }
                
                break;

        }

        buff.takenEffect();
	}
	
	/*
	 * Almost the same as processBuffs. Only difference is that it removes the
	 * effect it had on the player's stats, so if they lost 10 strength from a
	 * debuff, they get it back when it expires.
	 * DOES NOT GIVE HEALTH AND MANA BACK
	 */
	private void removeBuff ( Buff buff ) {

        int adjustValue = buff.getValue() * buff.getTicks();

        switch ( buff.getBuffType() ) {
            case ADD_MAX_HEALTH:
                maxHealth -= adjustValue;

                if ( maxHealth < 1 ) {
                	maxHealthOverage += maxHealth - 1;
                	maxHealth = 1;
                }
                
                health = health > maxHealth ? maxHealth : health;
                break;

            case ADD_MAX_MANA:
                maxMana -= adjustValue;
                
                if ( maxMana < 0 ) {
                	maxManaOverage += maxMana;
                	maxMana = 0;
                }
                
                mana = mana > maxMana ? maxMana : mana;
                break;

            case ADD_STRENGTH:
                strength -= adjustValue;
                
                if ( strength < 0 ) {
                	strengthOverage += strength;
                	strength = 0;
                }
                
                break;
                
            case ADD_SPEED:
            	speed -= adjustValue;
            	
            	if ( speed < 0 ) {
            		speedOverage += speed;
            		speed = 0;
            	}
            	
            	break;

            case ADD_MAGIC:
                magic -= adjustValue;
                
                if ( magic < 0 ) {
                	magicOverage += magic;
                	magic = 0;
                }
                
                break;

            case SUB_MAX_HEALTH:
            	if ( maxHealthOverage < 0 ) {
            		maxHealthOverage += adjustValue;
            		if ( maxHealthOverage > 0 ) {
            			maxHealth = maxHealthOverage + 1;
            			maxHealthOverage = 0;
            		}
            	}
            	else {
	                maxHealth += adjustValue;
            	}
                break;

            case SUB_MAX_MANA:
            	if ( maxManaOverage < 0 ) {
            		maxManaOverage += adjustValue;
            		if ( maxManaOverage > 0 ) {
            			maxMana = maxManaOverage;
            			maxManaOverage = 0;
            		}
            	}
            	else {
	                maxMana += adjustValue;
            	}
                break;

            case SUB_STRENGTH:
            	if ( strengthOverage < 0 ) {
            		strengthOverage += adjustValue;
            		if ( strengthOverage > 0 ) {
            			strength = strengthOverage;
            			strengthOverage = 0;
            		}
            	}
            	else {
	                strength += adjustValue;
            	}
                break;

            case SUB_SPEED:
            	if ( speedOverage < 0 ) {
            		speedOverage += adjustValue;
            		if ( speedOverage > 0 ) {
            			speed = speedOverage;
            			speedOverage = 0;
            		}
            	}
            	else {
	                speed += adjustValue;
            	}
                break;
                
            case SUB_MAGIC:
            	if ( magicOverage < 0 ) {
            		magicOverage += adjustValue;
            		if ( magicOverage > 0 ) {
            			magic = magicOverage;
            			magicOverage = 0;
            		}
            	}
            	else {
	                magic += adjustValue;
            	}
                break;

        }

    }

	@Override
	public int buffCount() {
		// TODO Auto-generated method stub
		return buffs.size();
	}

	public int getMaxHealth () {
		return maxHealth;
	}
	
	public int getHealth () {
		return health;
	}
	
	public int getMaxMana () {
		return maxMana;
	}
	
	public int getMana () {
		return mana;
	}
	
	@Override
	public int getStrength() {
		// TODO Auto-generated method stub
		return strength;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	@Override
	public int getMagic() {
		// TODO Auto-generated method stub
		return magic;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}
	
}
