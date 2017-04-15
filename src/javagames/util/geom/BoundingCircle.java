package javagames.util.geom;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingCircle implements BoundingShape
{

	public Vector2f center;
	public float radius;
	
	public BoundingCircle(float radius)
	{
		this(new Vector2f(), radius);
	}
	
	public BoundingCircle(Vector2f center, float radius)
	{
		this.center = center;
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
		return intersectsCircle(otherCircle.center, otherCircle.radius);
	}
	
	/**
	 * Intersection test with another circle
	 * @param center	Other circle's Center
	 * @param radius	Other circle's Radius
	 * @return	true if the circles intersect
	 */
	public boolean intersectsCircle(Vector2f center, float radius)
	{
		Vector2f c = this.center.sub(center);
		float r = this.radius + radius;
		return c.lenSqr() < r * r;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		Vector2f dist = point.sub(center);
		return dist.lenSqr() < radius*radius;
	}
	
}
