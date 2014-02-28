package com.aukeman.f35game.model.path;

import java.util.ArrayList;
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
		mSegments = new ArrayList<IPathSegment>();
	}

	
	public void addSegment(IPathSegment segment){
		mSegments.add(segment);
	}
	
	@Override
	public float getX(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {
		
		return calculateValue(ourGetX, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getY(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValue(ourGetY, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getHeading(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValue(ourGetHeading, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getPitch(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValue(ourGetPitch, startTime, startX, startY, frameInfo);
	}

	@Override
	public float getRoll(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValue(ourGetRoll, startTime, startX, startY, frameInfo);
	}

	@Override
	public boolean getShoot(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValueSinceLastFrame(ourGetShoot, startTime, startX, startY, frameInfo);
	}

	@Override
	public boolean isComplete(long startTime, float startX, float startY,
			IFrameInfo frameInfo) {

		return calculateValue(ourIsComplete, startTime, startX, startY, frameInfo);
	}

	private float calculateValue(IGetFloatValue getter, long startTime, float startX, float startY, IFrameInfo frameInfo){

		float result = 0.0f;
		
		for ( int idx = 0;
			  idx < mSegments.size();
			  ++idx )
		{
			IPathSegment segment = mSegments.get(idx);	
			
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
		}
		
		return result;
	}
	
	private boolean calculateValue(IGetBooleanValue getter, long startTime, float startX, float startY, IFrameInfo frameInfo){

		boolean result = false;

		for ( int idx = 0;
			  idx < mSegments.size();
			  ++idx ){

			IPathSegment segment = mSegments.get(idx);
			
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
		}
		
		return result;
	}
	
	private boolean calculateValueSinceLastFrame(IGetBooleanValue getter, long startTime, float startX, float startY, IFrameInfo frameInfo){

		boolean result = false;

		IPathSegment resultSegment = null;

		// TODO: really need to be smarter about 
		// how we handle shooting "segments"
		
		for ( int idx = 0;
			  idx < mSegments.size();
			  ++idx ){

			IPathSegment segment = mSegments.get(idx);
			
//			boolean completedThisFrame = segment.isComplete(startTime, startX, startY, frameInfo);
//			boolean completedLastFrame = segment.isCompleteSinceLastFrame(startTime, startX, startY, frameInfo);
			
			if ( idx == (mSegments.size()-1) ||
				 (frameInfo.getTopOfLastFrame() <= startTime &&
				  startTime + segment.getDuration() < frameInfo.getTopOfFrame()) ){

				resultSegment = segment;
				
				//result = getter.calculate(segment, startTime, startX, startY, frameInfo);
			}
			else if ( frameInfo.getTopOfFrame() < startTime + segment.getDuration() ){
				break;
			}
			else{
				startTime += segment.getDuration();
				startX = segment.getFinalX(startX);
				startY = segment.getFinalY(startY);
			}
		}
		
		if ( resultSegment != null ){
			result = getter.calculate(resultSegment, startTime, startX, startY, frameInfo);
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
