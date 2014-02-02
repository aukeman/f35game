package com.aukeman.f35game.model;

import com.aukeman.f35game.model.interfaces.IUpdatable;

public abstract class AbstractModel implements IUpdatable {

	private float mTop;
	
	private float mLeft;
	
	private float mHeight;
	
	private float mWidth;
	
	public float getTop(){
		return mTop;
	}
	
	public AbstractModel(float height, float width){
		this.mTop = 0.0f;
		this.mLeft = 0.0f;
		
		this.mHeight = height;
		this.mWidth = width;
	}
	
	public float getLeft(){
		return mLeft;
	}
	
	public float getWidth(){
		return mWidth;
	}
	
	public float getHeight(){
		return mHeight;
	}
	
	public void moveTo(float top, float left){
		this.mTop = top;
		this.mLeft = left;
	}
	
	@Override
	public void update(float frameLengthSeconds) {
		/* no-op */
	}

}
