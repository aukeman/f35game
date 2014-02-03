package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;

public class AbstractPath implements IPath{

	
	public float getLeft(long startTime, IFrameInfo frameInfo){
		return 0.0f;
	}
	
	public float getTop(long startTime, IFrameInfo frameInfo){
		return 0.0f;
	}
	
	public float getHeading(long startTime, IFrameInfo frameInfo){
		return 0.0f;
	}

	@Override
	public float getPitch(long startTime, IFrameInfo frameInfo) {
		return 0.0f;
	}
	
	public float getRoll(long startTime, IFrameInfo frameInfo){
		return 0.0f;
	}

	@Override
	public boolean getShot(long startTime, IFrameInfo frameInfo) {
		return false;
	}
	
	public boolean isComplete(long startTime, IFrameInfo frameInfo){
		return false;
	}

}
