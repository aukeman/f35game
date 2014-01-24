package com.aukeman.f35game.view;


import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.aukeman.f35game.R;
import com.aukeman.f35game.model.TouchWidgetModel;

public class F35GameGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

	private class Viewport{
		public float top = 0.0f;
		public float bottom = 0.0f;
		public float left = 0.0f;
		public float right = 0.0f;
		
		public float screenWidth = 0.0f;
		public float screenHeight = 0.0f;
	
		public float getXFromScreenX(float screenX){
			return screenX * (this.right - this.left) / this.screenWidth + this.left;
		}

		public float getYFromScreenY(float screenY){
			return screenY * (this.bottom - this.top) / this.screenHeight + this.top;
		}
	}
	
	private static final String VIEW_LOG_TAG = F35GameGLSurfaceView.class.getName();
	
	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];

	private Sprite sprite;
	
	private JoystickView joystick;
	
	private Font font;
	
	private Background background;
	
	private int frameCount; 
	private long timeLastFrameCountPosted;
	private long lastFrameTime;
	private String frameRateMessage = "";

	private Viewport mViewport;
	
	private List<TouchWidgetModel> mWidgets;
	private List<IDrawable> mDrawables;
	
	public F35GameGLSurfaceView(Context context) {
		super(context);
		
		setEGLContextClientVersion(2);
		
		setRenderer(this);
		
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		mViewport = new Viewport();
		mWidgets = new LinkedList<TouchWidgetModel>();
		mDrawables = new LinkedList<IDrawable>();
		
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


		sprite = new Sprite( getContext(), 16f, 16f, R.drawable.sprite, 2, 2 );
		joystick = new JoystickView(getContext(), 0, 0, 32, 32);
		 			 
		 font = new Font(getContext());
		 
		 background = new Background(16, 16, 16, 16, new Sprite[] { sprite } );
			
		 for ( int i = 0; i < 16; ++i ){
			 for ( int j = 0; j < 16; ++j ){
				 background.setTile(i, j, 0);
			 }
		 }
		 
		 background.setTile(0, 1, -1);
		 
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Shaders.initialize();
		
		mDrawables.clear();
		mDrawables.add(background);
		mDrawables.add(joystick);
		
		mWidgets.clear();
		mWidgets.add(joystick.getModel());
	}
	
	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		int numberOfLines = 256;
		
		int pixelHeightFactor = height/numberOfLines;
		
		float ratio = (float)width / height;
		
		mViewport.top = -(float)0.5f*height/pixelHeightFactor;
		mViewport.bottom = -mViewport.top;
		mViewport.left = (float)ratio*mViewport.top;
		mViewport.right = -mViewport.left;

		mViewport.screenWidth = width;
		mViewport.screenHeight = height;
		
		Log.i(VIEW_LOG_TAG, String.format("width: %d height: %d", width, height));
		Log.i(VIEW_LOG_TAG, String.format("top: %f bottom: %f left: %f right: %f", mViewport.top, mViewport.bottom, mViewport.left, mViewport.right));
		Log.i(VIEW_LOG_TAG, String.format("pixelHeightFactor: %d ratio: %f", pixelHeightFactor, ratio));
		
		Matrix.orthoM(mProjectionMatrix, 0, mViewport.left, mViewport.right, mViewport.bottom, mViewport.top, 3, 7);

		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
		
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

		
	}
	
	
	@Override
	public void onDrawFrame(GL10 arg0) {

		long now = System.currentTimeMillis();
		
		if ( now % 1000 < lastFrameTime % 1000 ){
			frameRateMessage = String.format("FPS: %.1f", (double)frameCount / (now - timeLastFrameCountPosted ) * 1000 );
			timeLastFrameCountPosted = now;
			frameCount = 0;
		}
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
	
		long seconds = now/1000;
		
		sprite.setTextureFrameIdx( (int)seconds%4 );
		
		for (IDrawable d : mDrawables){
			d.draw(mMVPMatrix);
		}
		
		font.drawString(mMVPMatrix, -100, -200, frameRateMessage);
		
		lastFrameTime = now;
		++frameCount;
		
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
