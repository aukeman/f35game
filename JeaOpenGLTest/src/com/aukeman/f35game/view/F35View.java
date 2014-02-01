package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.F35Model;

public class F35View extends AbstractView{

	private Sprite mSprite;
	
	private F35Model mModel;
	
	public F35View(Context context){
		mModel = new F35Model(48, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.f35, 5, 1);

		mSprite.setTextureFrameIdx(2);
		
		setModel(mModel);
		setDrawable(mSprite);
	}

	public F35Model getModel(){
		return mModel;
	}
	
}
