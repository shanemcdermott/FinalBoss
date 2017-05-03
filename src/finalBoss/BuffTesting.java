package finalBoss;

import java.awt.Graphics;

import javagames.combat.Pawn;
import javagames.combat.buffs.BuffManager;
import javagames.util.WindowFramework;

public class BuffTesting extends WindowFramework {

	Pawn pawn;
	
	public BuffTesting () {
		pawn = new Pawn("", null);
		BuffManager.initialize();
		addBuffs();
	}
	
	private void addBuffs () {
		pawn.addBuff( BuffManager.getBuff( 1, pawn ) );
		pawn.addBuff( BuffManager.getBuff( 2, pawn ) );
		pawn.addBuff( BuffManager.getBuff( 3, pawn ) );
	}
	
	protected void processInput(float delta) {
		super.processInput(delta);
	}

	protected void updateObjects(float delta) {
		pawn.update(delta);
	}

	protected void render(Graphics g) {
		
	}
	
	public static void main ( String[] args ) {
		launchApp( new BuffTesting() );
	}

}
