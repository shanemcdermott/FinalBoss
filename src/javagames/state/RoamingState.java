package javagames.state;

import java.awt.Color;

import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;

/*Roaming State is the "Open-World, Mayhem-causing portion of each level */

public abstract class RoamingState extends GameState 
{

	

	
	/*Return the next State to switch to*/
	@Override
	protected abstract State getNextState();

	/*Return the Dungeon State of this level */
	protected abstract DungeonState getDungeonState();

	@Override
	protected abstract boolean shouldChangeState(); 
	
}
