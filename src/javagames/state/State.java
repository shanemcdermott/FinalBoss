package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.util.StateFramework;
import javagames.util.Matrix3x3f;

public class State {
	
	protected StateController controller;
	protected StateFramework app;
	
	
	public void setController(StateController controller) {
		this.controller = controller;
		app = (StateFramework) controller.getAttribute("app");
	}

	protected StateController getController() {
		return controller;
	}

	public void enter() {
		
	}

	public void processInput(float delta) {
	}

	public void updateObjects(float delta) {
	}

	public void render(Graphics2D g, Matrix3x3f view) {
	}


	public void log(String str)
	{
		controller.log(str);
	}
	
	public void log(String str, Color clr)
	{
		controller.log(str,clr);
	}
	
	public void exit() {
		
	}
}