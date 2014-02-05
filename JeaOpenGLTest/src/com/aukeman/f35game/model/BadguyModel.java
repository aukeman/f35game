package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;

public class BadguyModel extends AbstractModel {

	private float mHeading;
	
	private float mPitch;
	
	private float mRoll;
	
	private long mStartTime;
	
	private float mStartTop;
	
	private float mStartLeft;
	
	private IPath mPath;
	
	public BadguyModel(){
		super(0.0f, 0.0f);
		
		mHeading = 0.0f;
		mPitch = 0.0f;
		mRoll = 0.0f;
		
		mStartTime = 0;
		mStartTop = 0.0f;
		mStartLeft = 0.0f;
		
		mPath = null;
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
	
	public void activate(IFrameInfo frameInfo, float startTop, float startLeft, IPath path){
		mStartTime = frameInfo.getTopOfFrame();
		mStartTop = startTop;
		mStartLeft = startLeft;
		mPath = path;
	}
	
	public boolean isActive(){
		return mPath != null;
	}

	@Override
	public void update(IFrameInfo frameInfo) {

		if ( isActive() ){
			float top = mPath.getY(mStartTime, mStartLeft, mStartTop, frameInfo);
			float left = mPath.getX(mStartTime, mStartLeft, mStartTop, frameInfo);
			
			moveTo(top, left);
			
			mHeading = mPath.getHeading(mStartTime, mStartLeft, mStartTop, frameInfo);
			mPitch = mPath.getPitch(mStartTime, mStartLeft, mStartTop, frameInfo);
			mRoll = mPath.getRoll(mStartTime, mStartLeft, mStartTop, frameInfo);
			
			if ( mPath.isComplete(mStartTime, mStartLeft, mStartTop, frameInfo) ){
				mStartTime = 0;
				mStartTop = 0.0f;
				mStartLeft = 0.0f;
				mPath = null;
			}
		}
	}

	
	
	
}
