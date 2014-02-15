package com.aukeman.f35game.model;

import java.util.List;
import java.util.Queue;

import android.content.Context;

import com.aukeman.f35game.IFrameInfo;
import com.aukeman.f35game.R;
import com.aukeman.f35game.model.interfaces.IPath;
import com.aukeman.f35game.model.interfaces.IUpdatable;
import com.aukeman.f35game.model.path.CompositePath;
import com.aukeman.f35game.model.path.ShootingSegment;
import com.aukeman.f35game.model.path.StraightSegment;
import com.aukeman.f35game.model.path.TurnSegment;
import com.aukeman.f35game.view.BadguyView;
import com.aukeman.f35game.view.BulletView;
import com.aukeman.f35game.view.Sprite;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class BadguyFactory implements IUpdatable {

	private List<BadguyView> mBadguyPool;

	private List<BulletView> mBadguyBulletPool;
	
	private CompositePath mPath;
	
	private IDrawable mSprite;
	
	private List<Long> mBadguyTimes;
	
	int mNextBadguyIndex;
	
	public BadguyFactory(Context context, List<BadguyView> badguyPool, List<BulletView> badguyBulletPool, List<Long> badguyTimes){
		this.mBadguyPool = badguyPool;
		this.mBadguyBulletPool = badguyBulletPool;
	
		mPath = new CompositePath();

		mPath.addSegment(new StraightSegment(180.0f, 40f, 100f));
		mPath.addSegment(new ShootingSegment() );
		mPath.addSegment(new TurnSegment(180.0f, -90.0f, 40f, 25));

		mPath.addSegment(new StraightSegment(90.0f, 40f, 50f));
		mPath.addSegment(new ShootingSegment() );
		mPath.addSegment(new TurnSegment(90.0f, -90.0f, 40f, 25));
		
		mPath.addSegment(new StraightSegment(0.0f, 40f, 50f));
		mPath.addSegment(new ShootingSegment() );
		mPath.addSegment(new TurnSegment(0.0f, -90.0f, 40f, 25));
		
		mPath.addSegment(new StraightSegment(270.0f, 40f, 50f));
		mPath.addSegment(new ShootingSegment() );
		mPath.addSegment(new TurnSegment(270.0f, -90.0f, 40f, 25));

		mPath.addSegment(new StraightSegment(180.0f, 40f, 200f));
		
		mSprite = new Sprite(context, 16, 16, R.drawable.sprite, 2, 2);
		
		mBadguyTimes = badguyTimes;

		mNextBadguyIndex = 0;
	}
	
	
	@Override
	public void update(IFrameInfo frameInfo) {

		while ( mNextBadguyIndex < mBadguyTimes.size() &&
				mBadguyTimes.get(mNextBadguyIndex) <= frameInfo.getTopOfFrame() ){
			
			boolean availableBadguyFound = false;
			for ( int idx = 0;
				  idx < mBadguyPool.size();
				  ++idx ){
				
				BadguyView badguy = mBadguyPool.get(idx);
				
				if ( !badguy.isActive() ){
					
					badguy.activate(frameInfo, mSprite, -16, 100, mPath);
					
					mNextBadguyIndex += 1;
					availableBadguyFound = true;
					break;
				}
			}
			
			if ( !availableBadguyFound ){
				break;
			}
			
		}
		
	}

}
