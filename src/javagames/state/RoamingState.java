package javagames.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;
import javagames.combat.Avatar;
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
import javagames.util.Utility;
import javagames.util.Vector2f;

/*Roaming State is the "Open-World, Mayhem-causing portion of each level */

public abstract class RoamingState extends GameState 
{

	/*
	 * Add any stationary game objects from the controller
	 */
	@Override
	public void addObjects(List<String> objectNames)
	{
		for(String s : objectNames)
		{
			GameObject g = (GameObject)controller.getAttribute(s);
			g.reset();
			if(g instanceof PhysicsObject)
			{
				physicsObjects.add((PhysicsObject)g);
			}
			else
			{
				gameObjects.add(g);
			}
		}
	}

	
	@Override
	public void updateObjects(float delta) 
	{
		super.updateObjects(delta);
		activeRegion.setPosition(avatar.getPosition());

	}
	
	
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
		
		for(PhysicsObject po : physicsObjects)
		{
			if(activeRegion.contains(po.getPosition()))
				po.draw(g,view, avatar.getPosition());
		}
			
		if(foreground != null)
		{
			foreground.render(g, view, avatar.getPosition().mul(-1.f),0.f);
		}
		
		gui.draw(g);
		
		g.drawString(String.format("%s", avatar.getCurrentState().getName()), 10, 30);
	}
	
}
