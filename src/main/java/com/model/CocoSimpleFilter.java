package com.model;


public abstract class CocoSimpleFilter extends CocoFilter{

	protected Object condition;
	CocoSimpleFilter(Object o){
		super();
		condition=o;
	}

}
