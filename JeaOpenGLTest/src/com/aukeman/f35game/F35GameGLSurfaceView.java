package com.aukeman.f35game;


import java.util.LinkedList;
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

import com.aukeman.f35game.R;
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
	
	private BadguyFactory badguyFactory;
	
	public F35GameGLSurfaceView(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		setRenderer(this);
		
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		mViewport = null;
		mWidgets = new LinkedList<TouchWidgetModel>();
		mDrawables = new LinkedList<IDrawable>();
		mUpdatables = new LinkedList<IUpdatable>();
		
		bullets = new LinkedList<BulletView>();
		
		badguys = new LinkedList<BadguyView>();
		
		mFrameInfo = new FrameInfo(true);
		
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
		bullets.add(new BulletView(getContext()));
		bullets.add(new BulletView(getContext()));
		bullets.add(new BulletView(getContext()));
		
		badguys.add(new BadguyView());
		badguys.add(new BadguyView());

		Queue<Long> badguyTimes = new LinkedList<Long>();
		badguyTimes.add(5000L);
		badguyTimes.add(10000L);
		badguyTimes.add(15000L);
		badguyTimes.add(20000L);
		badguyTimes.add(25000L);
		badguyTimes.add(30000L);
		
		badguyFactory = new BadguyFactory(getContext(), badguys, badguyTimes);
		
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
	
		for ( IUpdatable updatable : mUpdatables ){
			updatable.update(mFrameInfo);
		}
		
		for (IDrawable d : mDrawables){
			d.draw(mMVPMatrix);
		}
		
		font.drawString(mMVPMatrix, mViewport.getTop()+5, mViewport.getLeft()+5, mFrameInfo.getFrameRateString());
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int idx = event.getActionIndex();
		float x = mViewport.getXFromScreenX(event.getX(idx));
		float y = mViewport.getYFromScreenY(event.getY(idx));
		
		switch (event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			
			for (TouchWidgetModel w : mWidgets){
				w.handleDown(x, y, idx);
			}

			break;
		
		case MotionEvent.ACTION_MOVE:
			
			for ( idx = 0; idx < event.getPointerCount(); ++idx )			{
				for (TouchWidgetModel w : mWidgets){
					w.handleMove(x, y, idx);
				}
			}
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			
			for (TouchWidgetModel w : mWidgets){
				w.handleUp(x, y, idx);
			}
			break;
		}
		
		return true;
	}
	
}
