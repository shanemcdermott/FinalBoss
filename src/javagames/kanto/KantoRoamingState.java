package javagames.kanto;

import javagames.state.AttractState;
import javagames.state.DungeonState;
import javagames.state.GameOverState;
import javagames.state.RoamingState;
import javagames.state.State;
import javagames.state.TitleMenuState;

public class KantoRoamingState extends RoamingState 
{

	@Override
	protected State getNextState() 
	{
		if(avatar.isDead())
		{
			return new GameOverState();
		}
		
		return new TitleMenuState();
	}

	@Override
	protected DungeonState getDungeonState() 
	{
		return new KantoDungeonState();
	}

	@Override
	protected boolean shouldChangeState() 
	{
		return avatar.isDead();
	}

	@Override
	public void addObjects() 
	{
		// TODO Auto-generated method stub

	}


}