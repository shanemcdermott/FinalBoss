package javagames.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameLog 
{
	public ArrayList<String> messages;
	public Color color;
	public String name;
	
	public GameLog()
	{
		this("GameLog");
	}
	
	public GameLog(String name)
	{
		this(name, Color.WHITE);
	}
	
	public GameLog(String name, Color color)
	{
		this.color = color;
		this.name = name;
		messages = new ArrayList<String>();
	}
	
	public void log(String text)
	{
		messages.add(text);
	}
	
	public void add(String text)
	{
		messages.add(text);
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.setColor(color);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		for(int i = messages.size()-1; i >= 0; i--, y+=10)
		{
			g.drawString(String.format("%s: %s", name, messages.get(i)),x,y);
		}	
	}
	
	
	
	@Override
	public String toString()
	{
		String str = messages.toString();
		return str;
	}
}
