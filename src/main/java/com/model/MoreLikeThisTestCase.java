package com.model;
import java.io.IOException;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.similar.MoreLikeThis;



public class MoreLikeThisTestCase {

	protected int hitUserSelect;
	protected MoreLikeThis mltQuery;
	protected CocoFilter filtro;
	protected Query queryMlt;

	public MoreLikeThisTestCase(CocoSearcher searcher,int hitUserSelect,int recall){
		
		this.hitUserSelect=hitUserSelect;
		filtro=new CocoScoreDocFilter(hitUserSelect);
		mltQuery = new MoreLikeThis( searcher.getReader() );
		mltQuery.setFieldNames(new String[] {"author","title"});
		mltQuery.setMinTermFreq(1);
		mltQuery.setMinDocFreq(1);
		mltQuery.setBoostFactor(2);
		mltQuery.setBoost(true);
		
		try {
				queryMlt = mltQuery.like(hitUserSelect);
		} catch (IOException e) {	e.printStackTrace(); 	}

		searcher.setRecall(recall);
			
	}
	
	public Query getQuery(){
		
		return queryMlt;
	}


}
