package com.aukeman.f35game.model;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.interfaces.IUpdatable;

public abstract class AbstractModel extends BoundingBox implements IUpdatable {

	
	public AbstractModel(float height, float width){
		super(0.0f, 0.0f, height,width);
	}
	 
	public void moveTo(float top, float left){
		setTop(top);
		setLeft(left);
	}
	
	@Override
	public void update(IFrameInfo frameInfo) {
		/* no-op */
	}

}
