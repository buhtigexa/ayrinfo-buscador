package com.model;

public class Memento {
	
	protected String fileName;
	protected int 	 indexeds;
	protected int  progress;					// refleja el estado de avance de indexación.
	protected int totalfiles;
	protected String directory;

	public Memento() {
		this.fileName = "";
		this.indexeds = 0;
		this.totalfiles = 0;
		progress = 0;
		this.directory = "";
	}
	
	public Memento(String filename,int indexados,int totalfiles,String directory){
		
		this.fileName=filename;
		this.indexeds=indexados;
		this.totalfiles=totalfiles;
		progress = (indexeds*100)/this.totalfiles+1;
		this.directory=directory;
		
	}
	

	public String getFileName() {
		return fileName;
	}

	public int getIndexeds() {
		return indexeds;
	}

	public float getProgress() {
		return progress;
	}

	public int getTotalfiles() {
		return totalfiles;
	}
	
	public String getDirectory() {
		return directory;
	}


}
