package javagames.game;

import java.awt.Graphics2D;

import com.sun.glass.events.KeyEvent;

import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.util.Vector2f;

public class Avatar extends GreaterPawn 
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
			move(Vector2f.up());
			((SpriteSheet)sprite).startAnimation("WalkUp");
		}
		else if(keyboard.keysDownOnce(KeyEvent.VK_S, KeyEvent.VK_DOWN))
		{
			move(Vector2f.down());
			((SpriteSheet)sprite).startAnimation("WalkDown");
		}
		
		else if(keyboard.keysDownOnce(KeyEvent.VK_D, KeyEvent.VK_RIGHT))
		{
			move(Vector2f.right());
			((SpriteSheet)sprite).startAnimation("WalkRight");
		}
		
		else if(keyboard.keysDownOnce(KeyEvent.VK_A,KeyEvent.VK_LEFT))
		{
			move(Vector2f.left());
			((SpriteSheet)sprite).startAnimation("WalkLeft");

		}
		else if(keyboard.keysDownOnce(KeyEvent.VK_1,KeyEvent.VK_NUMPAD1))
			fastAction();
		else if(keyboard.keysDownOnce(KeyEvent.VK_2,KeyEvent.VK_NUMPAD2))
			powerAction();	
		else if(keyboard.keysDownOnce(KeyEvent.VK_3,KeyEvent.VK_NUMPAD3))
			specialAction();
		else if(keyboard.keysDownOnce(KeyEvent.VK_4, KeyEvent.VK_NUMPAD4))
			powerAction();	
		else if(!keyboard.keysDown(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT))
		{
			stopMoving();
			((SpriteSheet)sprite).startAnimation("StandDown");
		}
	}
	
	@Override
	protected void die(Object source) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void fastAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void powerAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void specialAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void ultimateAction() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		sprite.render(g, view, position.sub(posOffset), rotation);
	}
	
}
