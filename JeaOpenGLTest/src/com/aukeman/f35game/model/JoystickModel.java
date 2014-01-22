package com.aukeman.f35game.model;

public class JoystickModel {

	private float mInitialX;
	private float mInitialY;
	
	private float mCurrentX;
	private float mCurrentY;
	
	private float mTop;
	private float mLeft; 
	
	private float mWidth;
	private float mHeight;
	
	private boolean mDown;
	
	public JoystickModel(float top, float left, float height, float width){

		mTop = top;
		mLeft = left;
		
		mWidth = width;
		mHeight = height;
	}
	
	public float getX(){
		return Math.min( 1, Math.max( -1, (mCurrentX - mInitialX)/(0.5f*mWidth) ) );
	}
	
	public float getY(){
		return Math.min( 1, Math.max( -1, (mCurrentY - mInitialY)/(0.5f*mHeight) ) );
	}

	public void handleDown(float x, float y){
		
		if ( !mDown && 
		     mLeft <= x && x <= mLeft + mWidth &&
		     mTop <= y && y <= mTop + mHeight){
			
			mInitialX = mCurrentX = x;
			mInitialY = mCurrentY = y;
			
			mDown = true;
		}
	}

	public void handleMove(float x, float y){
		
		if ( mDown ){
			mCurrentX = x;
			mCurrentY = y;
		}
	}

	public void handleUp(float x, float y){
		if ( mDown ){
			initialize();
		}
	}
	
	private void initialize(){
		
		mInitialX = 0.0f;
		mInitialY = 0.0f;
		
		mCurrentX = 0.0f;
		mCurrentY = 0.0f;

		mDown = false;
	}
	
}
