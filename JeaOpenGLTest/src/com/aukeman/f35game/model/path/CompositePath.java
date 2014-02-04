package com.aukeman.f35game.model.path;

import java.util.LinkedList;
import java.util.List;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.model.interfaces.IPathSegment;

public abstract class CompositePath implements IPath{

	private List<IPathSegment> mSegments;
	
	private static IGetFloatValue ourGetLeft;
	
	private static IGetFloatValue ourGetTop;
	
	private static IGetFloatValue ourGetHeading;
	
	private static IGetFloatValue ourGetPitch;
	
	private static IGetFloatValue ourGetRoll;
	
	private static IGetBooleanValue ourGetShot;
	
	private static IGetBooleanValue ourIsComplete;
	
	public CompositePath(){
		mSegments = new LinkedList<IPathSegment>();
	}

	
	protected void addSegment(IPathSegment segment){
		mSegments.add(segment);
	}
	
	@Override
	public float getLeft(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		
		return calculateFloatValue(ourGetLeft, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getTop(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateFloatValue(ourGetTop, startTime, startX, startY, frameInfo);
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
	public boolean getShot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateBooleanValue(ourGetShot, startTime, startX, startY, frameInfo);
	}

	@Override
	public boolean isComplete(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateBooleanValue(ourIsComplete, startTime, startX, startY, frameInfo);
	}

	@Override
	public long getDuration() {

		long result = 0;
		
		for ( IPathSegment segment : mSegments ){
			result += segment.getDuration();
		}
		
		return result;
	}

	@Override
	public float getFinalX(float startX) {
		
		for ( IPathSegment segment : mSegments ){
			startX = segment.getFinalX(startX);
		}
		
		return startX;
	}

	@Override
	public float getFinalY(float startY) {

		for ( IPathSegment segment : mSegments ){
			startY = segment.getFinalX(startY);
		}
		
		return startY;
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
		ourGetLeft = new IGetFloatValue() {
			
			@Override
			public float calculate(IPathSegment segment, long startTime, float startX,
					float startY, IFrameInfo frameInfo) {
				return segment.getLeft(startTime, startX, startY, frameInfo);
			}};

		ourGetTop = new IGetFloatValue() {
			
			@Override
			public float calculate(IPathSegment segment, long startTime, float startX,
					float startY, IFrameInfo frameInfo) {
				return segment.getTop(startTime, startX, startY, frameInfo);
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
			
		ourGetShot = new IGetBooleanValue() {
			
			@Override
			public boolean calculate(IPathSegment segment, long startTime,
					float startX, float startY, IFrameInfo frameInfo) {
				return segment.getShot(startTime, startX, startY, frameInfo);
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
