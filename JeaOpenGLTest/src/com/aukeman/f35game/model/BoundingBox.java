package com.aukeman.f35game.model;

public class BoundingBox {
	
		private float mTop;
		private float mLeft;

		private float mWidth;
		private float mHeight;
		
		public BoundingBox(float top, float left, float height, float width) {
			this.mTop = top;
			this.mLeft = left;
			this.mHeight = height;
			this.mWidth = width;
		}
		
		public float getTop() {
			return mTop;
		}
		
		public void setTop(float top) {
			this.mTop = top;
		}
		
		public float getLeft() {
			return mLeft;
		}
		
		public void setLeft(float left) {
			this.mLeft = left;
		}
		
		public float getWidth() {
			return mWidth;
		}
		
		public void setWidth(float width) {
			this.mWidth = width;
		}
		
		public float getHeight() {
			return mHeight;
		}
		
		public void setHeight(float height) {
			this.mHeight = height;
		}
		
		public float getBottom(){
			return this.mTop + this.mHeight;
		}
		
		public float getRight(){
			return this.mLeft + this.mWidth;
		}
		
		public boolean testCollision( BoundingBox other ){
			return ( this.getLeft() < other.getLeft() + other.getWidth() &&
					 other.getLeft() < this.getLeft() + this.getWidth()  &&
					 this.getTop()  < other.getTop() + other.getHeight()&&
					 other.getTop() < this.getTop() + this.getHeight() ); 
		}

		
}
