package com.aukeman.f35game.view;

import com.aukeman.f35game.model.AbstractModel;
import com.aukeman.f35game.view.interfaces.IDrawable;

public class AbstractView implements IDrawable {

	private AbstractModel mModel;
	
	private IDrawable mDrawable;

	public AbstractView(){
		mModel = null;
		mDrawable = null;
	}
	
	public AbstractView(AbstractModel model, IDrawable drawable){
		mModel = model;
		mDrawable = drawable;
	}
	
	protected void setModel(AbstractModel model){
		this.mModel = model;
	}
	
	protected void setDrawable(IDrawable drawable){
		this.mDrawable = drawable;
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		mDrawable.moveTo(mModel.getTop(), mModel.getLeft());
		mDrawable.draw(mvpMatrix);
	}

	@Override
	public float getTop() {
		return mModel.getTop();
	}

	@Override
	public float getLeft() {
		return mModel.getLeft();
	}

	@Override
	public float getWidth() {
		return mModel.getWidth();
	}

	@Override
	public float getHeight() {
		return mModel.getHeight();
	}

	@Override
	public float getPriority() {
		return mDrawable.getPriority();
	}

	@Override
	public void setPriority(float priority) {
		mDrawable.setPriority(priority);
	}

	@Override
	public void moveTo(float top, float left) {
		mModel.moveTo(top, left);
	}
	
	public AbstractModel getModel(){
		return mModel;
	}
}
