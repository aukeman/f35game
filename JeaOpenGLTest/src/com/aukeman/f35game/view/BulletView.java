package com.aukeman.f35game.view;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.BulletModel;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class BulletView extends AbstractView {

	private static IDrawable ourGoodguyBulletSprite = null;
	
	private static IDrawable ourBadguyBulletSprite = null;
	
	public static BulletView buildGoodguyBullet(Context context){
		
		BulletModel model = new BulletModel(8f, 2f, 220f);
		
		if ( ourGoodguyBulletSprite == null ){
			ourGoodguyBulletSprite = new Sprite(context, model.getWidth(), model.getHeight(), R.drawable.bullet, 1, 1);
		}
		
		return new BulletView(model, ourGoodguyBulletSprite);
	}
	
	public static BulletView BuildBadguyBullet(Context context){

		BulletModel model = new BulletModel(8f, 8f, 60f);
		
		if ( ourBadguyBulletSprite == null ){
			ourBadguyBulletSprite = new Sprite(context, model.getWidth(), model.getHeight(), R.drawable.badguy_bullet, 1, 1);
		}
		
		return new BulletView(model, ourBadguyBulletSprite);
	}
	
	private BulletModel mModel;
	
	private BulletView(BulletModel model, IDrawable drawable){
		this.mModel = model;
		
		setModel(this.mModel);
		setDrawable(drawable);
	}
	
	public void draw(float[] mvpMatrix){
		if ( this.mModel.isActive() ){
			super.draw(mvpMatrix);
		}
	}
	
	public BulletModel getModel(){
		return mModel;
	}
}
