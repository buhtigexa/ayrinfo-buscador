package com.model;

public abstract class CocoInstance {
	
	protected int level;
	
	

	public CocoInstance() {
	
	}
	
	
	public abstract String toString();

	protected String getIndent(int level)
	 {
	   StringBuilder indent = new StringBuilder();
	   for (int i = 0; i < level; i++)
	        {
	            indent.append("  ");
	        }

	   return indent.toString();
	 }
	
	public void setLevel(int level) {
		this.level = level;
	}


}
