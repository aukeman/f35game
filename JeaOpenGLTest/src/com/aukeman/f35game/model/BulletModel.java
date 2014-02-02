package com.aukeman.f35game.model;

import com.aukeman.f35game.view.interfaces.IViewport;

public class BulletModel extends AbstractModel {

	public static final float MAXIMUM_SPEED_PPS = 240.0f;
	
	private boolean mActive;
	
	private IViewport mViewport;
	
	public BulletModel(float height, float width) {
		super(height, width);
		
		mActive = false;
	}
	
	public boolean isActive(){
		return mActive;
	}
	
	public void activate(){
		mActive = true;
	}
	
	public void setViewport(IViewport viewport){
		mViewport = viewport;
	}
	
	@Override
	public void update(float frameLengthSeconds) {

		if (mActive){
			this.moveTo(getTop() - frameLengthSeconds*MAXIMUM_SPEED_PPS , getLeft());

			if ( this.getTop() < mViewport.getTop() - this.getHeight() ){
				mActive = false;
			}
		}
	}
	
}
