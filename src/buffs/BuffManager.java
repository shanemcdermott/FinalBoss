package buffs;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class BuffManager {

	final private int MAX_BUFFS = 50;
	final private String LOCATION = "res/assets/buffs/";
	private BuffFile[] buffFiles;
	
	public BuffManager () {
		buffFiles = new BuffFile[MAX_BUFFS];
		loadFiles();
	}
	
	public Buff getBuff ( int id, Buffable buffable ) {
		if ( id < 1 || id > MAX_BUFFS)
			return null;
		
		return buffFiles[id - 1].extractBuff( buffable );
	}
	
    public void loadFiles () {
    	for( int i = 0; i < MAX_BUFFS; i++ ) {
    		String fileLocation = String.format( LOCATION + "Buff%02d.dat", i + 1);
    		
	        try (ObjectInputStream ois = new ObjectInputStream( new FileInputStream( fileLocation ) ) ) {
	            BuffFile buffFile = (BuffFile) ois.readObject();
	        	buffFiles[i] = buffFile;
	        } catch ( EOFException EOFE ) {
	        	buffFiles[i] = null;
	        	continue;
	        } catch (Exception E) {
	        	System.err.println(E);
	        	System.exit(1);
	        }
    	}
    }
	
}
