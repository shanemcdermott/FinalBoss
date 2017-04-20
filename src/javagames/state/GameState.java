package javagames.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Vector;

import javagames.game.Avatar;
import javagames.game.GameObject;
import javagames.sound.LoopEvent;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.Sprite;

public abstract class GameState extends State 
{
	protected LoopEvent ambience;
	protected List<GameObject> gameObjects;
	protected KeyboardInput keys;
	protected Sprite background;
	protected Avatar avatar;
	
	@Override
	public void enter() 
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		background = (Sprite) controller.getAttribute("background");
		avatar = (Avatar)controller.getAttribute("avatar");
		if(gameObjects == null)
		{
			gameObjects = new Vector<GameObject>();
			addObjects();
		}
		
	}

	/*
	 * Add any necessary game objects from the controller
	 */
	public abstract void addObjects();
	
	public void processInput(float delta) 
	{
		avatar.processInput(keys, delta);
	}

	
	@Override
	public void updateObjects(float delta) 
	{
		
		if (shouldChangeState()) 
		{
			getController().setState(getNextState());
			return;
		}
		
		avatar.update(delta);
		for (GameObject g : gameObjects) 
		{
			g.update(delta);
		}
		
	}
	
	@Override
	public void render(Graphics2D g, Matrix3x3f view)
	{
	
		background.render(g,view);
		avatar.draw(g, view);
		
		for(GameObject go : gameObjects)
		{
			go.draw(g,view);
		}
		
		
	}
	
	protected boolean shouldChangeState()
	{
		return isAvatarDead();
	}
	
	public boolean isAvatarDead()
	{
		return false;
	}
	
	protected abstract State getNextState();
}
