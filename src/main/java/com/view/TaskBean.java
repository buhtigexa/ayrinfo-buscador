package com.view;

import java.io.Serializable;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public abstract class TaskBean implements Runnable, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7992604734228728895L;
    private boolean started;
	private boolean running;	
	private String time = "--:--:--";
	
	protected Version version  = Version.LUCENE_33;
	protected Analyzer analyzador = new StandardAnalyzer(version);
	protected String indexDir;

	public abstract boolean isCompleted();
	public abstract int getPercent();
	
	public TaskBean() {
    	started = false;
    	running = false;
    }
	
    public String getTime() {
		return time;
	}
    
    protected void timer() {    	
    	Thread hilo = new Thread() {
    		public void run() {
    			int hora=0,min=0,seg=0;	
    			try	{
    				while(true && isRunning()) {
    					if(seg==59) { seg=0; min++; }
    					if(min==59) { min=0; hora++; }
    					seg++;
    					time = hora+":"+min+":"+seg;
    					Thread.sleep(1000);
    				}
    			} catch (Exception e) {
					// TODO: handle exception
				}
    		}
    	};
    	hilo.start();
    }

	public synchronized boolean isStarted() {
        return started;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
        if (running)
            started = true;
    }    

}
