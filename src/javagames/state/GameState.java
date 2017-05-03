package javagames.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.game.GameObject;
import javagames.game.Gui;
import javagames.game.MultiStateObject;
import javagames.game.PhysicsObject;
import javagames.sound.LoopEvent;
import javagames.util.FrameRate;
import javagames.util.GameConstants;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.RelativeMouseInput;
import javagames.util.Utility;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;
import javagames.combat.Avatar;
import javagames.combat.Damageable;
import javagames.combat.Pawn;
import javagames.g2d.Sprite;

public abstract class GameState extends State 
{
	protected LoopEvent ambience;
	protected List<GameObject> gameObjects;
	protected List<PhysicsObject> physicsObjects;
	
	protected KeyboardInput keys;
	protected Sprite background;
	protected Sprite foreground;
	protected Avatar avatar;
	public BoundingBox activeRegion;
	protected Gui gui;
	
	public GameState()
	{
		gameObjects = new ArrayList<GameObject>();
		physicsObjects = new ArrayList<PhysicsObject>();
		activeRegion = new BoundingBox(2*GameConstants.VIEW_WIDTH, 2*GameConstants.VIEW_HEIGHT);
		
	}
	
	@Override
	public void enter() 
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		background = (Sprite) controller.getAttribute("background");
		foreground = (Sprite) controller.getAttribute("foreground");
		ambience = (LoopEvent) controller.getAttribute("ambience");
		ambience.fire();
		
		avatar = (Avatar)controller.getAttribute("avatar");
		gui = new Gui(avatar);
		avatar.reset();
		
		Vector2f spawn = (Vector2f)controller.getAttribute("spawnPoint");
		avatar.setPosition(spawn);
		activeRegion.setPosition(avatar.getPosition());
		GameConstants.GAME_STATE = this;
	}

	public void addObject(GameObject newObject)
	{
		if(newObject instanceof PhysicsObject)
		{
			physicsObjects.add((PhysicsObject)newObject);
		}
		else
		{
			gameObjects.add(newObject);
		}
		System.out.println(newObject.getName() + " added.");
	}
	
	/*
	 * Add any game objects from the controller
	 */
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
		
		for(PhysicsObject p : physicsObjects)
		{
			if(p instanceof MultiStateObject)
			{
				MultiStateObject mo = (MultiStateObject)p;
				for(GameObject g: mo.getEffects())
				{
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
		}
	}
		
	public void processInput(float delta) 
	{
		if(keys.keyDownOnce(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
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
		
		Vector<PhysicsObject> movingObjects = new Vector<PhysicsObject>();
		
		
		if(avatar.isMoving())
		{
			movingObjects.add(avatar);
		}
		
		avatar.update(delta);
		
		for(PhysicsObject p : physicsObjects)
		{
			if(activeRegion.contains(p.getPosition()))
			{
				if(p.isMoving())
				{
					movingObjects.add(p);
				}
				p.update(delta);
			}
		}
				
		for (GameObject g : gameObjects) 
		{
			g.update(delta);
		}
		
		
		for(PhysicsObject m : movingObjects)
		{
			BoundingShape b = m.getBounds();
			for(GameObject g : gameObjects)
			{
				if(m.equals(g)) continue;
				if(g.intersects(b))
				{
					g.onBeginOverlap(m);
					m.onBeginOverlap(g);
				}
			}
			
			for(PhysicsObject p : physicsObjects)
			{
				if(m.equals(p)) continue;
				if(p.intersects(b))
				{
					p.onBeginOverlap(m);
					m.onBeginOverlap(p);
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
		
		for(PhysicsObject po : physicsObjects)
		{
			po.draw(g,view);
		}
		
		if(foreground != null)
		{
			foreground.render(g, view);
		}
				
	}
	
	protected boolean shouldChangeState()
	{
		return avatar.isDead();
	}
	
	protected abstract State getNextState();
}
