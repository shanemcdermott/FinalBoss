package javagames.state;

import java.awt.Color;
import java.awt.Graphics2D;

import javagames.game.GameObject;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.Matrix3x3f;

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
	
	@Override
	public void render(Graphics2D g, Matrix3x3f view)
	{
		//view = view.mul(Matrix3x3f.translate(avatar.getPosition()));
		background.render(g,view,avatar.getPosition().mul(-1.f), 0.f);
		avatar.draw(g, view);
		
		for(GameObject go : gameObjects)
		{
			go.draw(g,view);
		}
		
		
	}
	
}
