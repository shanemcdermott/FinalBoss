package javagames.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javagames.util.Matrix3x3f;
import javagames.g2d.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingShape;

public class GameObject 
{
	
	protected String name;
	protected Sprite sprite;
	protected ArrayList<String> tags;
	
	protected BoundingShape bounds;
	
	protected Matrix3x3f transform;
	protected Vector2f scale;
	protected float rotation;
	protected Vector2f position;
	
	public GameObject(String name, Sprite sprite)
	{
		this.name = name;
		this.sprite = sprite;
		bounds = new BoundingCircle(1.f);
		transform = Matrix3x3f.identity();
		scale = new Vector2f(1.f,1.f);
		rotation = 0.f;
		position = new Vector2f();
	}
	
	public void addTag(String...cTags)
	{
		for(String s : cTags)
		{
			this.tags.add(s);
		}
	}
	
	public ArrayList<String> getTags()
	{
		return new ArrayList<String>(tags);
	}
	
	public void setSprite(Sprite sprite) 
	{
		this.sprite = sprite;
	}

	public Sprite getSprite() 
	{
		return sprite;
	}
	
	public void update(float deltaTime)
	{
		updateTransform();
		bounds.setPosition(transform.mul(position));
	}

	public void setPosition(Vector2f position)
	{
		this.position = new Vector2f(position);
	}
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public Matrix3x3f getWorldTransform()
	{
		Matrix3x3f wTransform = Matrix3x3f.scale(scale);
		wTransform = wTransform.mul(Matrix3x3f.rotate(rotation));
		wTransform = wTransform.mul(Matrix3x3f.translate(position));
		return wTransform;
	}
	
	private void updateTransform()
	{
		transform = getWorldTransform();
	}
	
	public void draw(Graphics2D g, Matrix3x3f view)
	{
		sprite.render(g, view,position,rotation);
	}
	
	public void draw(Graphics2D g, Matrix3x3f view, Vector2f posOffset)
	{
		sprite.render(g, view, position.sub(posOffset), rotation);
	}
}
