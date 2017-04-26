package javagames.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.state.LoadingState;
import javagames.state.StateController;
import javagames.state.TitleLoadingState;

public class StateFramework extends WindowFramework 
{

	private StateController controller;
	protected boolean bDrawDebug = false;
	
	public StateFramework()
	{
		appBorder = GameConstants.APP_BORDER;
		appWidth = GameConstants.APP_WIDTH;
		appHeight = GameConstants.APP_HEIGHT;
		appSleep = GameConstants.APP_SLEEP;
		appTitle = GameConstants.APP_TITLE;
		appWorldWidth = GameConstants.VIEW_WIDTH;
		appWorldHeight = GameConstants.VIEW_HEIGHT;
		appBorderScale = GameConstants.BORDER_SCALE;
		appDisableCursor = GameConstants.DISABLE_CURSOR;
		appMaintainRatio = GameConstants.MAINTAIN_RATIO;
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		controller = new StateController();
		controller.setAttribute("app", this);
		controller.setAttribute("keys", keyboard);
		controller.setAttribute("viewport", getViewportTransform());
		controller.setState(new TitleLoadingState());
	}
	
	public void shutDownGame()
	{
		shutDown();
	}
	
	@Override
	protected void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		controller.processInput(deltaTime);
	}
	
	@Override
	protected void updateObjects(float deltaTime)
	{
		controller.updateObjects(deltaTime);
	}

	@Override
	protected void render(Graphics g)
	{
		controller.render((Graphics2D) g, getViewportTransform());
		if(bDrawDebug)
			super.render(g);
	}
	
	public static void main(String[] args)
	{
		launchApp(new StateFramework());
	}
}
