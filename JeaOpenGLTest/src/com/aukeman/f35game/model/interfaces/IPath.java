package com.aukeman.f35game.model.interfaces;

import com.aukeman.f35game.IFrameInfo;

public interface IPath {

	public float getLeft(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getTop(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getHeading(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getPitch(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getRoll(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public boolean getShot(long startTime, float startX, float startY, IFrameInfo frameInfo); 
	
	public boolean isComplete(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public long getDuration();
	
	public float getFinalX(float startX);
	
	public float getFinalY(float startY);
	
}
