package javagames.combat;

import javagames.g2d.SpriteSheet;
import javagames.util.geom.BoundingShape;

public class Enemy extends Pawn {

	private Avatar avatar;
	private int expGiven;
	
	public Enemy(String name, SpriteSheet sprite)
	{
		super(name,sprite);
	}
	
	public Enemy(String name, SpriteSheet sprite, BoundingShape bounds)
	{
		super(name,sprite,bounds);
	}
	
	@Override
	public void initialize () {
		super.initialize();
		maxMana = 0;
		currentMana = 0;
	}
	
	public void setAvatar ( Avatar avatar ) {
		this.avatar = avatar;
	}
	
	public void setExpGiven ( int expGiven ) {
		this.expGiven = expGiven;
	}
	
	public void processInput ( float deltaTime ) {
		moveAI( deltaTime );
	}
	
	private void moveAI ( float deltaTime ) {
		
	}
	
	public int getExpGiven () {
		return expGiven;
	}
	
	@Override
	public void setMaxMana (int value) {
		
	}
	
	@Override
	public void setCurrentMana (int value) {
		
	}
	
	@Override
	public void setExcessMaxMana (int value) {
		
	}

}
