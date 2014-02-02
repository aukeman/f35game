package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;

public class BadguyModel extends AbstractModel {

	private boolean mTurnToHeading;

	private float mHeading;
	
	private long mStartTime;
	
	public BadguyModel(float height, float width, boolean turnToHeading){
		super(height, width);
		
		mTurnToHeading = turnToHeading;
		mHeading = 0.0f;
		mStartTime = 0;
	}

	@Override
	public void update(IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
