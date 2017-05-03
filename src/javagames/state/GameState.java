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
import javagames.combat.DamageObject;
import javagames.combat.Damageable;
import javagames.combat.Enemy;
import javagames.combat.Pawn;
import javagames.combat.buffs.BuffManager;
import javagames.g2d.Sprite;

public abstract class GameState extends State 
{
	protected LoopEvent ambience;
	protected List<GameObject> gameObjects;
	protected List<Enemy> enemies;
	protected ArrayList<DamageObject> actionEffects;
	
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
		enemies = new ArrayList<Enemy>();
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
		
		actionEffects = new ArrayList<DamageObject>();
		
		timeElapsed = 0f;
		rejuv = false;
	}

	public void addActionEffect(DamageObject effect)
	{
		effect.reset();
		effect.setGameState(this);
		actionEffects.add(effect);
	}
	
	public void addObject(GameObject newObject)
	{
		if(newObject instanceof Avatar || newObject instanceof DamageObject) return;
		
		if(newObject instanceof Enemy)
		{
			enemies.add((Enemy)newObject);
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
		
		
		
		avatar.update(delta);
	
		updateGameObjects(delta);
		
		updateEnemies(delta);
		
		updateEffects(delta);
		
		updateOverlaps();
		
		activeRegion.setPosition(avatar.getPosition());
		
	}
	
	private void updateGameObjects(float deltaTime)
	{
		ArrayList<GameObject> copy = new ArrayList<GameObject>(gameObjects);
		for(GameObject go : copy)
		{
			if(activeRegion.intersects(go.getBounds()))
			{
				go.update(deltaTime);
				if(go.isActive() == false)
				{
					gameObjects.remove(go);
				}
			}
		}		
	}
	
	private void updateEnemies(float deltaTime)
	{
		ArrayList<Enemy> copy = new ArrayList<Enemy>(enemies);
		for(Enemy p: copy)
		{
			p.update(deltaTime);
			if(p.isDead())
			{
				avatar.addExperience(p.getExperienceReward());
				enemies.remove(p);
			}
		}
	}
	
	private void updateEffects(float deltaTime)
	{
		ArrayList<DamageObject> copy = new ArrayList<DamageObject>(actionEffects);
		for(DamageObject d: copy)
		{
			d.update(deltaTime);
			if(d.isActive() == false)
			{
				actionEffects.remove(d);
			}
		}
	}
	
	private void updateOverlaps()
	{
		ArrayList<Enemy> enCopy = new ArrayList<Enemy>(enemies);
		ArrayList<GameObject> objCopy = new ArrayList<GameObject>(gameObjects);

		for(Enemy p: enCopy)
		{
			ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
			BoundingShape b = p.getBounds();
				
			if(getOverlappingObjects(overlaps, b))
			{
				for(GameObject g: overlaps)
				{
					g.onBeginOverlap(p);
					p.onBeginOverlap(g);
				}
			}
		}
		
		for(GameObject p: objCopy)
		{
			ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
			BoundingShape b = p.getBounds();
				
			if(getOverlappingObjects(overlaps, b))
			{
				for(GameObject g: overlaps)
				{
					g.onBeginOverlap(p);
					p.onBeginOverlap(g);
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
		
		for(Enemy p : enemies)
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
		
		for(DamageObject d : actionEffects)
		{
			if(d.intersects(bounds))
			{
				overlappingObjects.add(d);
			}
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
		
		for(Enemy po : enemies)
		{
			po.draw(g,view);
		}
		
		for(DamageObject de: actionEffects)
		{
			de.draw(g, view);
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
		for(Enemy en : enemies)
		{
			controller.removeAttribute(en.getName());
		}
	}
}
