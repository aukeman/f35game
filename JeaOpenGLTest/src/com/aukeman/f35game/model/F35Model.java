package com.aukeman.f35game.model;

import java.util.LinkedList;
import java.util.List;

public class F35Model extends AbstractModel {

	public static final float MAXIMUM_SPEED_PPS = 60.0f;
	
	private JoystickModel mJoystick;
	
	private TouchWidgetModel mButton;
	
	private List<BulletModel> mBullets;
	
	public F35Model(float height, float width){
		super(height,width);
		
		this.mJoystick = null;
		this.mButton = null;
		
		this.mBullets = new LinkedList<BulletModel>();
	}
	
	public void setControls(JoystickModel joystick, 
							TouchWidgetModel button ){
		this.mJoystick = joystick;
		this.mButton = button;
	}
	
	public void addBullet(BulletModel bullet){
		mBullets.add(bullet);
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
		
		if ( mButton.pressed() ){
			for ( BulletModel bullet : mBullets ){

				if ( !bullet.isActive() ){
					bullet.moveTo(top - bullet.getHeight(), left + getWidth()/2);
					bullet.activate();
					break;
				}
			}
		}
	}
	
}
