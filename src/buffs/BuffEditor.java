package buffs;
import java.util.Scanner;

public class BuffEditor implements Runnable {

	private BuffManagerEditor editor;
	private boolean running;
	private Scanner input;
	
	public BuffEditor () {
		initialize();
		run();
	}
	
	private void initialize () {
		editor = new BuffManagerEditor();
		running = true;
		input = new Scanner(System.in);
	}
	
	public void run () {
		while ( running ) {
			startingMessage();
			doAction();
		}
	}
	
	private void startingMessage () {
		System.out.println( "Here are the current buffs:\n" );
		editor.printBuffs();
		System.out.println();
	}
	
	private void doAction () {
		boolean valid = true;
		
		do {
			valid = true;
			
			System.out.println( "What would you like to do?\n"
					+ "\t(C)reate a buff\n"
					+ "\t(E)dit a buff\n"
					+ "\t(R)emove a buff\n"
					+ "\t(S)ave changes\n"
					+ "\tE(x)it (Will not save changes)\n"
					+ "--> ");
			
			char action = new Scanner( System.in ).next().charAt(0);
			System.out.println();
			
			switch ( action ) {
			case 'C': case 'c':
				createBuff();
				break;
				
			case 'E': case 'e':
				if ( editor.size() > 0 )
					editBuff();
				else
					System.out.println( "There are no buffs to edit!\n" );
				break;
				
			case 'R': case 'r':
				removeBuff();
				break;
				
			case 'S': case 's':
				saveChanges();
				break;
				
			case 'X': case 'x':
				running = false;
				break;
				
			default:
				System.out.println( "That's not an action." );
				valid = false;
			}
			
		} while ( !valid );
	}
	
	private void createBuff () {
		if ( !editor.openSpace() ) {
			System.out.println("There is no space to create a buff. Either increase the max or remove a buff.");
			return;
		}
		
		BuffFile buffFile = new BuffFile();
		
		buffFile.name = promptName();
		buffFile.buffType = promptBuffType();
		buffFile.baseValue = promptBaseValue();
		buffFile.strengthMod = promptStrengthMod();
		buffFile.speedMod = promptSpeedMod();
		buffFile.magicMod = promptMagicMod();
		buffFile.levelMod = promptLevelMod();
		buffFile.duration = promptDuration();
		buffFile.interval = promptInterval();
		
		if ( editor.addBuff(buffFile) )
			System.out.println("Buff added successfully\n");
		else 
			System.err.println("Could not add the buff!");
	}
	
	private void removeBuff () {
		int buffId = promptBuffId();
		boolean success = editor.removeBuff(buffId);
		if ( success )
			System.out.printf("Buff %d removed successfully.\n\n", buffId);
		else
			System.out.printf("Buff %d could not be removed.\n\n", buffId);
	}
	
	private void editBuff () {
		BuffFile buffFile;
		int field;
		int buffId;
		
		do {
			buffId = promptBuffId();
			buffFile = editor.getBuff( buffId );
		} while ( buffFile == null );
		
		
		do {
			editor.printBuff(buffId);
			System.out.println();
			
			System.out.print("What field would you like to change?"
					+ "\n\t1) Name"
					+ "\n\t2) Buff Type"
					+ "\n\t3) Base Value"
					+ "\n\t4) Strength Modifier"
					+ "\n\t5) Speed Modifier"
					+ "\n\t6) Magic Modifier"
					+ "\n\t7) Level Modifier"
					+ "\n\t8) Duration"
					+ "\n\t9) Interval"
					+ "\n\t0) Exit"
					+ "\n--> ");
			field = input.nextInt();
			System.out.println();
			
			switch (field) {
			case 0:
				break;
			
			case 1:
				buffFile.name = promptName();
				break;
				
			case 2:
				buffFile.buffType = promptBuffType();
				break;
				
			case 3:
				buffFile.baseValue = promptBaseValue();
				break;
				
			case 4:
				buffFile.strengthMod = promptStrengthMod();
				break;
				
			case 5:
				buffFile.speedMod = promptSpeedMod();
				break;
				
			case 6:
				buffFile.magicMod = promptMagicMod();
				break;
				
			case 7:
				buffFile.levelMod = promptLevelMod();
				break;
				
			case 8:
				buffFile.duration = promptDuration();
				break;
				
			case 9: 
				buffFile.interval = promptInterval();
				break;
				
			default:
				System.out.println("Not a valid option.");
			}
			
		} while ( field != 0 );
	}
	
	private void saveChanges () {
		editor.writeFiles();
		System.out.println("Changed saved.\n");
	}
	
	private int promptBuffId () {
		System.out.println("Select the ID of the buff:\n--> ");
		int id = input.nextInt();
		System.out.println();
		return id;
	}
	
	private String promptName () {
		System.out.print( "Enter a name for the (de)buff:\n--> " );
		input.nextLine();
		String name = input.nextLine();
		System.out.println();
		return name;
	}
	
	private BuffType promptBuffType () {
		BuffType buffType = null;
		int response = 0;
		
		do {
			System.out.print( "Pick a buff type:"
					+ "\n\t1) Add Max Health"
					+ "\n\t2) Add Max Mana"
					+ "\n\t3) Add Health"
					+ "\n\t4) Add Mana"
					+ "\n\t5) Add Strength"
					+ "\n\t6) Add Speed"
					+ "\n\t7) Add Magic"
					+ "\n\t8) Sub Max Health"
					+ "\n\t9) Sub Max Mana"
					+ "\n\t10) Sub Health"
					+ "\n\t11) Sub Mana"
					+ "\n\t12) Sub Strength"
					+ "\n\t13) Sub Speed"
					+ "\n\t14) Sub Magic"
					+ "\n--> ");
			
			response = input.nextInt();
		} while ( response < 1 || response > BuffType.size() );
			
		switch (response)  {
		case 1:
			buffType = BuffType.ADD_MAX_HEALTH;
			break;
			
		case 2:
			buffType = BuffType.ADD_MAX_MANA;
			break;
			
		case 3:
			buffType = BuffType.ADD_HEALTH;
			break;
			
		case 4:
			buffType = BuffType.ADD_MANA;
			break;
			
		case 5:
			buffType = BuffType.ADD_STRENGTH;
			break;
			
		case 6:
			buffType = BuffType.ADD_SPEED;
			break;
			
		case 7:
			buffType = BuffType.ADD_MAGIC;
			break;
			
		case 8:
			buffType = BuffType.SUB_MAX_HEALTH;
			break;
			
		case 9:
			buffType = BuffType.SUB_MAX_MANA;
			break;
			
		case 10:
			buffType = BuffType.SUB_HEALTH;
			break;
			
		case 11:
			buffType = BuffType.SUB_MANA;
			break;
			
		case 12:
			buffType = BuffType.SUB_STRENGTH;
			break;
			
		case 13:
			buffType = BuffType.SUB_SPEED;
			break;
		
		case 14:
			buffType = BuffType.SUB_MAGIC;
			break;
		}
		
		System.out.println();
		return buffType;
	}
	
	private int promptBaseValue () {
		int baseValue;
		
		do {
			System.out.print( "Enter a positive base value:\n--> " );
			baseValue = input.nextInt();
		} while ( baseValue < 0 );
		
		System.out.println();
		return baseValue;
	}
	
	private float promptMagicMod () {
		System.out.print( "Enter a magic modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptStrengthMod () {
		System.out.print( "Enter a strength modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptSpeedMod () {
		System.out.print( "Enter a speed modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptLevelMod () {
		System.out.print( "Enter a level modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptDuration () {
		float duration;
		
		do {
			System.out.print( "Duration of (de)buff:\n--> " );
			duration = input.nextFloat();
		} while ( duration < 0 );
		
		System.out.println();
		return duration;
	}
	
	private float promptInterval () {
		float interval;
		
		do {
			System.out.print( "Interval time of (de)buff:\n--> " );
			interval = input.nextFloat();
		} while ( interval < 0 );
		
		System.out.println();
		return interval;
	}
}
