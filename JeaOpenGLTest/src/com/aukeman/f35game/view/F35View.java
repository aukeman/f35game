package com.aukeman.f35game.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.F35Model;

public class F35View extends AbstractView{

	private Sprite mSprite;
	
	private F35Model mModel;
	
	private List<BulletView> mBullets;
	
	public F35View(Context context){
		mModel = new F35Model(48, 32);
		
		mSprite = new Sprite(context, mModel.getWidth(), mModel.getHeight(), R.drawable.f35, 5, 1);

		mSprite.setTextureFrameIdx(2);
		
		setModel(mModel);
		setDrawable(mSprite);
		
		mBullets = new LinkedList<BulletView>();
	}

	public F35Model getModel(){
		return mModel;
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		super.draw(mvpMatrix);
		
		for ( BulletView bullet : mBullets ){
			if ( bullet.getModel().isActive() ){
				bullet.draw(mvpMatrix);
			}
		}
	}
	
	public void addBullet(BulletView bullet){
		mBullets.add(bullet);
		
		mModel.addBullet(bullet.getModel());
	}
	
}
