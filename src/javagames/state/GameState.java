package javagames.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.game.Avatar;
import javagames.game.GameObject;
import javagames.game.Pawn;
import javagames.game.PhysicsObject;
import javagames.sound.LoopEvent;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;
import javagames.g2d.Sprite;

public abstract class GameState extends State 
{
	protected LoopEvent ambience;
	protected List<GameObject> gameObjects;
	protected List<PhysicsObject> physicsObjects;
	protected KeyboardInput keys;
	protected Sprite background;
	protected Avatar avatar;
	
	public GameState()
	{
		gameObjects = new Vector<GameObject>();
		physicsObjects = new Vector<PhysicsObject>();
	}
	
	@Override
	public void enter() 
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		background = (Sprite) controller.getAttribute("background");
		avatar = (Avatar)controller.getAttribute("avatar");
		avatar.reset();
	}

	/*
	 * Add any stationary game objects from the controller
	 */
	public void addObjects(List<String> objectNames)
	{
		for(String s : objectNames)
		{
			GameObject g = (GameObject)controller.getAttribute(s);
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

	/*
	 * Add any moving objects from the controller
	 */
	public abstract void addPhysicsObjects();
		
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
		
		ArrayList<PhysicsObject> movingObjects = new ArrayList<PhysicsObject>();
		ArrayList<Vector2f> oldPositions = new ArrayList<Vector2f>();
		
		
		if(avatar.isMoving())
		{
			movingObjects.add(avatar);
			oldPositions.add(avatar.getWorldPosition());
		}
		
		avatar.update(delta);
		
		for(PhysicsObject p : physicsObjects)
		{
			if(p.isMoving())
			{
				movingObjects.add(p);
				oldPositions.add(p.getWorldPosition());
			}
			p.update(delta);
		}
				
		for (GameObject g : gameObjects) 
		{
			g.update(delta);
			if(g.getCollisionResponseTo("DEFAULT").equals("BLOCK"))
			{
				BoundingShape b = g.getBounds();
				int i = 0;
				for(PhysicsObject p : movingObjects)
				{
					if(p.intersects(b))
					{
						p.stopMotion();
						p.setPosition(oldPositions.get(i));
					}
					i++;
				}
			}
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
		return avatar.isDead();
	}
	
	protected abstract State getNextState();
}
