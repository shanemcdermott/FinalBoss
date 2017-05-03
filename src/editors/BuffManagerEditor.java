package editors;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javagames.combat.buffs.*;

public class BuffManagerEditor
{
	
	private static final int MAX_BUFFS = 50;
	private static final String FILE_LOCATION = "res/assets/buffs/";
	private static final String ICON_LOCATION = "res/assets/icons/";
	private int size;
	private BuffFile[] buffFiles;
	
	public BuffManagerEditor () {
		size = 0;
		buffFiles = new BuffFile[MAX_BUFFS];
		
		createFiles();
		loadFiles();
	}
	
	public BuffFile getBuff ( int id ) {
		if ( id < 1 || id > MAX_BUFFS)
			return null;
		
		return buffFiles[id - 1];
	}
	
    public void loadFiles () {
    	for( int i = 0; i < MAX_BUFFS; i++ ) {
    		String fileLocation = String.format( FILE_LOCATION + "Buff%02d.dat", i + 1);
    		
	        try (ObjectInputStream ois = new ObjectInputStream( new FileInputStream( fileLocation ) ) ) {
	            BuffFile buffFile = (BuffFile) ois.readObject();
	        	buffFiles[i] = buffFile;
	        	size++;
	        } catch ( EOFException EOFE ) {
	        	buffFiles[i] = null;
	        	continue;
	        } catch (Exception E) {
	        	System.err.println(E);
	        	System.exit(1);
	        }
    	}
    }
    
    public void writeFiles () {
    	for ( int i = 0; i < MAX_BUFFS; i++ ) {
    		String fileLocation = String.format( FILE_LOCATION + "Buff%02d.dat", i + 1 );
    		
		    try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( fileLocation ) ) ) {
			    if ( buffFiles[i] != null )
			    	oos.writeObject( buffFiles[i] );
			    
			    oos.close();
		    } catch ( Exception E ) {
		    	System.err.println(E);
		    }
    	}
    }
    
    public void createFiles () {
    	try {
    		Path buffDirectory = Paths.get( FILE_LOCATION );
    		Files.createDirectories(buffDirectory);
    	} catch (Exception E) {
    		System.err.println(E);
    	}
    	
    	
    	for ( int i = 0; i < MAX_BUFFS; i++ ) {
    		String fileLocation = String.format( FILE_LOCATION + "Buff%02d.dat", i + 1 );
    		Path filePath = Paths.get( fileLocation );
    		
    		if ( !Files.exists( filePath, LinkOption.NOFOLLOW_LINKS ) ) {
    			try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( fileLocation ) ) ) {
    		    	oos.close();
    	    	} catch ( Exception E ) {
    	    		System.err.println(E);
    	    	}
    		}
    	}
    }
    
    public void printBuffs () {
    	for ( int i = 0; i < MAX_BUFFS; i++ ) {
    		if ( buffFiles[i] != null ) {
    			System.out.print( (i + 1) + ".\n" + buffFiles[i] + "\n" );
    		}
    	}
    }
    
    public void printBuff ( int id ) {
    	if ( id < 1 || id > MAX_BUFFS || buffFiles[id-1] == null )
    		return;
    	
    	System.out.print( id + ".\n" + buffFiles[id-1] );
    }
    
    public boolean addBuff ( BuffFile buffFile ) {
    	if ( size == MAX_BUFFS ) {
    		System.err.println( "No space available!" );
    		return false;
    	}
    	
    	for ( int i = 0; i < buffFiles.length; i++ ) {
    		if ( buffFiles[i] == null ) {
    			buffFiles[i] = buffFile;
    			size++;
    			break;
    		}
    	}
    	
    	return true;
    }
    
    public boolean removeBuff ( int id ) {
    	if ( id < 1 || id > MAX_BUFFS )
    		return false;
  
    	if ( buffFiles[ id - 1 ] == null )
    		return false;
    	
    	buffFiles[ id - 1 ] = null;
    	size--;
    	return true;
    }
    
    public int size () {
    	return size;
    }
    
    public boolean openSpace () {
    	return size < MAX_BUFFS;
    }

}
