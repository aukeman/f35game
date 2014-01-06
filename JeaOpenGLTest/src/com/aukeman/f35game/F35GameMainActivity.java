package com.aukeman.f35game;

import com.aukeman.f35game.view.F35GameGLSurfaceView;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class F35GameMainActivity extends Activity {

	private GLSurfaceView mGLView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGLView = new F35GameGLSurfaceView(this);
		
		setContentView(mGLView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}
}
