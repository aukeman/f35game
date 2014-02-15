package com.aukeman.f35game.interfaces;

public interface IBoundingBox {

	public abstract float getTop();

	public abstract float getLeft();

	public abstract float getBottom();

	public abstract float getRight();

	public abstract boolean overlaps(IBoundingBox other);

}