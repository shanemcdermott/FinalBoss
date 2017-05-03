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
import javagames.util.StopWatch;
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
		StopWatch watch = new StopWatch();
		watch.start();
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
		watch.stop();
		System.out.println("Added Objects: " + watch.totalMilliseconds());
	}

	
	@Override
	public void updateObjects(float delta) 
	{
		StopWatch watch = new StopWatch();
		watch.start();
		super.updateObjects(delta);
		watch.stop();
		System.out.println("Updated Objects" + watch.totalMilliseconds());
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
		StopWatch watch = new StopWatch();
		watch.start();
		background.render(g,view,avatar.getPosition().mul(-1.f), 0.f);
		watch.stop();
		System.out.println("Background Render: " + watch.totalMilliseconds());
		avatar.draw(g, view, avatar.getPosition());
		
		watch.start();
		for(GameObject go : gameObjects)
		{
			if(activeRegion.contains(go.getPosition()))
				go.draw(g,view, avatar.getPosition());
		}
		watch.stop();
		System.out.println("GameObject Render: " + watch.totalMilliseconds());
		watch.start();
		for(PhysicsObject po : physicsObjects)
		{
			if(activeRegion.contains(po.getPosition()))
				po.draw(g,view, avatar.getPosition());
		}
		watch.stop();
		System.out.println("PhysicsObject Render: " + watch.totalMilliseconds());
		if(foreground != null)
		{
			watch.start();
			foreground.render(g, view, avatar.getPosition().mul(-1.f),0.f);
			watch.stop();
			System.out.println("Foreground Render: " + watch.totalMilliseconds());
		}
		
		gui.draw(g);
		
		g.drawString(String.format("%s", avatar.getCurrentState().getName()), 10, 30);
	}
	
}
