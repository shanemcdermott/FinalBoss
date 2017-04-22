package javagames.game;

import com.sun.glass.events.KeyEvent;

import javagames.util.KeyboardInput;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.util.Vector2f;

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
		if(keyboard.keyDown(KeyEvent.VK_W) || keyboard.keyDown(KeyEvent.VK_UP))
			move(Vector2f.up());
		else if(keyboard.keyDown(KeyEvent.VK_S) || keyboard.keyDown(KeyEvent.VK_DOWN))
			move(Vector2f.down());
		
		else if(keyboard.keyDown(KeyEvent.VK_D) || keyboard.keyDown(KeyEvent.VK_RIGHT))
			move(Vector2f.right());
		
		else if(keyboard.keyDown(KeyEvent.VK_A) || keyboard.keyDown(KeyEvent.VK_LEFT))
			move(Vector2f.left());
		else
			move(new Vector2f());
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

}
