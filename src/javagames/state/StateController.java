package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javagames.util.Matrix3x3f;

public class StateController 
{
	private Map<String, Color> taskLog;
	private Map<String, Object> attributes;
	private State currentState;

	public StateController() {
		attributes = Collections.synchronizedMap(new HashMap<String, Object>());
		taskLog = Collections.synchronizedMap(new HashMap<String, Color>());
	}

	public void setState(State newState) {
		if (currentState != null) {
			currentState.exit();
		}
		if (newState != null) {
			newState.setController(this);
			newState.enter();
		}
		currentState = newState;
	}

	public void processInput(float delta) {
		currentState.processInput(delta);
	}

	public void updateObjects(float delta) {
		currentState.updateObjects(delta);
	}

	public void render(Graphics2D g, Matrix3x3f view) {
		g.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
		);
		currentState.render(g, view);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public Object removeAttribute(String name) {
		return attributes.remove(name);
	}

	public void setAttribute(String name, Object attribute) {
		attributes.put(name, attribute);
	}

	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	public void log(String str)
	{
		taskLog.put(str, Color.WHITE);
	}
	
	public void log(String str, Color clr)
	{
		taskLog.put(str,clr);
	}
	
	public void drawLog(Graphics2D g)
	{
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		int y = 20;
		for (Map.Entry<String, Color> entry : taskLog.entrySet())
		{
			g.setColor(entry.getValue());
			g.drawString(entry.getKey(),10,y);
			y+=10;
		}

	}
	
}
