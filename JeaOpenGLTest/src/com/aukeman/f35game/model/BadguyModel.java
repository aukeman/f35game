package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;

public class BadguyModel extends AbstractModel {

	private float mHeading;
	
	private float mPitch;
	
	private float mRoll;
	
	private long mStartTime;
	
	private IPath mPath;
	
	public BadguyModel(){
		super(0.0f, 0.0f);
		
		mHeading = 0.0f;
		mPitch = 0.0f;
		mRoll = 0.0f;
		
		mStartTime = 0;
	}
	
	public float getHeading(){
		return mHeading;
	}
	
	public float getPitch(){
		return mPitch;
	}
	
	public float getRoll(){
		return mRoll;
	}
	
	public void activate(IFrameInfo frameInfo, IPath path){
		mStartTime = frameInfo.getTopOfFrame();
		mPath = path;
	}
	
	public boolean isActive(){
		return mPath != null;
	}

	@Override
	public void update(IFrameInfo frameInfo) {

		if ( isActive() ){
			float top = mPath.getTop(mStartTime, frameInfo);
			float left = mPath.getLeft(mStartTime, frameInfo);
			
			moveTo(top, left);
			
			mHeading = mPath.getHeading(mStartTime, frameInfo);
			mPitch = mPath.getPitch(mStartTime, frameInfo);
			mRoll = mPath.getRoll(mStartTime, frameInfo);
			
			if ( mPath.isComplete(mStartTime, frameInfo) ){
				mStartTime = 0;
				mPath = null;
			}
		}
	}

	
	
	
}
