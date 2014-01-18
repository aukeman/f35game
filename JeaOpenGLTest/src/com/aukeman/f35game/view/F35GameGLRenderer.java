package com.aukeman.f35game.view;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.aukeman.f35game.R;
import com.aukeman.f35game.R.drawable;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.View;

public class F35GameGLRenderer implements GLSurfaceView.Renderer {

	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];
	private final float[] mRotationMatrix = new float[16];
	
	private Sprite sprite;
	
	private Sprite joystick;
	
	private Font font;
	
	private Background background;
	
	private Context context;
	private View view;
	
	private int frameCount; 
	private long timeLastFrameCountPosted;
	private long lastFrameTime;
	private String frameRateMessage = "";
	
	
	public F35GameGLRenderer(Context context){
		
		this.context = context;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		 sprite = new Sprite( context, 16f, 16f, R.drawable.sprite, 2, 2 );
		 joystick = new Sprite(context, 32, 32, R.drawable.joystick, 1, 1);
		 	
		 
		 font = new Font(context);
		 
		 background = new Background(16, 16, 16, 16, new Sprite[] { sprite } );
			
		 for ( int i = 0; i < 16; ++i ){
			 for ( int j = 0; j < 16; ++j ){
				 background.setTile(i, j, 0);
			 }
		 }
		 
		 background.setTile(0, 1, -1);
		 
		 GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1);

		Shaders.initialize();
	}
	
	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		int numberOfLines = 256;
		
		int pixelHeightFactor = height/numberOfLines;
		
		float ratio = (float)width / height;
		
		float top = (float)0.5f*height/pixelHeightFactor;
		float bottom = -top;
		float left = -(float)ratio*top;
		float right = -left;

		Log.i(F35GameGLRenderer.class.getName(), String.format("width: %d height: %d", width, height));
		Log.i(F35GameGLRenderer.class.getName(), String.format("top: %f bottom: %f left: %f right: %f", top, bottom, left, right));
		Log.i(F35GameGLRenderer.class.getName(), String.format("pixelHeightFactor: %d ratio: %f", pixelHeightFactor, ratio));
		
//		Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
		Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, 3, 7);
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
		
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0);
		
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
	
		long seconds = now/1000;
		
		sprite.setTextureFrameIdx( (int)seconds%4 );
		
		
		background.draw(mMVPMatrix);
		
		font.drawString(mMVPMatrix, -100, -200, frameRateMessage);
		
		joystick.moveTo(100, -100);
		joystick.draw(mMVPMatrix);
		
		lastFrameTime = now;
		++frameCount;
		
//		sprite.draw(mMVPMatrix);
		
	}

}
