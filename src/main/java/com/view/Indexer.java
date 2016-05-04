package com.view;

import com.model.CocoFileFilter;
import com.model.CocoIndexer;
import com.model.HtmlIndexer;

public class Indexer extends TaskBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6322579916465067804L;
	private CocoIndexer indexador = new HtmlIndexer();
	private String dataDir;
	private String status = "No iniciado";
	private boolean indexLoaded = false;

	public String getIndexDir() {
		return indexDir;
	}
	
	
	public void setIndexDir(String indexDir) {
		this.indexLoaded = true;
		this.indexDir = indexDir;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}
	
	public synchronized Object getResult() {
//        if (isCompleted())
//        	return new String("Finalizado");
//       return null;
		return this.status;
    }
	
	public boolean isIndexLoaded() {
		return this.indexLoaded;
	}
	
	@Override
	public synchronized int getPercent() {
		return (int)indexador.getMemento().getProgress();
	}
	
	@Override
	public synchronized boolean isCompleted() {
//		return indexador.isCompleted();
		return (this.status == "Finalizado"); 
	}

	private void work() {
		this.status = "Iniciando...";
    	indexador = new HtmlIndexer(analyzador,indexDir,dataDir,new CocoFileFilter());
    	this.status = "Indexando...";
    	timer();
    	indexador.index(dataDir);
    	this.status = "Finalizado";
	}

	public void run() {
        try {
            setRunning(true);            
            while (isRunning() && !isCompleted())
                work();
        } finally {
            setRunning(false);
            indexador.close();
        }
    }

}
