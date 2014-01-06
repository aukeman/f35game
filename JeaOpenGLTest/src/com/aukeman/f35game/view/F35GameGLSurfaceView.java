package com.aukeman.f35game.view;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class F35GameGLSurfaceView extends GLSurfaceView {

	private final F35GameGLRenderer mRenderer;
	
	public F35GameGLSurfaceView(Context context) {
		super(context);
		
		
		setEGLContextClientVersion(2);
		
		mRenderer = new F35GameGLRenderer(context);
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
