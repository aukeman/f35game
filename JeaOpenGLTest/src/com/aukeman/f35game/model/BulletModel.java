package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.view.interfaces.IViewport;

public class BulletModel extends AbstractModel {

	private static final float INACTIVE_SPEED = 0.0f;
	
	private float mSpeed;
	
	private float mSpeedX;
	
	private float mSpeedY;
	
	private IViewport mViewport;
	
	public BulletModel(float height, float width, float speed) {
		super(height, width);

		this.mSpeed = speed;
		
		reinitialize();
	}

	public boolean isActive(){
		return (mSpeedX != INACTIVE_SPEED || 
				mSpeedY != INACTIVE_SPEED);
	}
	
	public void activate(float currentX, float currentY, float heading){
		
		float targetX = currentX + (float)Math.sin( heading*Math.PI/180 );
		float targetY = currentY - (float)Math.cos( heading*Math.PI/180 );
		
		activate( currentX, currentY, targetX, targetY );
	}
	
	public void activate(float currentX, 
			             float currentY, 
			             float targetX, 
			             float targetY){
		
		moveTo(currentY, currentX);

		mSpeedX = targetX - currentX;
		mSpeedY = targetY - currentY;

		float distanceToTarget = (float)Math.sqrt(mSpeedX*mSpeedX + mSpeedY*mSpeedY);
		
		mSpeedX /= distanceToTarget;
		mSpeedY /= distanceToTarget;
		
		mSpeedX *= mSpeed;
		mSpeedY *= mSpeed;
	}
	
	public void setViewport(IViewport viewport){
		mViewport = viewport;
	}
	
	@Override
	public void update(IFrameInfo frameInfo) {
		
		if ( isActive() ){
			
			this.moveTo( getTop() + mSpeedY*frameInfo.getLengthOfLastFrameInSeconds(),
					     getLeft() + mSpeedX*frameInfo.getLengthOfLastFrameInSeconds() );
			
			if ( !getBoundingBox().overlaps(mViewport) ){
				
				reinitialize();
			}
		}
	}
	
	private void reinitialize(){
		mSpeedX = INACTIVE_SPEED;
		mSpeedY = INACTIVE_SPEED;
	}
	
}

