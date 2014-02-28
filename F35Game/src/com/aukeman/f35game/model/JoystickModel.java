package com.aukeman.f35game.model;


public class JoystickModel extends TouchWidgetModel{

	private float mAxisX;
	private float mAxisY;
	
	public JoystickModel(float top, float left, float height, float width){
		super(top, left, height, width);
		
		reset();
	}
	
	public float getAxisX(){
		return mAxisX;
	}
	
	public float getAxisY(){
		return mAxisY;
	}
	
	@Override
	protected void onDown() {
		updateAxes();
	}
	
	@Override
	protected void onMove() {
		updateAxes();
	}
	
	@Override
	protected void onUp() {
		reset();
	}

	private void reset(){
		mAxisX = 0.0f;
		mAxisY = 0.0f;
	}
	
	private void updateAxes(){
		mAxisX = Math.min( 1, Math.max( -1, getDiffX()/(0.5f*getWidth()) ) );
		mAxisY = Math.min( 1, Math.max( -1, getDiffY()/(0.5f*getHeight()) ) );
	}
	
}
