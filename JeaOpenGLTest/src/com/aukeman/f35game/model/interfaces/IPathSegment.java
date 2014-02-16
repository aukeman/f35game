package com.aukeman.f35game.model.interfaces;

import com.aukeman.f35game.IFrameInfo;


public interface IPathSegment extends IPath{

	public long getDuration();
	
	public float getFinalX(float startX);
	
	public float getFinalY(float startY);
	
	public boolean isCompleteSinceLastFrame(long startTime, float startX, float startY, IFrameInfo frameInfo);

}
