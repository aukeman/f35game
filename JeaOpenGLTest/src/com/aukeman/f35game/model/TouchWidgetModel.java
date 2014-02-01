package com.aukeman.f35game.model;


public class TouchWidgetModel extends AbstractModel{

	private float mInitialX;
	private float mInitialY;
	
	private float mCurrentX;
	private float mCurrentY;
	
	private boolean mDown;
	
	private boolean mDownLastFrame;
	
	private int mPointerIndex;

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
		super(height,width);
		
		this.moveTo(top, left);

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
		     getLeft() <= x && x <= getLeft() + getWidth() &&
		     getTop() <= y && y <= getTop() + getHeight()){
			
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
