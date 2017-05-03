package javagames.combat;

import javagames.g2d.SpriteSheet;
import javagames.util.geom.BoundingShape;

public class Enemy extends Pawn {

	float time;
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
	protected void initialize () {
		super.initialize();
		maxMana = 0;
		currentMana = 0;
		time = 0.f;
		setState("Idle");
	}
	
	@Override
	public void setJob(CombatArchetype job)
	{
		this.job = job;
	}
	
	public int getExperienceReward()
	{
		return expGiven;
	}
	
	public void setAvatar ( Avatar avatar ) {
		this.avatar = avatar;
	}
	
	public void setExpGiven ( int expGiven ) {
		this.expGiven = expGiven;
	}
	
	public void processInput ( float deltaTime ) 
	{
		time += deltaTime;
		moveAI( deltaTime );
	}
	
	private void moveAI ( float deltaTime ) 
	{
		if(time >= 3.f)
		{
			String dir = "MoveUp";
			if(Math.random()> 0.7)
				dir = "MoveUp";
			else
				dir = "MoveDown";
			
			System.out.println("Moving " + dir);
			startAction(dir);
			time = 0.f;
		}
	}
	
	public int getExpGiven () {
		return expGiven;
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(time >= 3.f)
		{
			String dir = "MoveUp";
			if(Math.random()> 0.7)
				dir = "MoveUp";
			else
				dir = "MoveDown";
			
			System.out.println("Moving " + dir);
			startAction(dir);
			time = 0.f;
		}
		super.update(deltaTime);
	}
	
	@Override
	public void setMaxMana (int value) {
		
	}
	

	public void setCurrentMana (int value) 
	{
		currentMana = value;	
	}
	
	@Override
	public void setExcessMaxMana (int value) {
		
	}

}
