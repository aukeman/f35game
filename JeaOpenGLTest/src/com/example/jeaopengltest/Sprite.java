package com.example.jeaopengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.Matrix;

public class Sprite {

	private float mTop;
	private float mLeft;

	private float mWidth;
	private float mHeight;
	
	private static float[] mMVPMatrix = new float[16];
	
	private FloatBuffer mVertices;
	
	private ShortBuffer mDrawOrder;
	
	private float[] mColor;
	
	public Sprite(float top, float left, float width, float height) {
		super();
		this.mTop = top;
		this.mLeft = left;
		this.mWidth = width;
		this.mHeight = height;
		
		ByteBuffer bb = ByteBuffer.allocateDirect(12*4);
		bb.order(ByteOrder.nativeOrder());
		
		mVertices = bb.asFloatBuffer();
		mVertices.put(new float[] { 0.0f,        0.0f,        0.0f,
				                    0.0f,       -1.0f*height, 0.0f,
				                    1.0f*width, -1.0f*height, 0.0f,
				                    1.0f*width,  0.0f,        0.0f });
		mVertices.position(0);
		
		bb = ByteBuffer.allocateDirect(6*2);
		bb.order(ByteOrder.nativeOrder());
		
		mDrawOrder = bb.asShortBuffer();
		mDrawOrder.put(new short[] { 0, 1, 2, 0, 2, 3 });
		mDrawOrder.position(0);
		
		mColor = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		
		
	}
	
	public void draw(float[] mvpMatrix){

		Matrix.translateM(mMVPMatrix, 0, mvpMatrix, 0, mTop, mLeft, 0);
		
		mLeft += 0.001f;
		
		Shaders.render(6, mVertices, mDrawOrder, mColor, mMVPMatrix);
	}
	
	
}
