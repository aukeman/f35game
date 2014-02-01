package com.aukeman.f35game.model;

public class F35Model implements IUpdatable {

	public static final float MAXIMUM_SPEED_PPS = 60.0f;
	
	private float mTop;	
	private float mLeft;

	private float mHeight;
	private float mWidth;
	
	private JoystickModel mJoystick;
	
	private TouchWidgetModel mButton;
	
	public F35Model(float height, float width){
		this.mTop = 0.0f;
		this.mLeft = 0.0f;
		
		this.mHeight = height;
		this.mWidth = width;

		this.mJoystick = null;
		this.mButton = null;
	}
	
	public float getTop(){
		return mTop;
	}
	
	public float getLeft(){
		return mLeft;
	}
	
	public float getWidth(){
		return mWidth;
	}
	
	public float getHeight(){
		return mHeight;
	}
	
	public void moveTo(float top, float left){
		this.mTop = top;
		this.mLeft = left;
	}
	
	public void setControls(JoystickModel joystick, 
							TouchWidgetModel button ){
		this.mJoystick = joystick;
		this.mButton = button;
	}
	
	public void update(float frameLengthSeconds){
		
		if (mJoystick.getAxisX() < -0.25f){
			this.mLeft -= MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		else if (0.25f < mJoystick.getAxisX()){
			this.mLeft += MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		
		if ( mJoystick.getAxisY() < -0.25f ){
			this.mTop -= MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		else if ( 0.25f < mJoystick.getAxisY()){
			this.mTop += MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
	}
	
}
