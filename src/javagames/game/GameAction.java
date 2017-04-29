package javagames.game;

public abstract class GameAction 
{
	public Pawn owner;
	public String animName;
	
	
	
	public boolean canStart()
	{
		return false;
	}
	
	public void start()
	{
		owner.startAnimation(animName);
	}
	
	public void stop()
	{
		
	}
	
}
