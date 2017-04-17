package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javagames.sound.LoopEvent;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;

public class TitleMenuState extends AttractState 
{
	protected Color fontColor = Color.GREEN;
	
	
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
		TitleMenuState s = new TitleMenuState();
		if(fontColor == Color.GREEN)
		{
			s.fontColor = Color.BLUE;
		}
		return s;
	}

	@Override
	public void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		
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
			"", 
			"",
			"P R E S S  S P A C E  T O  P L A Y  S O U N D",
			"",
			"H O L D  'W'  T O P L A Y  L O O P I N G  S O U N D",
			"",
			"P R E S S  E S C  T O  E X I T" 
		};
		Utility.drawCenteredString(g, width, height / 3, msg);
	}
}
