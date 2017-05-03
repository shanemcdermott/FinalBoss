package buffs;
import java.io.Serializable;

public class BuffFile implements Serializable {
	
	protected BuffType buffType;
	protected String name;
	
	protected int baseValue;
	protected float magicMod;
	protected float speedMod;
	protected float strengthMod;
	protected float levelMod;
	
	protected float duration;
	protected float interval;
	
	protected Buff extractBuff ( Buffable buffable ) {
		Buff buff = null;
            
            int value = (int) ( baseValue + 
            		buffable.getMagic() * magicMod + 
            		buffable.getSpeed() * speedMod + 
            		buffable.getStrength() * strengthMod + 
            		buffable.getLevel() * levelMod 
            );
            
            buff = new Buff( name, buffType, value, duration, interval );
		
		return buff;
	}
	
	public String toString () {
		return String.format( "Name: %s\nType: %s\nBase Value: %d\nStrength Modifier: %.3f\n"
				+ "Speed Modifier: %.3f\nMagic Modifier: %.3f\nLevel Modifier: %.3f\nDuration: %.3f\n"
				+ "Interval: %.3f\n", name, buffType, baseValue, strengthMod, speedMod, magicMod,
				levelMod, duration, interval);
	}
	
}
