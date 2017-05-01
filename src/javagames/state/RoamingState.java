package javagames.state;

import java.awt.Color;
import java.awt.Graphics2D;

import javagames.game.GameObject;
import javagames.game.PhysicsObject;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;

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
	
		background.render(g,view,avatar.getPosition().mul(-1.f), 0.f);
		
		avatar.draw(g, view, avatar.getPosition());
		
		for(GameObject go : gameObjects)
		{
			go.draw(g,view, avatar.getPosition());
		}
		
		for(PhysicsObject po : physicsObjects)
		{
			po.draw(g,view, avatar.getPosition());
		}
			
		
		if(foreground != null)
		{
			foreground.render(g, view, avatar.getPosition().mul(-1.f),0.f);
		}
		
	}
	
}
