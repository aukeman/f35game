package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.JoystickModel;

public class JoystickView {

	private float mTop;
	private float mLeft;
	private float mWidth;
	private float mHeight;
	
	private Sprite mSprite;
	
	private JoystickModel mModel;
	
	public JoystickView( Context context, float top, float left, float width, float height){
		mTop = top;
		mLeft = left;
		mWidth = width;
		mHeight = height;
		
		mSprite = new Sprite(context, width, height, R.drawable.joystick, 1, 1);
		mSprite.moveTo(top, left);
		
		mModel = new JoystickModel(mTop, mLeft, mHeight, mWidth);
	}

	public void draw(float[] mvpMatrix){
		
		mSprite.moveTo(mTop + mModel.getY()*0.5f*mHeight, 
					   mLeft + mModel.getX()*0.5f*mWidth);
		this.mSprite.draw(mvpMatrix);
		
	}
	
	public JoystickModel getModel(){
		return mModel;
	}
}
