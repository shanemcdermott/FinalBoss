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

	
	public Projectile(String name, BoundingShape bounds, GameObject owner, SpriteSheet sprite, float launchSpeed) {
		super(name, bounds, owner, sprite);
		this.launchSpeed=launchSpeed;
		// TODO Auto-generated constructor stub
	}
	
	public Projectile(String name,  GameObject owner, SpriteSheet sprite, float launchSpeed)
	{
		super(name,owner,sprite);
		this.launchSpeed=launchSpeed;
	}

	@Override
	public void reset()
	{
		super.reset();
		physics.move(getOwner().getForwardVector().mul(launchSpeed));
		
	}
	
	public void launch(Vector2f direction)
	{
		Projectile p = new Projectile(name, bounds, owner, sprite, launchSpeed);
				p.setLifespan(lifespan);
				p.setDPS(damagePerSecond);
				p.setCanDamageOwner(bCanDamageOwner);
				getGameState().addActionEffect(p);
	}

	
}
