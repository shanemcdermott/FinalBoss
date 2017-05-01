package javagames.combat;

import java.util.List;
import java.util.Vector;

import javagames.game.GameObject;
import javagames.util.geom.BoundingShape;

public class DamageObject extends GameObject 
{
	protected GameObject owner;
	protected float damagePerSecond;
	protected float lifespan;
	
	protected boolean bCanDamageOwner;
	protected float currentTime;
	
	public List<Damageable> overlappedObjects;
	
	public DamageObject(String name, BoundingShape bounds, GameObject owner) 
	{
		super(name, bounds);
		this.owner = owner;
		lifespan = 1.f;
		damagePerSecond = 1.f;
		bounds.setCollisionResponseTo("DEFAULT", "OVERLAP");
		overlappedObjects = new Vector<Damageable>();
		// TODO Auto-generated constructor stub
	}

	public void update(float deltaTime)
	{
		super.update(deltaTime);
		currentTime+=deltaTime;
		for(Damageable d : overlappedObjects)
		{
			d.takeDamage(this, damagePerSecond*deltaTime);
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
	
	
	public boolean canDamageOwner()
	{
		return bCanDamageOwner;
	}
}
