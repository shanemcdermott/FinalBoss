package editors;

import java.util.Scanner;
import javagames.combat.buffs.*;

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
			
			System.out.print( "What would you like to do?\n"
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
		
		buffFile.setName(promptName());
		buffFile.setBuffType(promptBuffType());
		buffFile.setBaseValue(promptBaseValue());
		buffFile.setMaxHealthMod(promptMaxHealthMod());
		buffFile.setHealthMod(promptHealthMod());
		buffFile.setMaxManaMod(promptMaxManaMod());
		buffFile.setManaMod(promptManaMod());
		buffFile.setStrengthMod(promptStrengthMod());
		buffFile.setSpeedMod(promptSpeedMod());
		buffFile.setMagicMod(promptMagicMod());
		buffFile.setLevelMod(promptLevelMod());
		buffFile.setDuration(promptDuration());
		buffFile.setInterval(promptInterval());
		buffFile.setIconFileName(promptIconFileName());
		
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
					+ "\n\t4) Max Health Modifier"
					+ "\n\t5) Health Modifier"
					+ "\n\t6) Max Mana Modifier"
					+ "\n\t7) Mana Modifier"
					+ "\n\t8) Strength Modifier"
					+ "\n\t9) Speed Modifier"
					+ "\n\t10) Magic Modifier"
					+ "\n\t11) Level Modifier"
					+ "\n\t12) Duration"
					+ "\n\t13) Interval"
					+ "\n\t14) Icon File Name"
					+ "\n\t0) Exit"
					+ "\n--> ");
			field = input.nextInt();
			System.out.println();
			
			switch (field) {
			case 0:
				break;
			
			case 1:
				buffFile.setName(promptName());
				break;
				
			case 2:
				buffFile.setBuffType(promptBuffType());
				break;
				
			case 3:
				buffFile.setBaseValue(promptBaseValue());
				break;
				
			case 4:
				buffFile.setMaxHealthMod(promptMaxHealthMod());
				break;
				
			case 5:
				buffFile.setMaxHealthMod(promptHealthMod());
				break;
				
			case 6:
				buffFile.setMaxHealthMod(promptMaxManaMod());
				break;
				
			case 7:
				buffFile.setMaxHealthMod(promptManaMod());
				break;
				
			case 8:
				buffFile.setStrengthMod(promptStrengthMod());
				break;
				
			case 9:
				buffFile.setSpeedMod(promptSpeedMod());
				break;
				
			case 10:
				buffFile.setMagicMod(promptMagicMod());
				break;
				
			case 11:
				buffFile.setLevelMod(promptLevelMod());
				break;
				
			case 12:
				buffFile.setDuration(promptDuration());
				break;
				
			case 13: 
				buffFile.setInterval(promptInterval());
				break;
				
			case 14:
				buffFile.setIconFileName(promptIconFileName());
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
		if ( input.hasNextLine() ) {
			input.nextLine();
		}
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
	
	private float promptMaxHealthMod () {
		System.out.print( "Enter max health modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptHealthMod () {
		System.out.print( "Enter a health modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptMaxManaMod () {
		System.out.print( "Enter max mana modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
	}
	
	private float promptManaMod () {
		System.out.print( "Enter mana modifier:\n--> " );
		float mod = input.nextFloat();
		System.out.println();
		return mod;
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
	
	private String promptIconFileName () {
		System.out.print( "Enter a file name of the icon:\n--> " );
		if ( input.hasNextLine() ) {
			input.nextLine();
		}
		String name = input.nextLine();
		System.out.println();
		return name;
	}
}
