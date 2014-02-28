package com.aukeman.f35game.model;

import java.util.ArrayList;
import java.util.List;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.ICollidable;
import com.aukeman.f35game.model.interfaces.IUpdatable;

public abstract class AbstractModel extends BoundingBox implements IUpdatable, ICollidable {

	private List<BoundingBox> mCollisionAreas;
	
	public AbstractModel(float height, float width){
		super(0.0f, 0.0f, height,width);
		
		mCollisionAreas = new ArrayList<BoundingBox>();
	}
	 
	public void moveTo(float top, float left){
		setTop(top);
		setLeft(left);
	}
	
	@Override
	public void update(IFrameInfo frameInfo) {
		/* no-op */
	}
	
	public BoundingBox getBoundingBox(){
		return this;
	}
	
	public List<BoundingBox> getCollisionAreas(){
		return mCollisionAreas;
	}
	
	public boolean testCollision( ICollidable other ){
		
		boolean result = false;
		
		if ( this.getBoundingBox().overlaps(other.getBoundingBox()) ){
			
			if ( this.getCollisionAreas().size() == 0 && other.getCollisionAreas().size() == 0 ){
				result = true;
			}
			else if ( 0 < this.getCollisionAreas().size() && 0 < other.getCollisionAreas().size() ){
				
				for ( int idx1 = 0;
						!result && idx1 < getCollisionAreas().size();
						++idx1 ){
					
					for ( int idx2 = 0;
							!result && idx2 < other.getCollisionAreas().size();
							++idx2 ){
						
						if ( this.getCollisionAreas().get(idx1).overlaps(other.getCollisionAreas().get(idx2)) ){
							result = true;
						}
					}
				}
			}
			else if ( 0 < this.getCollisionAreas().size() ){

				for ( int idx = 0; 
					  !result && idx < this.getCollisionAreas().size(); 
					  ++idx ){
					
					if ( this.getCollisionAreas().get(idx).overlaps(other.getBoundingBox()) ){
						result = true;
					}
				}
			}
			else { // 0 < other.getCollisionAreas().size()
				
				for ( int idx = 0; 
						  !result && idx < other.getCollisionAreas().size(); 
						  ++idx ){
						
					if ( this.getBoundingBox().overlaps(other.getCollisionAreas().get(idx)) ){
						result = true;
					}
				}
			}
		}
		
		return result;
	}

	
}
