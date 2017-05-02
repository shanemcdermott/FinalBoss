package javagames.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.game.GameObject;
import javagames.game.PhysicsObject;
import javagames.sound.LoopEvent;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
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
		foreground = (Sprite) controller.getAttribute("foreground");
		ambience = (LoopEvent) controller.getAttribute("ambience");
		ambience.fire();
		
		avatar = (Avatar)controller.getAttribute("avatar");
		avatar.reset();
		
		Vector2f spawn = (Vector2f)controller.getAttribute("spawnPoint");
		avatar.setPosition(spawn);
	}

	/*
	 * Add any stationary game objects from the controller
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
	}

	/*
	 * Add any moving objects from the controller
	 */
	public abstract void addPhysicsObjects();
		
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
				oldPositions.add(p.getPosition());
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
					if(p.getCollisionChannel().equals("DEFAULT"))
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
		
		int i = 0;
		for(PhysicsObject m : movingObjects)
		{
			BoundingShape b = m.getBounds();
			for(PhysicsObject p : physicsObjects)
			{
				if(m.equals(p)) continue;
				if(p.intersects(b))
				{
					m.stopMotion();
					m.setPosition(oldPositions.get(i));
				}
			}
			i++;
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
