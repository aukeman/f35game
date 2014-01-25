package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.TouchWidgetModel;

public class ButtonView implements IDrawable {
	
	private Sprite mSprite;
	
	private TouchWidgetModel mModel;

	public ButtonView( Context context){
		mModel = new TouchWidgetModel(0, 0, 32, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.button, 2, 1);
		mSprite.setTextureFrameIdx(0);
	}
	
	public float getTop(){
		return mModel.getTop();
	}
	
	public float getLeft(){
		return mModel.getLeft();
	}
	
	public float getWidth(){
		return mModel.getWidth();
	}
	
	public float getHeight(){
		return mModel.getHeight();
	}
	
	public void moveTo( float top, float left ){
		mModel.moveTo(top, left);
		mSprite.moveTo(top, left);
	}
	

	@Override
	public void draw(float[] mvpMatrix) {
		mSprite.setTextureFrameIdx( mModel.isDown() ? 1 : 0 );
		mSprite.draw(mvpMatrix);
	}
	
	public TouchWidgetModel getModel(){
		return mModel;
	}
}
