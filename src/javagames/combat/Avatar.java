package javagames.combat;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.glass.events.KeyEvent;

import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.Pawn;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class Avatar extends Pawn 
{
	protected int	experienceTotal;
	protected int	currentLevel;
	
	protected float manaBase;
	protected float manaScale;
	protected float manaBonus;
	protected float manaCurrent;

	public Avatar(String name, SpriteSheet sprite)
	{
		super(name,sprite);
	}
	
	public Avatar(String name, SpriteSheet sprite, BoundingShape bounds)
	{
		super(name,sprite,bounds);
		healthBase = 100.f;
		manaBase = 100.f;
		manaScale = 1.f;
		manaBonus = 0.f;
	}
	
	/*Apply appropriate level modifiers here */
	protected void setLevel(int newLevel)
	{
		
	}
	
	@Override
	public void reset()
	{
		super.reset();
		resetMana();
	}
	
	
	protected void resetMana()
	{
		manaCurrent = getMaxMana();
	}
	
	public float getMaxMana()
	{
		return manaBase * manaScale + manaBonus;
	}
	
	public void processInput(KeyboardInput keyboard, float deltaTime)
	{
		if(keyboard.keysDownOnce(KeyEvent.VK_W, KeyEvent.VK_UP))
		{	
			move("WalkUp");
		}
		else if(keyboard.keysDownOnce(KeyEvent.VK_S, KeyEvent.VK_DOWN))
		{
			move("WalkDown");
		}
		
		else if(keyboard.keysDownOnce(KeyEvent.VK_D, KeyEvent.VK_RIGHT))
		{
			move("WalkRight");
		}
		
		else if(keyboard.keysDownOnce(KeyEvent.VK_A,KeyEvent.VK_LEFT))
		{
			move("WalkLeft");
		}
		else if(keyboard.keysDownOnce(KeyEvent.VK_1,KeyEvent.VK_NUMPAD1))
			setState(new CombatAction("Alive"));
		else if(keyboard.keysDownOnce(KeyEvent.VK_2,KeyEvent.VK_NUMPAD2))
			setState(new CombatAction("Alive"));
		else if(keyboard.keysDownOnce(KeyEvent.VK_3,KeyEvent.VK_NUMPAD3))
			setState(new CombatAction("Alive"));
		else if(keyboard.keysDownOnce(KeyEvent.VK_4, KeyEvent.VK_NUMPAD4))
			setState(new CombatAction("Alive"));
		else if(keyboard.keysReleased(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D))
		{
			if(keyboard.keysDown(KeyEvent.VK_W, KeyEvent.VK_UP))
			{	
				move("WalkUp");
			}
			else if(keyboard.keysDown(KeyEvent.VK_S, KeyEvent.VK_DOWN))
			{
				move("WalkDown");
			}
			
			else if(keyboard.keysDown(KeyEvent.VK_D, KeyEvent.VK_RIGHT))
			{
				move("WalkRight");
			}
			
			else if(keyboard.keysDown(KeyEvent.VK_A,KeyEvent.VK_LEFT))
			{
				move("WalkLeft");
			}
			else
			{
				stopMoving();
			}
		}
	}
	
	@Override
	public void die(Object source) 
	{
		// TODO Auto-generated method stub

	}

	
}
