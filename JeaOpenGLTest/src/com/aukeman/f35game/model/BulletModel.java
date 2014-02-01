package com.aukeman.f35game.model;

public class BulletModel extends AbstractModel {

	public static final float MAXIMUM_SPEED_PPS = 120.0f;
	
	private boolean mActive;
	
	public BulletModel(float height, float width) {
		super(height, width);
		
		mActive = false;
	}
	
	public boolean isActive(){
		return mActive;
	}
	
	@Override
	public void update(float frameLengthSeconds) {

		if (mActive){
			this.moveTo(getTop() - frameLengthSeconds*MAXIMUM_SPEED_PPS , getLeft());

			if ( this.getTop() < 0 - this.getHeight() ){
				mActive = false;
			}
		}
	}
	
}
