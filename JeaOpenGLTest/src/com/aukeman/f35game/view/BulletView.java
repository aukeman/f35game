package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.BulletModel;

public class BulletView extends AbstractView {

	BulletModel mModel;

	Sprite mSprite;
	
	public BulletView(Context context){
		this.mModel = new BulletModel(16, 8);
		this.mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.bullet, 1, 1);

		setModel(this.mModel);
		setDrawable(this.mSprite);
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		if ( this.mModel.isActive() ){
			this.mSprite.draw(mvpMatrix);
		}
	}
}
