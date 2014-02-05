package com.aukeman.f35game.model.path;

import java.util.LinkedList;
import java.util.List;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public class CompositePath implements IPath{

	private List<IPathSegment> mSegments;
	
	private static IGetFloatValue ourGetX;
	
	private static IGetFloatValue ourGetY;
	
	private static IGetFloatValue ourGetHeading;
	
	private static IGetFloatValue ourGetPitch;
	
	private static IGetFloatValue ourGetRoll;
	
	private static IGetBooleanValue ourGetShoot;
	
	private static IGetBooleanValue ourIsComplete;
	
	public CompositePath(){
		mSegments = new LinkedList<IPathSegment>();
	}

	
	public void addSegment(IPathSegment segment){
		mSegments.add(segment);
	}
	
	@Override
	public float getX(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		
		return calculateFloatValue(ourGetX, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getY(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateFloatValue(ourGetY, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getHeading(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateFloatValue(ourGetHeading, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getPitch(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateFloatValue(ourGetPitch, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getRoll(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateFloatValue(ourGetRoll, startTime, startX, startY, frameInfo);
	}

	@Override
	public boolean getShoot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateBooleanValue(ourGetShoot, startTime, startX, startY, frameInfo);
	}

	@Override
	public boolean isComplete(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateBooleanValue(ourIsComplete, startTime, startX, startY, frameInfo);
	}

	private float calculateFloatValue(IGetFloatValue getter, long startTime, float startX, float startY, IFrameInfo frameInfo){
		int idx = 0;

		float result = 0.0f;
		
		for ( IPathSegment segment : mSegments ){
			if ( idx == (mSegments.size()-1) ||
				 !segment.isComplete(startTime, startX, startY, frameInfo) ){
				result = getter.calculate(segment, startTime, startX, startY, frameInfo);
				break;
			}
			else{
				startTime += segment.getDuration();
				startX = segment.getFinalX(startX);
				startY = segment.getFinalY(startY);
			}
			
			++idx;
		}
		
		return result;
	}
	
	private boolean calculateBooleanValue(IGetBooleanValue getter, long startTime, float startX, float startY, IFrameInfo frameInfo){
		int idx = 0;

		boolean result = false;
		
		for ( IPathSegment segment : mSegments ){
			if ( idx == (mSegments.size()-1) ||
				 !segment.isComplete(startTime, startX, startY, frameInfo) ){
				result = getter.calculate(segment, startTime, startX, startY, frameInfo);
				break;
			}
			else{
				startTime += segment.getDuration();
				startX = segment.getFinalX(startX);
				startY = segment.getFinalY(startY);
			}
			
			++idx;
		}
		
		return result;
	}
	
	private interface IGetFloatValue{
		public float calculate(IPathSegment segment, long startTime, float startX, float startY, IFrameInfo frameInfo);
	}

	private interface IGetBooleanValue{
		public boolean calculate(IPathSegment segment, long startTime, float startX, float startY, IFrameInfo frameInfo);
	}

	static {
		ourGetX = new IGetFloatValue() {
			
			@Override
			public float calculate(IPathSegment segment, long startTime, float startX,
					float startY, IFrameInfo frameInfo) {
				return segment.getX(startTime, startX, startY, frameInfo);
			}};

		ourGetY = new IGetFloatValue() {
			
			@Override
			public float calculate(IPathSegment segment, long startTime, float startX,
					float startY, IFrameInfo frameInfo) {
				return segment.getY(startTime, startX, startY, frameInfo);
			}};

		ourGetHeading = new IGetFloatValue() {
				
				@Override
				public float calculate(IPathSegment segment, long startTime, float startX,
						float startY, IFrameInfo frameInfo) {
					return segment.getHeading(startTime, startX, startY, frameInfo);
				}};

		ourGetRoll = new IGetFloatValue() {
				
				@Override
				public float calculate(IPathSegment segment, long startTime, float startX,
						float startY, IFrameInfo frameInfo) {
					return segment.getRoll(startTime, startX, startY, frameInfo);
				}};

		ourGetPitch = new IGetFloatValue() {
			
			@Override
			public float calculate(IPathSegment segment, long startTime, float startX,
					float startY, IFrameInfo frameInfo) {
				return segment.getPitch(startTime, startX, startY, frameInfo);
			}};
			
		ourGetShoot = new IGetBooleanValue() {
			
			@Override
			public boolean calculate(IPathSegment segment, long startTime,
					float startX, float startY, IFrameInfo frameInfo) {
				return segment.getShoot(startTime, startX, startY, frameInfo);
			}
		};
	
		ourIsComplete = new IGetBooleanValue() {
			
			@Override
			public boolean calculate(IPathSegment segment, long startTime,
					float startX, float startY, IFrameInfo frameInfo) {
				return segment.isComplete(startTime, startX, startY, frameInfo);
			}
		};
	}
	

	
}
