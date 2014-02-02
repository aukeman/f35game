package com.aukeman.f35game.model.interfaces;

import com.aukeman.f35game.IFrameInfo;

public interface IPath {

	public float getX(IFrameInfo frameInfo);
	
	public float getY(IFrameInfo frameInfo);
	
	public float getHeading(IFrameInfo frameInfo);
	
	public float getPitch(IFrameInfo frameInfo);
	
	public float getRoll(IFrameInfo frameInfo);
	
	public boolean getShot(IFrameInfo frameInfo); 
	
	
}
