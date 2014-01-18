package com.aukeman.f35game.view;

import com.aukeman.f35game.R;
import android.content.Context;

public class Font {

	private Sprite mFontSprite;
	
	private int mCharacterLookup[];
	
	public Font(Context context){
		
		mFontSprite = new Sprite(context, 16, 16, R.drawable.font, 10, 4);

		mCharacterLookup = new int[127];
		for ( int idx = 0; idx < mCharacterLookup.length; ++idx ){
			mCharacterLookup[idx] = -1;
		}
		
		int frameIdx = 0;
		
		for ( char letter = 'a'; letter <= 'z'; ++letter ){
			mCharacterLookup[letter] = frameIdx++;
		}
		
		for ( char number = '0'; number <= '9'; ++number ){
			mCharacterLookup[number] = frameIdx++;
		}
		
		mCharacterLookup['.'] = frameIdx++;
		mCharacterLookup['?'] = frameIdx++;
		mCharacterLookup['!'] = frameIdx++;
		mCharacterLookup['-'] = frameIdx++;
	}
	
	public void drawString( float[] mvpMatrix, float top, float left, String s ){
		
		int row = 0;
		int column = 0;
		
		for ( int charIdx = 0; charIdx < s.length(); ++charIdx ){
			
			char c = s.charAt(charIdx);
			
			if ( c == '\n' ){
				++row;
				column = 0;
			}
			else{
				
				int frameIdx = getFrameIndex(c);
				
				if ( 0 < frameIdx ){
					mFontSprite.moveTo( top+row*mFontSprite.getHeight(), 
										left+column*mFontSprite.getWidth() );
					
					mFontSprite.setTextureFrameIdx(frameIdx);
					
					mFontSprite.draw(mvpMatrix);
				}
				
				++column;
			}
		}
	}
	
	private int getFrameIndex( char c ){

		// convert to lower case
		if ( 'A' <= c && c <= 'Z' ){
			c += ('a' - 'A');
		}
		
		if ( 0 <= c && c < mCharacterLookup.length ){
			return mCharacterLookup[c];
		}
		else{
			return -1;
		}
	}
}
