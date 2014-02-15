package com.aukeman.f35game.model;

import com.aukeman.f35game.model.interfaces.IBoundingBox;

public abstract class Utilities {

	public static boolean overlaps(IBoundingBox bbox1, IBoundingBox bbox2){
		return ( bbox1.getLeft() < bbox2.getRight() &&
				 bbox2.getLeft() < bbox1.getRight()  &&
				 bbox1.getTop()  < bbox2.getBottom()&&
				 bbox2.getTop()  < bbox1.getBottom() ); 
	}
	
}
