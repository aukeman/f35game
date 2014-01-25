package com.aukeman.f35game.view;


public class Background implements IDrawable {

	private int mTileIndices[][];
	
	private IDrawable mTiles[];
	
	private int mTilesHigh;
	
	private int mTilesWide;
	
	private float mTileWidthPixels;
	
	private float mTileHeightPixels;
	
	private float mTop;
	
	private float mLeft;
	
	public Background( int tilesHigh, int tilesWide, IDrawable tiles[] ){
		mTilesHigh = tilesHigh;
		mTilesWide = tilesWide;
		
		mTileWidthPixels = tiles[0].getWidth();
		mTileHeightPixels = tiles[0].getHeight();
		
		mTop = 0.0f;
		mLeft = 0.0f;
		
		mTiles = tiles;

		mTileIndices = new int[mTilesWide][];
		
		for ( int colIdx = 0; colIdx < mTilesWide; ++colIdx ){
			mTileIndices[colIdx] = new int[mTilesHigh];
			
			for ( int rowIdx = 0; rowIdx < mTilesHigh; ++rowIdx ){
				mTileIndices[colIdx][rowIdx] = -1;
			}
		}
	}

	public void setTile(int colIdx, int rowIdx, int tileIdx){
		mTileIndices[colIdx][rowIdx] = tileIdx;
	}
	
	public float getTop(){ 
		return mTop;
	}
	
	public float getLeft(){
		return mLeft;
	}
	
	public float getWidth(){
		return mTileWidthPixels*mTilesWide;
	}
	
	public float getHeight(){
		return mTileHeightPixels*mTilesHigh;
	}
	
	public void moveTo(float top, float left){
		mTop = top;
		mLeft = left;
	}
	
	public void draw(float[] mvpMatrix){

		for ( int colIdx = 0; colIdx < mTilesWide; ++colIdx ){
			for ( int rowIdx = 0; rowIdx < mTilesHigh; ++rowIdx ){

				int tileIdx = this.mTileIndices[colIdx][rowIdx];
				
				if ( 0 <= tileIdx && tileIdx < this.mTiles.length ){

					this.mTiles[tileIdx].moveTo(mTop+rowIdx*mTileHeightPixels, mLeft+colIdx*mTileWidthPixels); 
					this.mTiles[tileIdx].draw(mvpMatrix);
				}
			}
		}
	}
}
