package com.aukeman.f35game.model;

import java.util.LinkedList;
import java.util.List;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.ICollidable;
import com.aukeman.f35game.model.interfaces.IUpdatable;

public abstract class AbstractModel extends BoundingBox implements IUpdatable, ICollidable {

	private List<BoundingBox> mBoundingBoxes;
	
	public AbstractModel(float height, float width){
		super(0.0f, 0.0f, height,width);
		
		mBoundingBoxes = new LinkedList<BoundingBox>();
		mBoundingBoxes.add(this);
	}
	 
	public void moveTo(float top, float left){
		setTop(top);
		setLeft(left);
	}
	
	@Override
	public void update(IFrameInfo frameInfo) {
		/* no-op */
	}
	
	public List<BoundingBox> getBoundingBoxes(){
		return mBoundingBoxes;
	}
	
	public boolean testCollision( ICollidable other ){
		for ( BoundingBox thisBBox : mBoundingBoxes ){
			for ( BoundingBox otherBBox : other.getBoundingBoxes() ){
				if ( thisBBox.overlaps(otherBBox) ){
					return true;
				}
			}
		}
		
		return false;
	}

	
}
