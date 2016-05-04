package com.model;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;



public abstract class CocoIndexer extends Observable implements Runnable{


		protected Directory dir ;
		protected IndexWriter writer;

		protected IndexSearcher searcher;
		
		int indexeds;
		
		protected IndexReader reader,newReader;
		protected CocoFileFilter filter;
		protected Analyzer analyzer;
		
							// DATOS DEL INDEXADOR
		
		protected boolean isIndexing;
		private boolean isCompleted;
		protected String currentFile;
		protected String dataDir;
		protected String indexDir;
		protected int totalFiles;
		protected Document doc;
		protected Memento memento=new Memento();
		
		
		public CocoIndexer(Analyzer analyzer,String indexDir,String dataDir,CocoFileFilter fileFilter) {
			super();
			
			setUp(analyzer,1,new String(),indexDir,dataDir,fileFilter);
		}
		
		public CocoIndexer() {
			
		}
		
		public boolean isCompleted() {
			return this.isCompleted;
		}
		
		public  CocoIndexer(Analyzer analyzer){
		
			setUp(analyzer,1,new String(),new String(),new String(),new CocoFileFilter());
		}
		
		protected void setUp(Analyzer analyzer, int indexeds,String currentFile,String indexDir,String dataDir,CocoFileFilter fileFilter){
				
			indexeds = 1;
			currentFile=new String();
			this.indexDir=indexDir;
			this.dataDir=dataDir;
			this.filter=fileFilter;
			totalFiles=0;
			setIndexing(false);
			setUpIndex(analyzer,indexDir);
			this.analyzer=analyzer;
			this.isCompleted=false;
		
		}
		
		public int getTotalFiles() {
			return totalFiles;
		}

		@SuppressWarnings("deprecation")
		protected void setUpIndex(Analyzer analyzer,String indexDir){
			
			this.indexDir = indexDir; 
				try {
					dir = FSDirectory.open(new File(indexDir));
					writer = new IndexWriter(dir,analyzer,IndexWriter.MaxFieldLength.UNLIMITED );
				
				} 
				
				catch (Exception e) {
					e.printStackTrace();
				}
				
		}
		
			
		public int index(String dataDir)	 {
			 
			setIndexing(true);
			File [] files = new File(dataDir).listFiles();
			
			try{
				this.totalFiles=files.length ;
				 for (File f: files) {					
						if ( filter.accept(f) ) { 
							indexFile(f);
							indexeds++;
							
						}
				 }
			}
				catch (Exception e)	{
										e.printStackTrace();	
									}
				
				finally {   	setIndexing(false); 	this.isCompleted=true;		}
	//		System.out.println(this);
			return indexeds;
		}	
			
		protected void indexFile(File f)  {
			
				try {
					 doc = getDocument(f);
					 writer.addDocument(doc);
				     memento = new Memento(f.getCanonicalPath(),indexeds,totalFiles,dataDir);
//				     setChanged();
//				  	 notifyObservers(memento);
					} 
			   catch (IOException e) {	e.printStackTrace(); }
			   
			}

		public void cancel() {		
			this.setIndexing(false);		
		}
		
		
		public void close() {
			try {
				writer.optimize(false);
				writer.close();
//				setChanged();
//				notifyObservers();
			}
			catch(Exception e){ 
				
				System.out.println("Error al cerrar el indice  :"  +  indexDir);
				e.printStackTrace();
			}
		}
		
		
		public synchronized boolean isIndexing() {
			return isIndexing;
		}

		public synchronized void setIndexing(boolean isIndexing) {
			this.isIndexing = isIndexing;
		}

		protected abstract Document getDocument(File f);
		
			public IndexWriter getWriter() {
			return writer;
		}

		public String toString(){
			try {
				@SuppressWarnings("deprecation")
				TermEnum terms = writer.getReader().terms();
				while (terms.next())
					System.out.println(terms.term().toString());
			} catch (IOException e) {
					e.printStackTrace();
			}
			return currentFile;
			
		}
	
		public CocoFileFilter getFileFilter() {
			return filter;
		}
		
		public Memento getMemento() {
			return this.memento;
		}
		
		public void run() {
			
		}
		
	}

	
