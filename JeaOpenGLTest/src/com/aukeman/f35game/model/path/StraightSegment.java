package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public class StraightSegment extends AbstractPathSegment{

	private float mSpeed;
	
	private float mHeading;
	
	public StraightSegment(float heading, float speed, float distance){
		this.mSpeed = speed;
		this.mHeading = heading;
		
		long duration = ( (long)(distance / mSpeed) * 1000 );
		
		float endX = ( distance *  (float)Math.sin((Math.PI/180.0) * mHeading) );
		float endY = ( distance * -(float)Math.cos((Math.PI/180.0) * mHeading) );

		initialize( endX, endY, duration );
	}
	
	public float calculateXAtPercentComplete(float percentComplete){
		return percentComplete * getEndX();
	}
	
	public float calculateYAtPercentComplete(float percentComplete){
		return percentComplete * getEndY();
	}
	
	@Override
	public float getHeading(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return mHeading;
	}

	@Override
	public float getPitch(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public float getRoll(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public boolean getShoot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return false;
	}
}
