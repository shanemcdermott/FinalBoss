package javagames.state;

import java.util.List;
import java.util.Vector;

import javagames.game.GameObject;
import javagames.sound.LoopEvent;
import javagames.util.KeyboardInput;
import javagames.util.Sprite;

public abstract class GameState extends State 
{
	protected LoopEvent ambience;
	protected List<GameObject> gameObjects;
	protected KeyboardInput keys;
	protected Sprite background;
	
	@Override
	public void enter() 
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		background = (Sprite) controller.getAttribute("background");
		if(gameObjects == null)
		{
			gameObjects = new Vector<GameObject>();
			//gameObjects.add(...);
		}
		
	}

	@Override
	public void updateObjects(float delta) 
	{
		
		if (shouldChangeState()) 
		{
			getController().setState(getNextState());
			return;
		}
		for (GameObject g : gameObjects) 
		{
			g.update(delta);
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
