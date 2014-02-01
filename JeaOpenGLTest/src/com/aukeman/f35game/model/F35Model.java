package com.aukeman.f35game.model;

public class F35Model implements IUpdatable {

	public static final float MAXIMUM_SPEED_PPS = 60.0f;
	
	public static final int STATIONARY = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	private float mTop;	
	private float mLeft;

	private float mHeight;
	private float mWidth;
	
	private int mDirection;
	
	public F35Model(float height, float width){
		this.mTop = 0.0f;
		this.mLeft = 0.0f;
		
		this.mHeight = height;
		this.mWidth = width;

		this.mDirection = STATIONARY;
	}
	
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
	
	public int getDirection(){
		return mDirection;
	}
	
	public void setDirection(int direction){
		this.mDirection = direction;
	}
	
	public void moveTo(float top, float left){
		this.mTop = top;
		this.mLeft = left;
	}
	
	public void update(float frameLengthSeconds){
		
		switch (this.mDirection){
		case UP: this.mTop -= MAXIMUM_SPEED_PPS*frameLengthSeconds; break;
		case DOWN: this.mTop += MAXIMUM_SPEED_PPS*frameLengthSeconds; break;
		case LEFT: this.mLeft -= MAXIMUM_SPEED_PPS*frameLengthSeconds; break;
		case RIGHT: this.mLeft += MAXIMUM_SPEED_PPS*frameLengthSeconds; break;
		}
	}
	
}
