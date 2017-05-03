package javagames.combat.buffs;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javagames.combat.Pawn;

public class BuffManager {

	private static final int MAX_BUFFS = 50;
	private static final String FILE_LOCATION = "res/assets/buffs/";
	private static final String ICON_LOCATION = "res/assets/icons/";
	private static javagames.combat.buffs.BuffFile[] buffFiles;
	
	public static void initialize () {
		buffFiles = new javagames.combat.buffs.BuffFile[MAX_BUFFS];
		loadFiles();
	}
	
	public static Buff getBuff ( int id, Pawn pawn ) {
		if ( id < 1 || id > MAX_BUFFS)
			return null;
		
		return buffFiles[id - 1].extractBuff( pawn );
	}
	
    public static void loadFiles () {
    	for( int i = 0; i < MAX_BUFFS; i++ ) {
    		String fileLocation = String.format( FILE_LOCATION + "Buff%02d.dat", i + 1);
    		
	        try (ObjectInputStream ois = new ObjectInputStream( new FileInputStream( fileLocation ) ) ) {
	        	javagames.combat.buffs.BuffFile buffFile = (javagames.combat.buffs.BuffFile) ois.readObject();
	        	buffFiles[i] = buffFile;
	        } catch ( EOFException EOFE ) {
	        	buffFiles[i] = null;
	        	continue;
	        } catch (Exception E) {
	        	System.err.println(E);
	        	E.printStackTrace();
	        	System.exit(1);
	        }
    	}
    }
	
}
