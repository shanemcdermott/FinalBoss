package javagames.kanto;

import java.util.concurrent.Callable;

import javagames.game.Avatar;
import javagames.game.GameObjectFactory;
import javagames.state.LoadingState;
import javagames.state.TitleMenuState;

public class KantoLoadingState extends LoadingState 
{
	
	


	public KantoLoadingState()
	{
		displayString = "Kanto";
		backgroundFileName = "map.png";
		ambienceFileName = "AMBIENCE_alien.wav";
	}
	
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
		getController().setState(new KantoRoamingState());
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
			
				Avatar avatar = GameObjectFactory.createAvatar("Nihil");
				controller.setAttribute( "avatar", avatar);
				
				return Boolean.TRUE;
			}
		});
		
	}

}
