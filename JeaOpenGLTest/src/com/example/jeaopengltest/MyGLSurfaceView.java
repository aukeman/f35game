package com.example.jeaopengltest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {

	private final MyGLRenderer mRenderer;
	
	public MyGLSurfaceView(Context context) {
		super(context);
		
		
		setEGLContextClientVersion(2);
		
		mRenderer = new MyGLRenderer();
		setRenderer(mRenderer);
		
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
