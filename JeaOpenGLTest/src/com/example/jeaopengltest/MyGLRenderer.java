package com.example.jeaopengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.View;

public class MyGLRenderer implements GLSurfaceView.Renderer {

	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjectionMatrix = new float[16];
	private final float[] mViewMatrix = new float[16];
	private final float[] mRotationMatrix = new float[16];
	
	private Sprite sprite;
	
	private Context context;
	private View view;
	
	public MyGLRenderer(Context context){
		
		this.context = context;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		 sprite = new Sprite( context, 0f, 0f, 16f, 16f, R.drawable.sprite);

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

		Log.i(MyGLRenderer.class.getName(), String.format("width: %d height: %d", width, height));
		Log.i(MyGLRenderer.class.getName(), String.format("top: %f bottom: %f left: %f right: %f", top, bottom, left, right));
		Log.i(MyGLRenderer.class.getName(), String.format("pixelHeightFactor: %d ratio: %f", pixelHeightFactor, ratio));
		
//		Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
		Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, 3, 7);
	}
	
	
	@Override
	public void onDrawFrame(GL10 arg0) {

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0, 0, 0, 0, 1, 0);
		
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		
		sprite.draw(mMVPMatrix);
		
	}

}
