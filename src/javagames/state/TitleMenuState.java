package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javagames.combat.Avatar;
import javagames.kanto.KantoLoadingState;
import javagames.kanto.KantoRoamingState;
import javagames.sound.LoopEvent;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Utility;
import javagames.util.XMLUtility;

public class TitleMenuState extends AttractState 
{
	protected Color fontColor = Color.BLUE;
	
	protected String[] avatars = {"Draaknar","Queen Zeal","Goonthoro","Nihil"};
	
	int avatarIndex = 0;
	protected LoopEvent ambience;
	protected SoundCue laser;
	
	protected SoundLooper thruster;
	private boolean bShouldLoopPlay = false;
	
	@Override
	public void enter()
	{
		super.enter();
	
		laser = (SoundCue) controller.getAttribute("fire-clip");
		thruster = (SoundLooper) controller.getAttribute("thruster");
		ambience = (LoopEvent) controller.getAttribute("ambience");
		ambience.fire();
	}
	
	@Override
	public void exit()
	{
		ambience.done();
	}
	
	@Override
	protected AttractState getState() 
	{
		return this;
	}

	@Override
	public void processInput(float deltaTime)
	{
		super.processInput(deltaTime);

		if(keys.keyDownOnce(KeyEvent.VK_DOWN))
		{
			avatarIndex = Math.min(avatarIndex + 1, avatars.length-1);
		}
		if(keys.keyDownOnce(KeyEvent.VK_UP))
		{
			avatarIndex = Math.max(0,avatarIndex - 1);
		}
		
		
		//Sound Cue Example
		if(keys.keyDownOnce(KeyEvent.VK_SPACE))
		{
			laser.fire();
		}
		//Looping Sound Example
		if (keys.keyDown(KeyEvent.VK_W)) 
		{
			if (!bShouldLoopPlay) 
			{
				thruster.fire();
				bShouldLoopPlay = true;
			}
		} 
		else if (bShouldLoopPlay) 
		{
			thruster.done();
			bShouldLoopPlay = false;
		}
		else if(keys.keyDownOnce(KeyEvent.VK_ENTER))
		{
			try
			{
				controller.setAttribute("avatar", XMLUtility.loadAvatar(this.getClass(), avatars[avatarIndex]));
				
			}
			catch(Exception e)
			{
				System.err.println("Failed to create avatar.");
				e.printStackTrace();
				System.err.println(e);
				System.exit(1);
			}
			getController().setState(new KantoLoadingState());
			return;
		}
	}
		
	public void render(Graphics2D g, Matrix3x3f view) {
		super.render(g, view);
		int width = app.getScreenWidth();
		int height = app.getScreenHeight();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(fontColor);
		String[] msg = { 
			GameConstants.APP_TITLE,
			"",  
			"P R E S S  ENTER  T O",
			"",
			"S E L E C T  A  C H A R A C T E R",
			"",
			"",
			"P R E S S  E S C  T O  E X I T" 
		};
		int y = Utility.drawCenteredString(g, width, height / 3, msg);
		for(int i = 0; i < avatars.length; i++)
		{
			if(i==avatarIndex)
				g.setColor(Color.GREEN);
			else
				g.setColor(fontColor);
			
			y = Utility.drawCenteredString(g, width, y, avatars[i]);
		}
	}
}
