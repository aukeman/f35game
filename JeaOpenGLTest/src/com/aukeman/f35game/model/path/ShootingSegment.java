package com.aukeman.f35game.model.path;

import com.aukeman.f35game.IFrameInfo;

public class ShootingSegment extends AbstractPathSegment{

	@Override
	public float getHeading(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public float getPitch(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public float getRoll(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return 0;
	}

	@Override
	public boolean getShoot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		return true;
	}

	@Override
	public float calculateXAtPercentComplete(float percent) {
		return 0;
	}

	@Override
	public float calculateYAtPercentComplete(float percent) {
		return 0;
	}

}
