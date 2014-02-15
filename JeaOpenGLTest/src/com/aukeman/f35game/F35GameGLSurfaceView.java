package com.aukeman.f35game;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.aukeman.f35game.model.BadguyFactory;
import com.aukeman.f35game.model.TouchWidgetModel;
import com.aukeman.f35game.model.interfaces.IUpdatable;
import com.aukeman.f35game.opengl.Shaders;
import com.aukeman.f35game.view.Background;
import com.aukeman.f35game.view.BadguyView;
import com.aukeman.f35game.view.BulletView;
import com.aukeman.f35game.view.ButtonView;
import com.aukeman.f35game.view.F35View;
import com.aukeman.f35game.view.Font;
import com.aukeman.f35game.view.JoystickView;
import com.aukeman.f35game.view.Sprite;
import com.aukeman.f35game.view.Viewport;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class F35GameGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

	private static final String VIEW_LOG_TAG = F35GameGLSurfaceView.class.getName();
	
	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];

	private Sprite sprite;
	
	private JoystickView joystick;
	private ButtonView button;
	
	private Font font;
	
	private Background background;
	
	private F35View ownship;
	
	private FrameInfo mFrameInfo;
	
	private Viewport mViewport;
	
	private List<TouchWidgetModel> mWidgets;
	private List<IDrawable> mDrawables;
	private List<IUpdatable> mUpdatables;
	
	private List<BulletView> bullets;
	
	private List<BadguyView> badguys;
	
	private List<BulletView> badguyBullets;

	private BadguyFactory badguyFactory;
	
	public F35GameGLSurfaceView(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		setRenderer(this);
		
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		mViewport = null;
		mWidgets = new ArrayList<TouchWidgetModel>();
		mDrawables = new ArrayList<IDrawable>();
		mUpdatables = new ArrayList<IUpdatable>();
		
		bullets = new ArrayList<BulletView>();
		
		badguys = new ArrayList<BadguyView>();
		
		badguyBullets = new ArrayList<BulletView>();
		
		mFrameInfo = new FrameInfo(false);
		
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		mViewport = new Viewport();
		
		sprite = new Sprite( getContext(), 16f, 16f, R.drawable.sprite, 2, 2 );
		joystick = new JoystickView(getContext());
		button = new ButtonView(getContext());
		
		ownship = new F35View(getContext());
		ownship.getModel().setControls(joystick.getModel(), button.getModel());
		ownship.getModel().setViewport(mViewport); 		
		
		bullets.clear();
		bullets.add( BulletView.buildGoodguyBullet(getContext()) );
		bullets.add( BulletView.buildGoodguyBullet(getContext()) );
		bullets.add( BulletView.buildGoodguyBullet(getContext()) );
		
		badguys.add(new BadguyView());
		badguys.add(new BadguyView());
		
		badguyBullets.clear();
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );
		badguyBullets.add( BulletView.BuildBadguyBullet(getContext()) );

		List<Long> badguyTimes = new ArrayList<Long>();
		badguyTimes.add(2000L);
//		badguyTimes.add(10000L);
//		badguyTimes.add(15000L);
//		badguyTimes.add(20000L);
//		badguyTimes.add(25000L);
//		badguyTimes.add(30000L);
		
		badguyFactory = new BadguyFactory(getContext(), badguys, badguyBullets, badguyTimes);
		
		 font = new Font(getContext());
		 
		 background = new Background(16, 32, new Sprite[] { sprite } );

		 for ( int i = 0; i < background.getTilesWide(); ++i ){
			 for ( int j = 0; j < background.getTilesHigh(); ++j ){
				 background.setTile(i, j, 0);
			 }
		 }
		 
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Shaders.initialize();
		
		mDrawables.clear();
//		mDrawables.add(background);
		mDrawables.add(joystick);
		mDrawables.add(button);
		mDrawables.add(ownship);
		
		mWidgets.clear();
		mWidgets.add(joystick.getModel());
		mWidgets.add(button.getModel());
		
		mUpdatables.clear();
		mUpdatables.add(ownship.getModel());
		mUpdatables.add(joystick.getModel());
		mUpdatables.add(button.getModel());
		mUpdatables.add(badguyFactory);

		for ( BadguyView badguy : badguys ){
			mUpdatables.add(badguy.getModel());
			mDrawables.add(badguy);
		}
		
		for ( BulletView bullet : bullets ){
			ownship.getModel().addBullet(bullet.getModel());
			bullet.getModel().setViewport(mViewport);
			mUpdatables.add(bullet.getModel());
			mDrawables.add(bullet);
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 arg0, int screenWidth, int screenHeight) {
		GLES20.glViewport(0, 0, screenWidth, screenHeight);
		
		int numberOfLines = 256;
		
		int pixelHeightFactor = screenHeight/numberOfLines;
		
		float ratio = (float)screenWidth / screenHeight;
		
		float top = 0.0f;
		float left = 0.0f;
		
		float bottom = (float)screenHeight/pixelHeightFactor + top;
		float right = (float)ratio*(bottom - top) + left; 

		mViewport.setTop(top);
		mViewport.setBottom(bottom);
		mViewport.setLeft(left);
		mViewport.setRight(right);
		mViewport.setScreenWidth(screenWidth);
		mViewport.setScreenHeight(screenHeight);
		
		Log.i(VIEW_LOG_TAG, String.format("width: %d height: %d", screenWidth, screenHeight));
		Log.i(VIEW_LOG_TAG, String.format("top: %f bottom: %f left: %f right: %f", mViewport.getTop(), mViewport.getBottom(), mViewport.getLeft(), mViewport.getRight()));
		Log.i(VIEW_LOG_TAG, String.format("pixelHeightFactor: %d ratio: %f", pixelHeightFactor, ratio));
		
		Matrix.orthoM(mProjectionMatrix, 0, mViewport.getLeft(), mViewport.getRight(), mViewport.getBottom(), mViewport.getTop(), 1, 201);

		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 101, 0, 0, 0, 0, 1, 0);
		
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

		joystick.moveTo( mViewport.getBottom() - 1.5f*joystick.getHeight(), 
				         mViewport.getLeft() + 0.5f*joystick.getWidth() );
		
		background.setPriority(-1.0f);
		
		button.moveTo( mViewport.getBottom() - 1.5f*button.getHeight(), 
					   mViewport.getRight() - 1.5f*button.getWidth() );
	}
	
	@Override
	public void onDrawFrame(GL10 arg0) {

		mFrameInfo.topOfFrame();
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
	
		for ( int idx = 0;
		      idx < mUpdatables.size();
			  ++idx ){
			
			mUpdatables.get(idx).update(mFrameInfo);
		}
		
		
		for ( int idx = 0;
			  idx < mDrawables.size();
			  ++idx ){

			mDrawables.get(idx).draw(mMVPMatrix);
		}
		
		boolean collision = false;
		
		for ( int badguyIdx = 0;
			  badguyIdx < badguys.size();
			  ++badguyIdx ){

			BadguyView badguy = badguys.get(badguyIdx);

			if ( badguy.isActive() ){

				if ( ownship.getModel().testCollision(badguy.getModel() ) ){
					collision = true;
				}

				
				for ( int bulletIdx = 0;
					  bulletIdx < bullets.size();
					  ++bulletIdx ){
				
					BulletView bullet = bullets.get(bulletIdx);

					if ( bullet.getModel().isActive() ){

						if ( badguy.getModel().testCollision(bullet.getModel()) ){
							collision = true;
						}
					}
				}
			}
		}
		
		font.drawString(mMVPMatrix, mViewport.getTop()+5, mViewport.getLeft()+5, mFrameInfo.getFrameRateString());

		if ( collision ){
			font.drawString(mMVPMatrix, mViewport.getBottom() - 24, mViewport.getLeft() + 32, "boom!");
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int pIdx = event.getActionIndex();
		float x = mViewport.getXFromScreenX(event.getX(pIdx));
		float y = mViewport.getYFromScreenY(event.getY(pIdx));
		
		switch (event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			
			for ( int wIdx = 0; wIdx < mWidgets.size(); ++wIdx ){
				mWidgets.get(wIdx).handleDown(x, y, pIdx);
			}

			break;
		
		case MotionEvent.ACTION_MOVE:
			
			for ( pIdx = 0; pIdx < event.getPointerCount(); ++pIdx )			{
				
				for ( int wIdx = 0; wIdx < mWidgets.size(); ++wIdx ){
					mWidgets.get(wIdx).handleMove(x, y, pIdx);
				}
			}
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:

			for ( int wIdx = 0; wIdx < mWidgets.size(); ++wIdx ){
				mWidgets.get(wIdx).handleUp(x, y, pIdx);
			}
			break;
		}
		
		return true;
	}
	
}
