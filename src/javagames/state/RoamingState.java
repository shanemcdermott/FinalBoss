package javagames.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;
import javagames.combat.Avatar;
import javagames.combat.DamageObject;
import javagames.combat.Enemy;
import javagames.g2d.Sprite;
import javagames.game.GameObject;
import javagames.game.PhysicsObject;
import javagames.sound.LoopEvent;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.FrameRate;
import javagames.util.GameConstants;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.StopWatch;
import javagames.util.Utility;
import javagames.util.Vector2f;

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
			if(activeRegion.contains(go.getPosition()))
				go.draw(g,view, avatar.getPosition());
		}
	
		for(Enemy po : enemies)
		{
			if(activeRegion.contains(po.getPosition()))
				po.draw(g,view, avatar.getPosition());
		}
		
		for(DamageObject dam: actionEffects)
		{
			if(activeRegion.intersects(dam.getBounds()) && dam.isActive())
			{
				dam.draw(g, view,avatar.getPosition());
			}
		}
		
		if(foreground != null)
		{
			
			foreground.render(g, view, avatar.getPosition().mul(-1.f),0.f);
			
		}
		
		gui.draw(g);
		
		g.drawString(String.format("%s", avatar.getCurrentState().getName()), 10, 30);
	}
	
}
