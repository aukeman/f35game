package com.aukeman.f35game.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


import android.content.Context;
import android.opengl.Matrix;

public class Sprite {

	private float mTop;
	private float mLeft;

	private float mWidth;
	private float mHeight;
	
	private static float[] mMVPMatrix = new float[16];
	
	private FloatBuffer mVertices;
	
	private FloatBuffer[] mTextureCoordinates;
	
	private ShortBuffer mDrawOrder;
	
	private float[] mColor;
	
	private int mTextureId;
	
	private int mTextureColumns;
	
	private int mTextureRows;
	
	private int mTextureFrameIdx;
	
	public Sprite(Context context, float width, float height, int textureId, int textureColumns, int textureRows) {
		super();
		this.mTop = 0.0f;
		this.mLeft = 0.0f;
		this.mWidth = width;
		this.mHeight = height;
		this.mTextureId = Shaders.loadTexture(context, textureId);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(4*3*4);
		bb.order(ByteOrder.nativeOrder());
		
		mVertices = bb.asFloatBuffer();
		mVertices.put(new float[] { 0.0f,        0.0f,        0.0f,
				                    0.0f,       1.0f*height, 0.0f,
				                    1.0f*width, 1.0f*height, 0.0f,
				                    1.0f*width,  0.0f,        0.0f });
		mVertices.position(0);
		
		mTextureCoordinates = new FloatBuffer[textureColumns*textureRows];
		
		float frameWidth = 1.0f / textureColumns;
		float frameHeight = 1.0f / textureRows;
		
		int idx = 0;
		
		for ( int rowIdx = 0; rowIdx < textureRows; ++rowIdx){
			for ( int colIdx = 0; colIdx < textureColumns; ++colIdx){

				bb = ByteBuffer.allocateDirect(4*2*4);
				bb.order(ByteOrder.nativeOrder());
				mTextureCoordinates[idx] = bb.asFloatBuffer();
				mTextureCoordinates[idx].put(new float[] { frameWidth*colIdx, frameHeight*(rowIdx+1),
														   frameWidth*colIdx, frameHeight*rowIdx,
														   frameWidth*(colIdx+1), frameHeight*rowIdx,
														   frameWidth*(colIdx+1), frameHeight*(rowIdx+1) });
				mTextureCoordinates[idx].position(0);
				
				++idx;
			}
		}
		
		bb = ByteBuffer.allocateDirect(6*2);
		bb.order(ByteOrder.nativeOrder());
		
		mDrawOrder = bb.asShortBuffer();
		mDrawOrder.put(new short[] { 0, 1, 2, 0, 2, 3 });
		mDrawOrder.position(0);
		  
		mColor = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		
		setTextureFrameIdx(0);
	}
	
	public float getWidth() { return this.mWidth; }
	
	public float getHeight() { return this.mHeight; }
	
	public void draw(float[] mvpMatrix){
		
		Matrix.translateM(mMVPMatrix, 0, mvpMatrix, 0, (int)mLeft, -(int)mTop, 0);
		
		Shaders.render(6, mVertices, mTextureCoordinates[mTextureFrameIdx], mDrawOrder, mTextureId, mColor, mMVPMatrix);
	}
	
	public void moveTo(float top, float left){
		this.mTop = top;
		this.mLeft = left;
	}
	
	public void setTextureFrameIdx(int idx){
		if ( 0 <= idx && idx < mTextureCoordinates.length ){
			mTextureFrameIdx = idx;
		}
		else{
			mTextureFrameIdx = 0;
		}
	}
}
