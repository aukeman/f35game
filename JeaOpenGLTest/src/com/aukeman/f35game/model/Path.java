package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;

public class Path implements IPath{

	public float getX(IFrameInfo frameInfo){
		return 0.0f;
	}
	
	public float getY(IFrameInfo frameInfo){
		return 0.0f;
	}
	
	public float getHeading(IFrameInfo frameInfo){
		return 0.0f;
	}

	@Override
	public float getPitch(IFrameInfo frameInfo) {
		return 0.0f;
	}
	
	public float getRoll(IFrameInfo frameInfo){
		return 0.0f;
	}

	@Override
	public boolean getShot(IFrameInfo frameInfo) {
		return false;
	}
}
