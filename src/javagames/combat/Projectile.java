package javagames.combat;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javagames.g2d.SpriteSheet;
import javagames.game.GameObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class Projectile extends DamageObject 
{
	protected float launchSpeed;
	protected ArrayList<Vector2f> positions;
	protected ArrayList<Vector2f> speeds;
	protected ArrayList<Float> timers;
	
	public Projectile(String name, BoundingShape bounds, GameObject owner, SpriteSheet sprite, float launchSpeed) {
		super(name, bounds, owner, sprite);
		positions = new ArrayList<Vector2f>();
		speeds = new ArrayList<Vector2f>();
		timers = new ArrayList<Float>();
		// TODO Auto-generated constructor stub
	}
	
	public Projectile(String name,  GameObject owner, SpriteSheet sprite, float launchSpeed)
	{
		super(name,owner,sprite);
		positions = new ArrayList<Vector2f>();
		speeds = new ArrayList<Vector2f>();
		timers = new ArrayList<Float>();
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		for(int i = 0; i < timers.size(); i++)
		{
			if(timers.get(i) >= lifespan) continue;
			
			timers.set(i, timers.get(i) + deltaTime);
			positions.set(i, positions.get(i).add(speeds.get(i).mul(deltaTime)));
		}
	}
	
	public void launch(Vector2f direction)
	{
		positions.add(getPosition());
		speeds.add(direction.mul(launchSpeed));
		timers.add(0.f);
	}
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		for(int i = 0; i < timers.size(); i++)
		{
			if(timers.get(i) >= lifespan) continue;
			
			sprite.render(g,view, positions.get(i).sub(posOffset), 0.f);
		}
	}
	
}
