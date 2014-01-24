package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.JoystickModel;

public class JoystickView implements IDrawable{

	private Sprite mSprite;
	
	private JoystickModel mModel;
	
	public JoystickView( Context context, float top, float left){
		mModel = new JoystickModel(top, left, 32, 32);

		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.joystick, 1, 1);
		mSprite.moveTo(top, left);
	}

	public void draw(float[] mvpMatrix){
		
		mSprite.moveTo(mModel.getTop() + mModel.getAxisY()*0.5f*mModel.getHeight(), 
					   mModel.getLeft() + mModel.getAxisX()*0.5f*mModel.getWidth());
		this.mSprite.draw(mvpMatrix);
	}
	
	public JoystickModel getModel(){
		return mModel;
	}
}
