package javagames.game;

public interface ObjectState 
{
	public void update(float deltaTime);
	public void reset();
	public void enter();
	public void exit();
	public void setOwner(MultiStateObject owner);
	public String getName();
	
}
