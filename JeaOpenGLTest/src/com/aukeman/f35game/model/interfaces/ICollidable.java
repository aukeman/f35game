package com.aukeman.f35game.model.interfaces;

import java.util.List;

import com.aukeman.f35game.model.BoundingBox;

public interface ICollidable {

	public boolean testCollision( ICollidable other );
	
	public BoundingBox getBoundingBox();
	
	public List<BoundingBox> getCollisionAreas();
}
