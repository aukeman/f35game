package com.aukeman.f35game.view;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.BadguyModel;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class BadguyView extends AbstractView {

	private BadguyModel mModel;
	
	public BadguyView(){
		
		this.mModel = new BadguyModel();
		
		setModel(mModel);
	}
	
	public boolean isActive(){
		return this.mModel.isActive();
	}
	
	public void activate(IFrameInfo frameInfo, IDrawable drawable, float startTop, float startLeft, IPath path){
		setDrawable(drawable);
		
		this.mModel.setWidth(drawable.getWidth());
		this.mModel.setHeight(drawable.getHeight());
		
		this.mModel.activate(frameInfo, startTop, startLeft, path);
	}
	
	@Override
	public void draw(float[] mvpMatrix) {

		if ( this.isActive() ){
			super.draw(mvpMatrix);
		}
	}
}
