package com.aukeman.f35game.model;

public class F35Model extends AbstractModel {

	public static final float MAXIMUM_SPEED_PPS = 60.0f;
	
	private JoystickModel mJoystick;
	
	private TouchWidgetModel mButton;
	
	public F35Model(float height, float width){
		super(height,width);
		
		this.mJoystick = null;
		this.mButton = null;
	}
	
	public void setControls(JoystickModel joystick, 
							TouchWidgetModel button ){
		this.mJoystick = joystick;
		this.mButton = button;
	}
	
	public void update(float frameLengthSeconds){
		
		float left = this.getLeft();
		float top = this.getTop();
		
		if (mJoystick.getAxisX() < -0.25f){
			left -= MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		else if (0.25f < mJoystick.getAxisX()){
			left += MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		
		if ( mJoystick.getAxisY() < -0.25f ){
			top -= MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		else if ( 0.25f < mJoystick.getAxisY()){
			top += MAXIMUM_SPEED_PPS*frameLengthSeconds;
		}
		
		this.moveTo(top, left);
	}
	
}
