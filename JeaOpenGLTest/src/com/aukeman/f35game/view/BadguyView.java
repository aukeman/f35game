package com.aukeman.f35game.view;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.model.BadguyModel;
import com.aukeman.f35game.model.interfaces.IPath;

public class BadguyView extends AbstractView {

	private Sprite mSprite;
	
	private BadguyModel mModel;
	
	public BadguyView(){
		
		this.mSprite = null;
		this.mModel = new BadguyModel();
		
		setModel(mModel);
	}
	
	public boolean isActive(){
		return this.mModel.isActive();
	}
	
	public void activate(IFrameInfo frameInfo, Sprite sprite, float startTop, float startLeft, IPath path){
		this.mSprite = sprite;
		
		this.mModel.setWidth(mSprite.getWidth());
		this.mModel.setHeight(mModel.getHeight());
		
		this.mModel.activate(frameInfo, startTop, startLeft, path);
	}
	
	@Override
	public void draw(float[] mvpMatrix) {

		if ( this.isActive() ){
			super.draw(mvpMatrix);
		}
	}
}
