package com.model;
import java.util.Enumeration;
import java.util.Vector;


public class CocoCluster extends CocoInstance{
	
	protected Vector<CocoInstance> vectorDocuments;
	protected String label;
	protected boolean isFull;  // indica si el cluster contiene todos los documentos y no deja ni uno sin clusterizar.
	protected String clusterDetails;
	
	protected String clusterNumber;
	protected String display;
	protected int level;
	
	
	
	
	public CocoCluster(String clusterNumber){
		
		super();
		vectorDocuments = new    Vector<CocoInstance>();
		label = new String("");
		isFull=true; 			
		clusterDetails="";
//		this.clusterNumber=clusterNumber + " ";
		this.clusterNumber=clusterNumber;
		this.level=0;
		
	}
	
	public void addInstance(CocoInstance cocoInstance){
		vectorDocuments.addElement(cocoInstance);
	}
	
	
	@Override
	public String toString() {
		
//		System.out.println("------ CLUSTER ------------------------------------------------------------------------------");
		
//		display= "["  + clusterNumber  + "]" + getLabel() +  getClusterDetails() + "<br/> ";
		
		display = new String("");
		
		Enumeration<CocoInstance> e=vectorDocuments.elements();
		while( e.hasMoreElements() ){
			display = display + ((CocoInstance)e.nextElement()).toString();
//			System.out.println();
		}
//		System.out.println(display);
		return display;
		
	
	}
	
	public String getNumber() {
		return this.clusterNumber;
	}
	
	public int getSize() {
		return vectorDocuments.size();
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = "<li><a href=\"showResult.action?cluster="+ this.clusterNumber+"\">" + label + "</a></li>";
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
	public String getClusterDetails() {
		return clusterDetails;

	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setClusterDetails(String clusterDetails) {
		this.clusterDetails = "<B>" +  clusterDetails +  "</B>";
	}


}
