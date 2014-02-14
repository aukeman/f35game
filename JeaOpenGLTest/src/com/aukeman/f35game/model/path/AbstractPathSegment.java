package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public abstract class AbstractPathSegment implements IPathSegment {

	private long mDuration;
	
	private float mEndX;
	private float mEndY;
	
	abstract public float calculateXAtPercentComplete(float percent);
	
	abstract public float calculateYAtPercentComplete(float percent);
	
	protected void initialize( float endX, float endY, long duration ){
		this.mEndX = endX;
		this.mEndY = endY;
		this.mDuration = duration;
	}
	
	public float getEndX(){
		return mEndX;
	}
	
	public float getEndY(){
		return mEndY;
	}
	
	@Override
	public float getX(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		float percentComplete = getPercentComplete(startTime, frameInfo);
		
		if ( percentComplete < 0.0f ){
			return startX;
		}
		if ( percentComplete < 1.0f ){
			return calculateXAtPercentComplete(percentComplete) + startX;
		}
		else{
			return getFinalX(startX);
		}
	}

	@Override
	public float getY(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		float percentComplete = getPercentComplete(startTime, frameInfo);
		
		if ( percentComplete < 1.0f ){
			return calculateYAtPercentComplete(percentComplete) + startY;
		}
		else{
			return getFinalX(startY);
		}
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
	
	public float getPercentComplete(long startTime, IFrameInfo frameInfo){
		return (float)(frameInfo.getTopOfFrame() - startTime) / mDuration;
	}

}
