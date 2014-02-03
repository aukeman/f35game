package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public class StraightSegment implements IPathSegment {

	@Override
	public float getLeft(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getTop(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeading(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPitch(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRoll(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getShot(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComplete(long startTime, IFrameInfo frameInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTimeToTraverse() {
		// TODO Auto-generated method stub
		return 0;
	}

}
