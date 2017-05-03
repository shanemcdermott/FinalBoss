package javagames.g2d;

import java.awt.image.BufferedImage;

public class Animation 
{

	
	protected BufferedImage image;
	protected int			frame;
	protected int			numFrames;
	protected float			frameLength;

	protected int			startX;
	protected int			startY;
	protected int			width;
	protected int 			height;

	protected float			time;
	
	public Animation(BufferedImage image, int numFrames, float frameLength, int startX, int startY, int width, int height) 
	{
		this.image = image;
		this.numFrames = numFrames;
		this.frameLength = frameLength;
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		time = 0.f;
		frame = 0;
	}

	public void start()
	{
		time = 0.f;
		frame = 0;
	}
	
	public void update(float deltaTime)
	{
		if(numFrames>0)
		{
			time += deltaTime;
			if(time >= frameLength)
			{
				frame = (frame + 1) % numFrames;
				time -= frameLength;
			}
		}
	}
	
	public BufferedImage getCurrentImage()
	{
		return image.getSubimage(startX + frame * width, startY, width, height);
	}
	
}
