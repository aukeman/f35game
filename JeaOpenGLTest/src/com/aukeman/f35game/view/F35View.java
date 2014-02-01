package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.F35Model;

public class F35View implements IDrawable{

	private Sprite mSprite;
	
	private F35Model mModel;
	
	public F35View(Context context){
		mModel = new F35Model(48, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.f35, 5, 1);

		mSprite.setTextureFrameIdx(2);
	}

	@Override
	public void draw(float[] mvpMatrix) {
		mSprite.moveTo(mModel.getTop(), mModel.getLeft());
		mSprite.draw(mvpMatrix);
	}

	@Override
	public float getTop() {
		return mModel.getTop();
	}

	@Override
	public float getLeft() {
		return mModel.getLeft();
	}

	@Override
	public float getWidth() {
		return mModel.getWidth();
	}

	@Override
	public float getHeight() {
		return mModel.getHeight();
	}

	@Override
	public float getPriority() {
		return mSprite.getPriority();
	}

	@Override
	public void moveTo(float top, float left) {
		mModel.moveTo(top, left);
	}

	@Override
	public void setPriority(float priority) {
		mSprite.setPriority(priority);
	}
	
	public F35Model getModel(){
		return mModel;
	}
	
}
