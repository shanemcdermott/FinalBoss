package javagames.game;

public abstract class Avatar extends Pawn 
{
	
	protected int	experienceTotal;
	protected int	currentLevel;
	
	protected float manaBase;
	protected float manaScale;
	protected float manaBonus;
	protected float manaCurrent;
	
	/*Apply appropriate level modifiers here */
	protected abstract void setLevel(int newLevel);
	
	
	@Override
	protected void die(Object source) 
	{
		// TODO Auto-generated method stub

	}

}
