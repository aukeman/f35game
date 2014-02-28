package com.aukeman.f35game.model.interfaces;

import com.aukeman.f35game.IFrameInfo;

public interface IPath {

	public float getX(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getY(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getHeading(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getPitch(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public float getRoll(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	public boolean getShoot(long startTime, float startX, float startY, IFrameInfo frameInfo); 
	
	public boolean isComplete(long startTime, float startX, float startY, IFrameInfo frameInfo);
	
	
}
