package javagames.state;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

import javagames.game.Avatar;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Sprite;
import javagames.util.Vector2f;

public class TitleLoadingState extends LoadingState 
{
	
	protected String backgroundFileName = "space_background_600x600.png";
	protected String ambienceFileName = "AMBIENCE_alien.wav";

	@Override
	public void addSoundCues() {
		soundCues.put("explosion", "EXPLOSION_large_01.wav");
		soundCues.put("fire-clip", "WEAPON_scifi_fire_02.wav");
		soundCues.put("cue-gui-confirm", "WEAPON_scifi_fire_02.wav");
		soundCues.put("cue-gui-cancel", "EXPLOSION_large_01.wav");
	}

	@Override
	public void addSoundLoops() 
	{
		soundLoops.put("thruster", "DRONE9RE.WAV");
		soundLoops.put("loop-gameOver", "DRONE9RE.WAV");

	}
	
	@Override
	protected void enterNextState() 
	{
		getController().setState(new TitleMenuState());
	}

	@Override
	public void loadGameObjects() 
	{
		
		
		//Load Avatar
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
			
				BufferedImage image = ResourceLoader.loadImage(LoadingState.class, "dark_dragon_first.png");
				image = image.getSubimage(0,0,100,100);
				Vector2f topLeft = new Vector2f
				(
					-8.0f,
					8.0f 
				);
				
				Vector2f bottomRight = new Vector2f
				(
					8.0f,
					-8.0f 
				);
				Sprite sprite =	new Sprite( image, topLeft, bottomRight );
				Matrix3x3f viewport =(Matrix3x3f)controller.getAttribute( "viewport" );
				sprite.scaleImage( viewport );
				
				
				Avatar avatar = new Avatar("Draaknar, the Malevolent", sprite);
				controller.setAttribute( "avatar", avatar);
				
				return Boolean.TRUE;
			}
		});
		
	}

}
