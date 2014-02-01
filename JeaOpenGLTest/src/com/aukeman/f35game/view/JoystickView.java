package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.JoystickModel;

public class JoystickView implements IDrawable{

	private Sprite mSprite;
	
	private JoystickModel mModel;
	
	public JoystickView( Context context){
		mModel = new JoystickModel(0, 0, 32, 32);

		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.joystick, 1, 1);
		mSprite.moveTo(0, 0);
	}
	
	public float getTop(){
		return mModel.getTop();
	}
	
	public float getLeft(){
		return mModel.getLeft();
	}
	
	public float getPriority(){
		return mSprite.getPriority();
	}
	
	public float getWidth(){
		return mModel.getWidth();
	}
	
	public float getHeight(){
		return mModel.getHeight();
	}

	public void moveTo(float top, float left){
		mModel.moveTo(top, left);
	}
	
	public void setPriority(float priority){
		mSprite.setPriority(priority);
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
