package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public class StraightSegment implements IPathSegment{

	private float mStartX;
	
	private float mStartY;
	
	private float mEndX;
	
	private float mEndY;
	
	private float mSpeed;
	
	private long mDuration;
	
	private float mHeading;
	
	public StraightSegment(float heading, float speed, float distance){
		this.mStartX = 0.0f;
		this.mStartY = 0.0f;
		
		this.mSpeed = speed;
		this.mHeading = heading;
		
		this.mDuration = (long)(distance / mSpeed) * 1000;
		
		this.mEndX = distance *  (float)Math.sin((Math.PI/180.0) * mHeading);
		this.mEndY = distance * -(float)Math.cos((Math.PI/180.0) * mHeading);
	}
	
	@Override
	public float getX(long startTime, 
			             float startX, 
			             float startY,
			             IFrameInfo frameInfo) {

		float percentComplete = getPercentComplete(startTime, frameInfo);
		
		if ( percentComplete < 1.0f ){
			return percentComplete * (mEndX - mStartX) + startX;
		}
		else{
			return mEndX;
		}
	}

	@Override
	public float getY(long startTime, 
			           float startX, 
			           float startY,
			           IFrameInfo frameInfo) {

		float percentComplete = getPercentComplete(startTime, frameInfo);
		
		if ( percentComplete < 1.0f ){
			return percentComplete * (mEndY - mStartY) + startY;
		}
		else{
			return mEndY;
		}
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

	@Override
	public boolean isComplete(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return (1.0 <= getPercentComplete(startTime, frameInfo));
	}

	@Override
	public long getDuration() {
		return mDuration;
	}

	@Override
	public float getFinalX(float startX) {
		return mEndX + startX;
	}

	@Override
	public float getFinalY(float startY) {
		return mEndY + startY;
	}

	private float getPercentComplete(long startTime, IFrameInfo frameInfo){
		return (float)(frameInfo.getTopOfFrame() - startTime) / mDuration;
	}
}
