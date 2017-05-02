package editors;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
	
	protected static Buff extractBuff ( String fileLocation ) {
		Buff buff = null;
		
		try (ObjectInputStream ois = new ObjectInputStream( new FileInputStream( fileLocation ) ) ) {
            BuffFile buffFile = (BuffFile) ois.readObject();
            
            int value = (int) ( buffFile.baseValue + 
            		buffFile.baseValue * buffFile.magicMod + 
            		buffFile.baseValue * buffFile.speedMod + 
            		buffFile.baseValue * buffFile.strengthMod + 
            		buffFile.baseValue * buffFile.levelMod 
            );
            
            buff = new Buff( buffFile.name, buffFile.buffType, buffFile.baseValue, buffFile.duration, buffFile.interval );
            
        } catch (Exception E) {
        	System.err.println(E);
        }
		
		return buff;
	}
	
	public String toString () {
		return String.format( "Name: %s\nType: %s\nBase Value: %d\nStrength Modifier: %.3f\n"
				+ "Speed Modifier: %.3f\nMagic Modifier: %.3f\nLevel Modifier: %.3f\nDuration: %.3f\n"
				+ "Interval: %.3f\n", name, buffType, baseValue, strengthMod, speedMod, magicMod,
				levelMod, duration, interval);
	}
	
}
