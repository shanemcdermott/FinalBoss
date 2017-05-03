package buffs;

public interface Buffable {

	public void addBuff ( Buff buff );
	public void displayBuffs ();
	public void updateBuffs ( float deltaTime );
	public int buffCount ();
	public int getStrength ();
	public int getSpeed ();
	public int getMagic ();
	public int getLevel ();
	
}
