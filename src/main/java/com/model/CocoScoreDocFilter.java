package com.model;
import org.apache.lucene.search.ScoreDoc;


public class CocoScoreDocFilter extends CocoFilter{
	
	protected int idDocToFilter;
	
	public CocoScoreDocFilter(int idDoc) {
		idDocToFilter = idDoc;
	}
	
	@Override
	public boolean accept(Object obj) {
		return ( (((ScoreDoc)obj).doc) != this.idDocToFilter); 
	}

}
