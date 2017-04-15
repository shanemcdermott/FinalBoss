package javagames.state;

public class DungeonState extends GameState 
{

	/*Return the next state to move to*/
	@Override
	protected  State getState() 
	{
		return new LoadingState();
	}

	@Override
	protected LoadingState getNextLoadingState() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
