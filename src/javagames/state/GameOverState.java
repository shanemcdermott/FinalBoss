package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javagames.game.GameObject;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.GameConstants;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.g2d.Sprite;
import javagames.util.Utility;

public class GameOverState extends State 
{
	
	protected Color fontColor = Color.GREEN;
	protected float waitTime = 1.5f;
	private float time;

	protected State				nextState;		
	protected SoundCue			confirmSoundCue;
	protected SoundCue	  		cancelSoundCue;
	protected SoundLooper 		gameOverLoop;
	private Sprite 				background;
	protected KeyboardInput 	keys;
	
	@Override
	public void enter()
	{
		nextState = null;
		
		keys = (KeyboardInput) controller.getAttribute("keys");
		background = (Sprite) controller.getAttribute("background");
		gameOverLoop = (SoundLooper) controller.getAttribute("loop-gameOver");
		confirmSoundCue = (SoundCue)controller.getAttribute("cue-gui-confirm");
		cancelSoundCue = (SoundCue)controller.getAttribute("cue-gui-cancel");
		gameOverLoop.fire();
		time = 0.0f;
	}
	
	@Override
	public void processInput(float delta)
	{
		if (keys.keyDownOnce(KeyEvent.VK_ESCAPE)) 
		{
			app.shutDownGame();
		}
		else if (keys.keyDownOnce(KeyEvent.VK_Y))
		{
			confirmSoundCue.fire();
			nextState = (LoadingState) controller.getAttribute("loading-state");
		}
		else if (keys.keyDownOnce(KeyEvent.VK_N))
		{
			cancelSoundCue.fire();
			nextState = new TitleMenuState();
		}
	}
	
	@Override
	public void updateObjects(float delta)
	{
		if(nextState != null)
		{
			time += delta;
			if(time >= waitTime)
			{
				controller.setState(nextState);
			}
		}
	}
	
	@Override
	public void render(Graphics2D g, Matrix3x3f view)
	{
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
			"G A M E   O V E R",
			"",
			"C O N T I N U E ? (Y/N)"
		};
		Utility.drawCenteredString(g, width, height / 3, msg);
	}
	
	@Override
	public void exit()
	{
		gameOverLoop.done();
	}
}
