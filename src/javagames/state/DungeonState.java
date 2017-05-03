package javagames.state;

public abstract class DungeonState extends GameState 
{

	
	public boolean isBossDead()
	{
		return false;
	}
	

	
	/*Return the next State to switch to*/
	@Override
	protected State getNextState()
	{
		if(isBossDead())
		{
			return getLoadingState();
		}
		else if(avatar.isDead())
		{
			return new GameOverState();
		}
		
		return new TitleMenuState();
	}

	/*Return the Loading State of the next level */
	protected abstract LoadingState getLoadingState();

	@Override
	protected boolean shouldChangeState()
	{
		return isBossDead() || avatar.isDead();
	}
	
}
