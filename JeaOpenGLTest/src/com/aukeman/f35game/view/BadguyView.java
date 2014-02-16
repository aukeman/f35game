package com.aukeman.f35game.view;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.Pool;
import com.aukeman.f35game.interfaces.IPoolable;
import com.aukeman.f35game.model.AbstractModel;
import com.aukeman.f35game.model.BadguyModel;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class BadguyView extends AbstractView implements IPoolable {

	private BadguyModel mModel;
	
	public BadguyView(){
		
		this.mModel = new BadguyModel();
		
		setModel(mModel);
	}
	
	public boolean isActive(){
		return this.mModel.isActive();
	}
	
	public void activate(IFrameInfo frameInfo, IDrawable drawable, float startTop, float startLeft, IPath path, AbstractModel ownshipModel, Pool<BulletView> bulletPool){
		setDrawable(drawable);
		
		this.mModel.setWidth(drawable.getWidth());
		this.mModel.setHeight(drawable.getHeight());
		
		this.mModel.activate(frameInfo, startTop, startLeft, path, ownshipModel, bulletPool);
	}
	
	@Override
	public void draw(float[] mvpMatrix) {

		if ( this.isActive() ){
			super.draw(mvpMatrix);
		}
	}
	
	@Override
	public boolean available() {
		return !isActive();
	}
}
