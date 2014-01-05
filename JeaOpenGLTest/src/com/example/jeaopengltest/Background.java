package com.example.jeaopengltest;

public class Background {

	private int mTileIndices[][];
	
	private Sprite mTileSprites[];
	
	private int mTilesHigh;
	
	private int mTilesWide;
	
	private int mTileWidthPixels;
	
	private int mTileHeightPixels;
	
	public Background( int tilesHigh, int tilesWide, int tileWidthPixels, int tileHeightPixels, Sprite tileSprites[] ){
		mTilesHigh = tilesHigh;
		mTilesWide = tilesWide;
		
		mTileWidthPixels = tileWidthPixels;
		mTileHeightPixels = tileHeightPixels;
		
		mTileSprites = tileSprites;

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
	
	public void draw(float[] mvpMatrix){

		for ( int colIdx = 0; colIdx < mTilesWide; ++colIdx ){
			for ( int rowIdx = 0; rowIdx < mTilesHigh; ++rowIdx ){

				int tileIdx = this.mTileIndices[colIdx][rowIdx];
				
				if ( 0 <= tileIdx && tileIdx < this.mTileSprites.length ){

					this.mTileSprites[tileIdx].moveTo(rowIdx*mTileHeightPixels, colIdx*mTileWidthPixels); 
					this.mTileSprites[tileIdx].draw(mvpMatrix);
				}
			}
		}
	}
}
