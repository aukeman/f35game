package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;

public class TurnSegment extends AbstractPathSegment {

	private float mStartHeading;
	private float mTurnAmount;
	private float mRadius;
	private float mSpeed;
	
	private float mRollAngle;
	
	
	private static final int COORDINATES_TABLE_LENGTH = 16;
	
	private float mXCoordinates[] = new float[COORDINATES_TABLE_LENGTH];
	private float mYCoordinates[] = new float[COORDINATES_TABLE_LENGTH];
	
	public TurnSegment( float startHeading, float turnAmount, float radius, float speed ){
		this.mStartHeading = startHeading;
		this.mTurnAmount = turnAmount;
		this.mRadius = radius;
		this.mSpeed = speed;
		
		float turnAmountRadians = (float)(mTurnAmount*Math.PI/180);
		float startHeadingRadians = (float)(mStartHeading*Math.PI/180);
		
		long duration = (long)((turnAmountRadians)*mRadius / mSpeed * 1000);
		
		for ( int idx = 0; idx < COORDINATES_TABLE_LENGTH; ++idx ){
			
			float percent = idx/(float)(COORDINATES_TABLE_LENGTH-1);
			
			float intermediateTurnAmountRadians = turnAmountRadians*percent;
			
			float tempX = -radius*(float)Math.cos(intermediateTurnAmountRadians/(2*Math.PI));
			float tempY = -radius*(float)Math.sin(intermediateTurnAmountRadians/(2*Math.PI));
			
			// rotate x and y to account for initial heading
			float x = (float)(tempX*Math.cos(startHeadingRadians) + tempY*Math.sin(startHeadingRadians));
			float y = (float)(tempX*Math.sin(startHeadingRadians) - tempY*Math.cos(startHeadingRadians));

			mXCoordinates[idx] = x;
			mYCoordinates[idx] = y;
		}

		initialize( mXCoordinates[COORDINATES_TABLE_LENGTH-1], 
				    mYCoordinates[COORDINATES_TABLE_LENGTH-1], 
				    duration ); 
		
		
		float centripetal_acceleration = mSpeed*mSpeed / mRadius;
		
		mRollAngle = (float)(Math.atan(centripetal_acceleration)*180.0/Math.PI); 
		
		if ( turnAmount < 0.0 ){
			mRollAngle = -mRollAngle;
		}
	}
	
	@Override
	public float calculateXAtPercentComplete(float percent) {

		float fractionalIndex = COORDINATES_TABLE_LENGTH*percent;
		
		int idx = (int)fractionalIndex;
		float remainder = fractionalIndex-idx;
				
		return remainder*(mXCoordinates[idx+1] - mXCoordinates[idx]) + mXCoordinates[idx];		
	}
	
	@Override
	public float calculateYAtPercentComplete(float percent) {
		float fractionalIndex = COORDINATES_TABLE_LENGTH*percent;
		
		int idx = (int)fractionalIndex;
		float remainder = fractionalIndex-idx;
				
		return remainder*(mYCoordinates[idx+1] - mYCoordinates[idx]) + mYCoordinates[idx];		
	}
	
	@Override
	public float getHeading(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		
		float percentComplete = getPercentComplete(startTime, frameInfo);
		
		return (percentComplete*mTurnAmount) + mStartHeading; 
	}

	@Override
	public float getPitch(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public float getRoll(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return mRollAngle;
	}

	@Override
	public boolean getShoot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return false;
	}
}
