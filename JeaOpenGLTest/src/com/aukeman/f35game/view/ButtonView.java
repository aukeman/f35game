package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.TouchWidgetModel;

public class ButtonView implements IDrawable {
	
	private Sprite mSprite;
	
	private TouchWidgetModel mModel;
	
	public ButtonView( Context context, float top, float left ){
		mModel = new TouchWidgetModel(top, left, 32, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.button, 2, 1);
		mSprite.moveTo(top, left);
		mSprite.setTextureFrameIdx(0);
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
