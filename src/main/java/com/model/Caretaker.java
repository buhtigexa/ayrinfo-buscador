package com.model;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;


public class Caretaker implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Hashtable<String, Memento> states;
	
	public Caretaker(){
		
		states = new Hashtable<String, Memento>();
		
	}
	
	public Memento getLast(){
		
		Memento memento = new Memento("",0,0,"");
		String lastKey=new String();
		Enumeration<String> e = states.keys();
		int size = states.size();
		int i=0;
		while ( (e.hasMoreElements()) &&  i <= (size-1)){
			i++;
			lastKey=e.nextElement();
		}
		if ( states.containsKey(lastKey)	)
				memento=states.get(lastKey);
		return memento;
		
	}
	
	public boolean contains (String key	){
		
		return states.containsKey(key.toLowerCase());
	}
	
	public void set(Memento m){
		
		 if (!contains(m.getFileName()))
			 states.put(m.getFileName().toLowerCase(), m);
	}
}
