package javagames.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
import javagames.util.geom.BoundingGroup;
import javagames.util.geom.BoundingShape;
import javagames.combat.Avatar;
import javagames.combat.Damageable;
import javagames.combat.Pawn;
import javagames.combat.buffs.BuffManager;
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
	
	private float timeElapsed;
	private boolean rejuv;
	
	
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
		avatar.setGameState(this);
		//avatar.addBuff( BuffManager.getBuff( 1, avatar ) );
		//avatar.addBuff( BuffManager.getBuff( 2, avatar ) );
		//avatar.addBuff( BuffManager.getBuff( 3, avatar ) );
		
		timeElapsed = 0f;
		rejuv = false;
	}

	public void addObject(GameObject newObject)
	{

		if(newObject instanceof Pawn)
		{
			if(newObject instanceof Avatar)
			{
				avatar = (Avatar)newObject;
			}
		}
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
			g.setGameState(this);
		}
		
		/*
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
		*/
	}
		
	public void processInput(float delta) 
	{
		if(keys.keyDownOnce(KeyEvent.VK_ESCAPE))
		{
			System.exit(0);
		}
		if(keys.keyDownOnce(KeyEvent.VK_ENTER))
		{
			avatar.takeDamage(avatar, 5000.f);
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
		
		timeElapsed += delta;
		
		/*
		if ( timeElapsed >= 8 && !rejuv ) {
			avatar.addBuff( BuffManager.getBuff( 4, avatar ) );
			rejuv = true;
		}
		*/
		
		Vector<PhysicsObject> movingObjects = new Vector<PhysicsObject>();
		
		
		if(avatar.isMoving())
		{
			movingObjects.add(avatar);
		}
		
		avatar.update(delta);
		
		Iterator<PhysicsObject> physr = physicsObjects.iterator();
		while (physr.hasNext()) 
		{
			PhysicsObject p = physr.next();
			if(activeRegion.contains(p.getPosition()))
			{
				if(p.isMoving())
				{
					movingObjects.add(p);
				}
				p.update(delta);
			}
		}
		
		Iterator<GameObject> iter = gameObjects.iterator();
		while (iter.hasNext()) 
		{
			GameObject g = iter.next();
			g.update(delta);
		}
		
		
		for(PhysicsObject m : movingObjects)
		{
			ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
			BoundingShape b = m.getBounds();
			if(getOverlappingObjects(overlaps, b))
			{
				for(GameObject g: overlaps)
				{
					g.onBeginOverlap(m);
					m.onBeginOverlap(g);
				}
			}
			
		}
	}
	
	public boolean getOverlappingObjects(List<GameObject> overlappingObjects, BoundingShape bounds)
	{
		if(activeRegion.intersects(bounds) == false) return false;
		
		for(GameObject g : gameObjects)
		{
			if(g.intersects(bounds))
			{
				overlappingObjects.add(g);
			}
		}
		
		for(PhysicsObject p : physicsObjects)
		{
			if(p.intersects(bounds))
			{
				overlappingObjects.add(p);
			}
		}
		
		if(avatar.intersects(bounds))
		{
			overlappingObjects.add(avatar);
		}
		
		return overlappingObjects.isEmpty();
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
	
	@Override
	public void exit()
	{
		ambience.done();
		for(GameObject go : gameObjects)
		{
			controller.removeAttribute(go.getName());
		}
		for(PhysicsObject po: physicsObjects)
		{
			controller.removeAttribute(po.getName());
		}
	}
}
