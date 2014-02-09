package com.aukeman.f35game.model;

import java.util.ArrayList;
import java.util.List;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.view.interfaces.IViewport;

public class F35Model extends AbstractModel {

	public static final float MAXIMUM_SPEED_PPS = 60.0f;
	
	public static final float ROLL_RATE_DPS = 120.0f;
	
	private JoystickModel mJoystick;
	
	private TouchWidgetModel mButton;

	private IViewport mViewport;
	
	private float mRollAngle;
	
	private List<BulletModel> mBullets;
	
	public F35Model(float height, float width){
		super(height,width);
		
		this.mJoystick = null;
		this.mButton = null;
		
		mRollAngle = 0.0f;
		
		this.mBullets = new ArrayList<BulletModel>();
		
		this.getBoundingBoxes().clear();
		this.getBoundingBoxes().add(new BoundingBox(0, 10, 48, 12));
	}
	
	public void setControls(JoystickModel joystick, 
							TouchWidgetModel button ){
		this.mJoystick = joystick;
		this.mButton = button;
	}
	
	public void setViewport(IViewport viewport){
		this.mViewport = viewport;
	}
	
	public float getRollAngle(){
		return mRollAngle;
	}
	
	public void addBullet(BulletModel bullet){
		mBullets.add(bullet);
	}
	
	public void update(IFrameInfo frameInfo){
		
		float left = this.getLeft();
		float top = this.getTop();
		
		float distance = MAXIMUM_SPEED_PPS*frameInfo.getLengthOfLastFrameInSeconds();
		float roll = ROLL_RATE_DPS*frameInfo.getLengthOfLastFrameInSeconds();
		
		if (mJoystick.getAxisX() < -0.25f){
			left -= distance;
			
			if ( -60.0f < mRollAngle){
				mRollAngle -= roll;
			}
		}
		else if (0.25f < mJoystick.getAxisX()){
			left += distance;
			
			if ( mRollAngle < 60.0f ){
				mRollAngle += roll;
			}
		}
		else{
			
			if ( 0 < mRollAngle ){
				mRollAngle -= roll;
			}
			else{
				mRollAngle += roll;
			}
		}
		
		if ( mJoystick.getAxisY() < -0.25f ){
			top -= distance;
		}
		else if ( 0.25f < mJoystick.getAxisY()){
			top += distance;
		}

		if ( top < mViewport.getTop() ){
			top = mViewport.getTop();
		}
		else if ( mViewport.getBottom() < top + getHeight() ){
			top = mViewport.getBottom() - getHeight();
		}
		
		if ( left < mViewport.getLeft() ){
			left = mViewport.getLeft();
		}
		else if ( mViewport.getRight() < left + getWidth() ){
			left = mViewport.getRight() - getWidth();
		}
		
		float xDistance=left-this.getLeft();
		float yDistance=top-this.getTop();
		
		for ( BoundingBox bbox : getBoundingBoxes() ){
			bbox.setTop(bbox.getTop() + yDistance);
			bbox.setLeft(bbox.getLeft() + xDistance);
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
