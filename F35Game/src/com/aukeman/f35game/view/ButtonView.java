package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.TouchWidgetModel;

public class ButtonView extends AbstractView{
	
	private Sprite mSprite;
	
	private TouchWidgetModel mModel;

	public ButtonView( Context context){
		mModel = new TouchWidgetModel(0, 0, 32, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.button, 2, 1);
		mSprite.setTextureFrameIdx(0);
		
		setDrawable(mSprite);
		setModel(mModel);
	}
	
	
	@Override
	public void draw(float[] mvpMatrix) {
		mSprite.setTextureFrameIdx( mModel.isDown() ? 1 : 0 );

		super.draw(mvpMatrix);
	}
	
	public TouchWidgetModel getModel(){
		return mModel;
	}
}
