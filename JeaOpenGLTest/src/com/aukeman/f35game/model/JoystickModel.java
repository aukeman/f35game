package com.aukeman.f35game.model;

import com.aukeman.f35game.view.F35GameGLSurfaceView;

import android.util.Log;

public class JoystickModel {

	private static final String VIEW_LOG_TAG = JoystickModel.class.getName();
	
	private float mInitialX;
	private float mInitialY;
	
	private float mCurrentX;
	private float mCurrentY;
	
	private float mX;
	private float mY;
	
	private float mTop;
	private float mLeft; 
	
	private float mWidth;
	private float mHeight;
	
	private boolean mDown;
	
	private int mPointerIndex;
	
	public JoystickModel(float top, float left, float height, float width){

		mTop = top;
		mLeft = left;
		
		mWidth = width;
		mHeight = height;
		
		initialize();
	}
	
	public float getX(){
		return mX;
	}
	
	public float getY(){
		return mY;
	}

	public void handleDown(float x, float y, int pointerIndex){
		
		Log.d(VIEW_LOG_TAG, String.format("handle down #%d: %f, %f", pointerIndex, x, y));
		
		if ( !mDown && 
		     mLeft <= x && x <= mLeft + mWidth &&
		     mTop <= y && y <= mTop + mHeight){
			
			mInitialX = mCurrentX = x;
			mInitialY = mCurrentY = y;
			
			mDown = true;
			
			mPointerIndex = pointerIndex;
			
			updateAxes();
		}
		else if ( mDown && 
				  pointerIndex <= mPointerIndex ){
			mPointerIndex += 1;
		}
	}

	public void handleMove(float x, float y, int pointerIndex){
		
		Log.d(VIEW_LOG_TAG, String.format("handle move #%d: %f, %f", pointerIndex, x, y));

		if ( mDown &&
			 mPointerIndex == pointerIndex ){
			mCurrentX = x;
			mCurrentY = y;
			
			updateAxes();
		}
	}

	public void handleUp(float x, float y, int pointerIndex){

		Log.d(VIEW_LOG_TAG, String.format("handle up #%d: %f, %f", pointerIndex, x, y));
		
		if ( mDown &&
			 mPointerIndex == pointerIndex ){
			initialize();
		}
		else if ( mDown && 
				  pointerIndex < mPointerIndex ){
			mPointerIndex -= 1;
		}
	}
	
	private void initialize(){
		
		mInitialX = 0.0f;
		mInitialY = 0.0f;
		
		mCurrentX = 0.0f;
		mCurrentY = 0.0f;

		mX = 0.0f;
		mY = 0.0f;
		
		mPointerIndex = -1;
		
		mDown = false;
	}
	
	private void updateAxes(){
		mX = Math.min( 1, Math.max( -1, (mCurrentX - mInitialX)/(0.5f*mWidth) ) );
		mY = Math.min( 1, Math.max( -1, (mCurrentY - mInitialY)/(0.5f*mHeight) ) );
	}
	
}
