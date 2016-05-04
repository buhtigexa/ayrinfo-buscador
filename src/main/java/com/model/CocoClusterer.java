package com.model;
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2011, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */



import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;


public class CocoClusterer implements Observer {


	protected   ArrayList<Document> documents;
	protected 	Controller controller;
	protected 	int call;
	protected Document document;
	protected boolean isClustering;
	
	protected Vector<CocoCluster> vectorCluster;
	
	private long startTime=0, endTime=0;

	public  CocoClusterer(){
				
			documents= new ArrayList<Document>();
			call=0;
		    controller = ControllerFactory.createSimple();
	}   
  	
//	@Override
	public void update(Observable o, Object arg) {

  		if (call == 0)	documents.clear();
		
  		CocoDocument cocoDocument = ((CocoDocument)arg);

//		System.out.println("Title : "  +  CocoDocument.get("title") );
//		System.out.println("Summary :"  +  CocoDocument.getSummary("contents") );
//		System.out.println("Filename :" +  CocoDocument.get("filename"));
  		
		document = new Document( cocoDocument.get("title"),cocoDocument.get("summary"),cocoDocument.get("filename") );
		document.setField("idDoc",(String)cocoDocument.get("idDoc"));
		documents.add(document);
		call++;
	
	}
  	
	public void makeClusters(){
    
    	CocoFormatter2 formatter = new CocoFormatter2();
		
    	final ProcessingResult byTopicClusters = controller.process(documents,null,LingoClusteringAlgorithm.class);
    	final List<Cluster> clustersByTopic = byTopicClusters.getClusters();
        	
    	setClustering(true);
    	this.startTime = System.currentTimeMillis();
    	formatter.displayClusters(clustersByTopic);
       	
    	vectorCluster=formatter.getClusters();
       	this.endTime = System.currentTimeMillis();
       	setClustering(false);       	      	
    }
	
	public long getDelayClusterer() {
		return (this.endTime - this.startTime);
	}
	
	public synchronized boolean isClustering(){
		return isClustering;
	}
	
	public synchronized void setClustering(boolean isClustering){
			this.isClustering=isClustering;
		
	}
	
	public CocoCluster getCuster(int cluster) {
		return this.vectorCluster.get(cluster); 
	}
	
	public  Vector<CocoCluster> getVectorCluster() {
		if (!isClustering()	)
				return vectorCluster;
		return null;
	}

}


