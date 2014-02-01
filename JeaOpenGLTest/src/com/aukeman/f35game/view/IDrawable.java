package com.aukeman.f35game.view;

public interface IDrawable {

	public void draw(float[] mvpMatrix);
	
	public float getTop();
	
	public float getLeft();
	
	public float getWidth();
	
	public float getHeight();
	
	public float getPriority();
	
	public void moveTo(float top, float left);
	
	public void setPriority(float priority);
}
