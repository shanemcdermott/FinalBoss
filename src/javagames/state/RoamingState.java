package javagames.state;

import java.awt.Color;

import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;

/*Roaming State is the "Open-World, Mayhem-causing portion of each level */

public abstract class RoamingState extends GameState 
{

	

	
	/*Return the next State to switch to*/
	@Override
	protected abstract AttractState getState();

	/*Return the Dungeon State of this level */
	protected abstract DungeonState getDungeonState();

	
	protected abstract boolean shouldChangeState(); 
	
}
