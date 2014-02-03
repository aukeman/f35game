package com.aukeman.f35game.model.interfaces;

import com.aukeman.f35game.IFrameInfo;

public interface IPath {

	public float getLeft(long startTime, IFrameInfo frameInfo);
	
	public float getTop(long startTime, IFrameInfo frameInfo);
	
	public float getHeading(long startTime, IFrameInfo frameInfo);
	
	public float getPitch(long startTime, IFrameInfo frameInfo);
	
	public float getRoll(long startTime, IFrameInfo frameInfo);
	
	public boolean getShot(long startTime, IFrameInfo frameInfo); 
	
	public boolean isComplete(long startTime, IFrameInfo frameInfo);
	
}
