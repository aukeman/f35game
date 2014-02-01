package com.aukeman.f35game.model;


public class TouchWidgetModel implements IUpdatable{

	private float mTop;
	private float mLeft; 
	
	private float mWidth;
	private float mHeight;
	
	private float mInitialX;
	private float mInitialY;
	
	private float mCurrentX;
	private float mCurrentY;
	
	private boolean mDown;
	
	private boolean mDownLastFrame;
	
	private int mPointerIndex;

	public float getTop(){
		return mTop;
	}
	
	public float getLeft(){
		return mLeft;
	}
	
	public void moveTo( float top, float left ){
		this.mTop = top;
		this.mLeft = left;
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
	
	public boolean pressed(){
		return mDown && !mDownLastFrame;
	}
	
	public boolean released(){
		return !mDown && mDownLastFrame;
	}
	
	public int getPointerIndex(){
		return mPointerIndex;
	}
	
	public TouchWidgetModel(float top, float left, float height, float width){
		mTop = top;
		mLeft = left;
		
		mWidth = width;
		mHeight = height;
		
		mDownLastFrame = false;
		
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
	
	public void update(float frameLengthSeconds){
		mDownLastFrame = mDown;
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
