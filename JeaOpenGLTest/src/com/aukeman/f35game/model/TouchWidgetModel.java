package com.aukeman.f35game.model;

import android.util.Log;

public class TouchWidgetModel {

	private float mTop;
	private float mLeft; 
	
	private float mWidth;
	private float mHeight;
	
	private float mInitialX;
	private float mInitialY;
	
	private float mCurrentX;
	private float mCurrentY;
	
	private boolean mDown;
	
	private int mPointerIndex;

	public float getTop(){
		return mTop;
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
	
	public float getDiffX(){
		return mCurrentX - mInitialX;
	}
	
	public float getDiffY(){
		return mCurrentY - mInitialY;
	}
	
	public boolean isDown(){
		return mDown;
	}
	
	public int getPointerIndex(){
		return mPointerIndex;
	}

	protected TouchWidgetModel(float top, float left, float height, float width){
		mTop = top;
		mLeft = left;
		
		mWidth = width;
		mHeight = height;
		
		reset();
	}
	
	protected void onDown(){
		
	}
	
	protected void onMove(){
		
	}
	
	protected void onUp(){
		
	}
	
	public void handleDown(float x, float y, int pointerIndex){
		
		if ( !mDown && 
		     mLeft <= x && x <= mLeft + mWidth &&
		     mTop <= y && y <= mTop + mHeight){
			
			mInitialX = mCurrentX = x;
			mInitialY = mCurrentY = y;
			
			mDown = true;
			
			mPointerIndex = pointerIndex;

			onDown();
		}
		else if ( mDown && 
				  pointerIndex <= mPointerIndex ){
			mPointerIndex += 1;
		}
	}

	public void handleMove(float x, float y, int pointerIndex){
		
		if ( mDown &&
			 mPointerIndex == pointerIndex ){
			mCurrentX = x;
			mCurrentY = y;
			
			onMove();
		}
	}

	public void handleUp(float x, float y, int pointerIndex){

		if ( mDown &&
			 mPointerIndex == pointerIndex ){
			
			onUp();
			
			reset();
		}
		else if ( mDown && 
				  pointerIndex < mPointerIndex ){
			mPointerIndex -= 1;
		}
	}
	
	private void reset(){
		mInitialX = 0.0f;
		mInitialY = 0.0f;
		
		mCurrentX = 0.0f;
		mCurrentY = 0.0f;
		
		mDown = false;
		
		mPointerIndex = -1;
	}
	
}
