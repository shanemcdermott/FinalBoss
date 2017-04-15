package javagames.state;

public abstract class GameState extends State 
{


	protected abstract State getState();

	protected abstract LoadingState getNextLoadingState();
}
