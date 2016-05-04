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


import java.text.NumberFormat;
import java.util.Collection;
import java.util.Observable;
import java.util.Vector;

import org.carrot2.core.Cluster;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;

/**
 * Simple console formatter for dumping {@link ProcessingResult}.
 */
public class CocoFormatter2 extends Observable
{
   protected CocoCluster superCluster;
   protected Vector<CocoCluster> vectorClusters;
	
   public CocoFormatter2(){
	   super();
	   superCluster=null;
	   vectorClusters = new Vector<CocoCluster>();
			   
   }
   
	public void displayResults(ProcessingResult processingResult)
    {
        final Collection<Document> documents = processingResult.getDocuments();
        final Collection<Cluster> clusters = processingResult.getClusters();
//        final Map<String, Object> attributes = processingResult.getAttributes();

        // Show documents
        if (documents != null)
        {
            displayDocuments(documents);
        }

        // Show clusters
        if (clusters != null)
        {
            displayClusters(clusters);
        }

    }

    public void displayDocuments(final Collection<Document> documents)
    {
        //System.out.println("Collected " + documents.size() + " documents\n");
        for (final Document document : documents)
        {
            displayDocument(0, document);
        }
    }

      public void displayClusters(final Collection<Cluster> clusters)
    {
        displayClusters(clusters, Integer.MAX_VALUE);
    }

    public void displayClusters(final Collection<Cluster> clusters, int maxNumberOfDocumentsToShow)
    {
    	ClusterDetailsFormatter clusterDetailsFormatter = new ClusterDetailsFormatter();
        displayClusters(clusters, maxNumberOfDocumentsToShow, clusterDetailsFormatter);
    }

    public  void displayClusters(final Collection<Cluster> clusters, int maxNumberOfDocumentsToShow, 
    		ClusterDetailsFormatter clusterDetailsFormatter)
    {
        //System.out.println("\n\nCreated " + clusters.size() + " clusters\n");
    	
    	if (vectorClusters!=null)
    		vectorClusters.clear();
    	else
    		vectorClusters=new Vector<CocoCluster>();
    	
    	int clusterNumber = 1;
        for (final Cluster cluster : clusters)
        {
        	superCluster = new CocoCluster(Integer.toString(clusterNumber)); 
            displayCluster(superCluster, 0, "" + clusterNumber++,cluster, maxNumberOfDocumentsToShow,
                clusterDetailsFormatter);
        }
    }

    protected CocoDocument displayDocument(final int level, Document document)
    {
    	//final String indent = getIndent(level);
        
    	
    	// aqui creo un documento con nivel de indentacion
        CocoDocument cocoDocument=new CocoDocument();
        
        cocoDocument.setLevel(level);
        
//        cocoDocument.set("filename", (String) document.getContentUrl());
        cocoDocument.set((String) document.getContentUrl());
        cocoDocument.set("title",    (String) document.getTitle() );
        cocoDocument.set("summary",  (String) document.getSummary());
        				
        		/* estos son campos privados que sirven para procesamiento */      				 
        		
        cocoDocument.set("idDoc",    (String) document.getField("idDoc")  );
        cocoDocument.set("idCluster", Integer.toString(document.getId()));
        
        
        /*
        		//System.out.printf(indent + "[%2d] ", document.getId());
        		//System.out.println(document.getField(Document.TITLE));
        	final String url = document.getField(Document.CONTENT_URL);
        	if (StringUtils.isNotBlank(url))    {
                   				//System.out.println(indent + "     " + url);
        	}
            	//System.out.println();
        */
        return cocoDocument;
        
    }

    private void displayCluster(CocoCluster superCluster,final int level, String tag, Cluster cluster,
        int maxNumberOfDocumentsToShow, ClusterDetailsFormatter clusterDetailsFormatter)
    {
        final String label = cluster.getLabel();

        
        superCluster.setLabel(label);
        superCluster.setLevel(level);
        superCluster.setClusterDetails(clusterDetailsFormatter.formatClusterDetails(cluster));
        
        for (int i = 0; i < level; i++)
        {
            //System.out.print("  ");
        }
        //System.out.println(label + "  " + clusterDetailsFormatter.formatClusterDetails(cluster));
        
        // le cargo documentos al cluster
        
        int documentsShown = 0;
        for (final Document document : cluster.getDocuments())
        {
            if (documentsShown >= maxNumberOfDocumentsToShow)
            {
                break;
            }
            superCluster.addInstance( displayDocument(level + 1, document) );
            documentsShown++;
        }
        if (maxNumberOfDocumentsToShow > 0
            && (cluster.getDocuments().size() > documentsShown))
        {
            //System.out.println(getIndent(level + 1) + "... and " + (cluster.getDocuments().size() - documentsShown) + " more\n");
        }

        //si este cluster tiene subcluster, me voy recursivamente.
        final int num = 1;
        for (final Cluster subcluster : cluster.getSubclusters())
        {
//           System.out.println("SUBCLUSRTER!!!!!!!!!!");
        	CocoCluster subCluster = new CocoCluster(tag + "." + num);
            superCluster.addInstance(subCluster);
        	displayCluster(subCluster,level + 1, tag + "." + num, subcluster,maxNumberOfDocumentsToShow, clusterDetailsFormatter);
        }
        setChanged();
        notifyObservers(superCluster);
        vectorClusters.add(superCluster);
//        System.out.println("UN CLUSTER COMPLETO :" +  superCluster.clusterNumber +  "  " );//  +  superCluster.toString() );
        
        
    }
    
    public Vector<CocoCluster> getClusters() {
    	return vectorClusters;
    }


     
//    private String getIndent(final int level)
//    {
//        final StringBuilder indent = new StringBuilder();
//        for (int i = 0; i < level; i++)
//        {
//            indent.append("  ");
//        }
//
//        return indent.toString();
//    }

    public class ClusterDetailsFormatter
    {
        

        protected NumberFormat numberFormat;

        public ClusterDetailsFormatter()
        {
            numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
        }

        public String formatClusterDetails(Cluster cluster)
        {
           // final Double score = cluster.getScore();
           // return "(" + cluster.getAllDocuments().size() + " docs"
            //    + (score != null ? ", score: " + numberFormat.format(score) : "") + ")";
        	return "(" + cluster.getAllDocuments().size() + ") documents";
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*public  void displayAttributes(final Map<String, Object> attributes)
    {
        //System.out.println("Attributes:");

        String DOCUMENTS_ATTRIBUTE = CommonAttributesDescriptor.Keys.DOCUMENTS;
        String CLUSTERS_ATTRIBUTE = CommonAttributesDescriptor.Keys.CLUSTERS;
        for (final Map.Entry<String, Object> attribute : attributes.entrySet())
        {
            if (!DOCUMENTS_ATTRIBUTE.equals(attribute.getKey())
                && !CLUSTERS_ATTRIBUTE.equals(attribute.getKey()))
            {
                //System.out.println("Atributo mostrado :" +  attribute.getKey() + ":   " + attribute.getValue());
            }
        }
    }
*/

}
