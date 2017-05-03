package javagames.deathValley;

import javagames.game.GameObject;
import javagames.state.AttractState;
import javagames.state.DungeonState;
import javagames.state.GameOverState;
import javagames.state.RoamingState;
import javagames.state.State;
import javagames.state.TitleMenuState;
import javagames.util.Vector2f;

public class DeathValleyGraveyardState extends RoamingState 
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
		return new DeathValleyCryptState();
	}

	@Override
	protected boolean shouldChangeState() 
	{
		return avatar.isDead();
	}

	public void addObject(GameObject object)
	{
		gameObjects.add(object);
	}



}
