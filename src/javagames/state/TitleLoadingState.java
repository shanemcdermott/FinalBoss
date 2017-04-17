package javagames.state;

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

}
