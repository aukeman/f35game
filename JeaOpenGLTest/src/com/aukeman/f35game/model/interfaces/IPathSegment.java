package com.aukeman.f35game.model.interfaces;


public interface IPathSegment extends IPath{

	public long getDuration();
	
	public float getFinalX(float startX);
	
	public float getFinalY(float startY);

}
