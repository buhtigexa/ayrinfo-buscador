package com.model;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class CocoSearcher extends Observable implements Runnable{
	
	protected IndexReader indexReader;
	protected IndexReader newReader;
	protected IndexSearcher indexSearcher;
	protected Directory		directory;
	protected Query			query;
	protected TopDocs 		topDocs;
	protected int recall;		
	protected Vector<CocoDocument> vectorSearchResult;
	protected int totalHits;
	public TopDocs getTopDocs() {
		return topDocs;
	}
	protected CocoFilter filter;
	protected boolean isSearching;
	private long startTime=0, endTime=0;
	
	

	protected CocoDocument cocoDocument;
	

	/*	SOLO SE PERMITE UNA INSTANCIA POR APLICACION, Y SE CIERRA .close() AL TERMINAR LA APLICACION!.
	 */
	
	@SuppressWarnings("deprecation")
	
	
												// NO AGREGAR EL CAMPO DE BUSQUEDA EN EL CONSTRUCTOR!!!!!!  

	public CocoSearcher(Analyzer analyzer,CocoIndexer indexer,String indexDir, int recall){

		vectorSearchResult = new Vector<CocoDocument>();
		totalHits = 0;
		newReader = null;
		this.recall = recall;
		try {	
		
			if ( (indexer != null) && indexer.isIndexing()  ) indexReader = indexer.getWriter().getReader();
			else 	{
								directory = FSDirectory.open(new File(indexDir));
								indexReader = IndexReader.open(directory);
			}
			
		}
		catch(Exception e )	{ 	
//			System.out.println("Directory-Searcher : " + directory.toString());
								e.printStackTrace();			}
		
	}
	
	public void testMatch (Query query,Query queryHighLight,CocoFilter condition,Filter filter){
		this.totalHits = 0;
		if ( vectorSearchResult==null	)
			vectorSearchResult = new Vector<CocoDocument>();
		else
			vectorSearchResult.clear();
			this.startTime = System.currentTimeMillis();
			System.out.println("----------------------\n\n\n"+this.startTime+"\n\n\n------------------");
		try		{
					
					setSearching(true);
					topDocs = this.getSearcher().search(query,filter,100);
					if (topDocs!=null) {
						
						totalHits = topDocs.totalHits;
						for  (ScoreDoc sd : topDocs.scoreDocs)  
							if ( condition.accept(sd) )	{
							
								cocoDocument = new CocoDocument( this.getSearcher().getIndexReader(), sd.doc,queryHighLight);
								vectorSearchResult.add(cocoDocument);
		
//						System.out.println("---------   DOCUMENT RETRIEVED ***********************");
//						System.out.println(cocoDocument);
								
						setChanged();
						notifyObservers(cocoDocument);
					}
					 setSearching(false);
				}
		}
		
		catch (Exception e){ e.printStackTrace();}
		finally { this.endTime = System.currentTimeMillis(); }
		System.out.println("----------------------\n\n\n"+this.endTime+"\n\n\n------------------");
	}
	
	public long getDelaySearch() {
		return (this.endTime - this.startTime);
	}
	
	protected synchronized IndexReader getReader() {
		
		 try {	 
				newReader = indexReader.reopen();
				if ( newReader != indexReader){
					indexReader.close();
					indexReader = newReader;
			 }
		 }
		 catch (Exception e) {   e.printStackTrace(); }
	  	 return indexReader;
	}
	
	
	public IndexSearcher getSearcher(){
		
		IndexReader myReader = this.getReader();
		indexSearcher = new IndexSearcher( myReader );
		return indexSearcher;
	
	}
	
	// Hacer el close SOLO cuando se cierra la aplicacion
	public void close(){
		try {
			indexSearcher.close();
			
		}
		 catch (IOException e) { e.printStackTrace(); 	}  
	}
	
	public int getTotalHits() {
		return this.totalHits;
	}
	
	public String getPage() {
		String page = new String("");
		for(Enumeration<CocoDocument> e = vectorSearchResult.elements(); e.hasMoreElements();)
			page += e.nextElement().toString();
		return page;
	}
	
	public synchronized boolean isSearching(){
			return isSearching;
	}
	
	protected synchronized void setSearching(boolean isSeaching){
			this.isSearching=isSeaching;
	}
	
	public void setRecall(int recall) {
		this.recall = recall;
	}
	
//	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	



}
