package com.aukeman.f35game.model;

import java.security.acl.Owner;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.Pool;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.view.BulletView;

public class BadguyModel extends AbstractModel {

	private float mHeading;
	
	private float mPitch;
	
	private float mRoll;
	
	private long mStartTime;
	
	private float mStartTop;
	
	private float mStartLeft;
	
	private Pool<BulletView> mBulletPool;
	
	private AbstractModel mOwnshipModel;
	
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
	
	public void activate(IFrameInfo frameInfo, float startTop, float startLeft, IPath path, AbstractModel ownshipModel, Pool<BulletView> bulletPool){
		mStartTime = frameInfo.getTopOfFrame();
		mStartTop = startTop;
		mStartLeft = startLeft;
		mPath = path;
		
		mBulletPool = bulletPool;
		mOwnshipModel = ownshipModel;
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

			boolean shoot = mPath.getShoot(mStartTime, mStartLeft, mStartTop, frameInfo);

			if ( shoot ){
				BulletView bullet = mBulletPool.getNextAvailable();
				
				if ( bullet != null ){
					bullet.getModel().activate(getLeft(), getTop(), mOwnshipModel.getLeft(), mOwnshipModel.getTop());
				}
			}
			
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
