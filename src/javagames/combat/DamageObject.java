package javagames.combat;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Element;

import javagames.g2d.SpriteSheet;
import javagames.game.GameObject;
import javagames.game.Ownable;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Vector2f;
import javagames.util.XMLUtility;
import javagames.util.geom.BoundingShape;

public class DamageObject extends GameObject implements Ownable
{
	protected GameObject owner;
	protected SpriteSheet sprite;
	
	protected float damagePerSecond;
	protected float lifespan;
	
	protected boolean bCanDamageOwner;
	protected float currentTime;
	
	public List<Damageable> overlappedObjects;
	
	public DamageObject(String name, GameObject owner, SpriteSheet sprite)
	{
		super(name);
		this.owner = owner;
		lifespan = 1.f;
		damagePerSecond = 1.f;
		bounds.setCollisionResponseTo("DEFAULT", "OVERLAP");
		overlappedObjects = new Vector<Damageable>();
		this.sprite = sprite;
	}
	
	public DamageObject(String name, BoundingShape bounds, GameObject owner, SpriteSheet sprite) 
	{
		super(name, bounds);
		this.owner = owner;
		lifespan = 1.f;
		damagePerSecond = 1.f;
		bounds.setCollisionResponseTo("DEFAULT", "OVERLAP");
		overlappedObjects = new Vector<Damageable>();
		this.sprite = sprite;
	}

	@Override
	public void setOwner(GameObject owner)
	{
		this.owner = owner;
	}
	
	public void setCanDamageOwner(boolean bCanDamage)
	{
		this.bCanDamageOwner = bCanDamage;
	}
	
	public void setLifespan(float lifespan)
	{
		this.lifespan = lifespan;
	}
	
	public void setDPS(float DPS)
	{
		damagePerSecond = DPS;
	}

	@Override
	public void reset()
	{
		activate();
	}
	
	public void activate()
	{
		currentTime = 0.f;
		sprite.startAnimation("Active");
	}
	
	public void deactivate()
	{
		sprite.startAnimation("Inactive");
	}
	
	@Override
	public void update(float deltaTime)
	{
		setPosition(owner.getPosition().add(owner.getForwardVector()));
		super.update(deltaTime);
		if(isActive())
		{
			currentTime+=deltaTime;
			for(Damageable d : overlappedObjects)
			{
				d.takeDamage(this, damagePerSecond*deltaTime);
			}
			sprite.update(deltaTime);
		}
		
	}
	
	@Override
	public void onBeginOverlap(GameObject other)
	{
		if(other instanceof Damageable)
		{
			if(canDamageOwner() || other!=owner)
				overlappedObjects.add(((Damageable)other));
		}	
	}
	
	//Called when another object stops overlapping with this object
	@Override
	public void onEndOverlap(GameObject other)
	{
		if(overlappedObjects.contains(other))
		{
			overlappedObjects.remove(other);
		}
	}
	
	@Override
	public boolean isActive()
	{
		return lifespan > currentTime;
	}
	
	public float getRemainingLifespan()
	{
		return lifespan - currentTime;
	}
	
	public boolean canDamageOwner()
	{
		return bCanDamageOwner;
	}
	
	@Override
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		if(isActive())
			sprite.render(g, view, position.sub(posOffset), rotation);
	}

	@Override
	public GameObject getOwner() 
	{
		return owner;
	}
}
