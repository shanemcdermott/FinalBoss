package javagames.util.geom;

import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingCircle extends BoundingShape
{
	public float radius;
	
	public BoundingCircle(float radius)
	{
		this(new Vector2f(), radius);
	}
	
	public BoundingCircle(Vector2f position, float radius)
	{
		this(position,radius,"DEFAULT");
	}

	public BoundingCircle(Vector2f position, float radius, String collisionChannel)
	{
		collisionResponse =  Collections.synchronizedMap(new HashMap<String, String>());
		collisionResponse.put("DEFAULT", "BLOCK");
		this.collisionChannel = collisionChannel;
		
		this.position = position;
		this.radius = radius;
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		if(otherShape instanceof BoundingBox)
		{
			BoundingBox aabb = (BoundingBox)otherShape;
			return aabb.intersectsCircle(this);
		}	
		if(otherShape instanceof BoundingCircle)
			return intersectsCircle((BoundingCircle) otherShape);
			
		System.err.println("otherShape is not recognized!");
		return false;
	}

	public boolean intersectsCircle(BoundingCircle otherCircle)
	{
		return intersectsCircle(otherCircle.position, otherCircle.radius);
	}
	
	/**
	 * Intersection test with another circle
	 * @param position	Other circle's position
	 * @param radius	Other circle's Radius
	 * @return	true if the circles intersect
	 */
	public boolean intersectsCircle(Vector2f position, float radius)
	{
		Vector2f c = this.position.sub(position);
		float r = this.radius + radius;
		return c.lenSqr() < r * r;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		Vector2f dist = point.sub(position);
		return dist.lenSqr() < radius*radius;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		// TODO Auto-generated method stub
		
	}
	
}
