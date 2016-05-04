package com.model;

import java.io.StringReader;
import java.util.Hashtable;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;


public class CocoDocument extends CocoInstance {

		/*
		 *    Encapsula el contenido de hit relevante al query de busqueda
		 * 
		 * 
		 */
	
	protected Hashtable<String, String> documentFields;
	protected IndexReader indexReader;
	protected Document doc ;
	protected List<Fieldable> list; 	
	protected String fieldName;
	protected Query queryHighLight;
	protected String fieldValue;
	
	
	public CocoDocument(IndexReader indexReader, int idDoc ,Query query){
		
		super();
		level=0;
		queryHighLight=query;
		
		if (documentFields!=null)	documentFields.clear();
		
		documentFields = new Hashtable<String, String>();
		fieldName=new String();
		fieldValue=new String();
		try{
			this.indexReader = indexReader;
			doc = indexReader.document(idDoc);
			list =  doc.getFields();
			if (list!=null)
				for (int f=0;f < list.size();f++)	{
					fieldName = (	list.get(f)	).name();
					fieldValue = doc.get(fieldName);
						fieldValue=makeUpFields(fieldName, fieldValue);
					documentFields.put(	fieldName, fieldValue);
			}
			fieldValue=Integer.toString(idDoc);
			documentFields.put("idDoc",fieldValue);
			documentFields.put("summary",makeUpFields("summary", getSummary("contents")));
		}
		
		catch(Exception e)		{   e.printStackTrace();	}
	}
	
	
	public CocoDocument(){
		
		level=0;
		queryHighLight=null;
		if (documentFields!=null)	documentFields.clear();
			documentFields = new Hashtable<String, String>();
		
	}
	
	public String get(String fieldName){
		
		String fieldContents = new String(" ");
		if (	documentFields.containsKey(fieldName)		)
			fieldContents= documentFields.get(fieldName);
			
		return fieldContents;
	}
	
	public void set(String t) {
		this.fieldValue=t;
		documentFields.put("filename", fieldValue );
	}

	public void set	(String fieldName,String fieldValue){
		
		if (documentFields.containsKey(fieldName))
			documentFields.remove(fieldName);
		String fieldValueCleanses= fieldValue.replaceAll("<a|</a>|<b>|</b>|href=|<br/>|<B>|</B>","");
		fieldValueCleanses = fieldValueCleanses.replace("javascript:window.open('readLink.jsp?link=\"", "");
		fieldValueCleanses = fieldValueCleanses.replace("')>", "");
//		fieldValueCleanses = fieldValueCleanses.replace("\";>", "");
		fieldValue=makeUpFields(fieldName, fieldValueCleanses);
		documentFields.put(fieldName, fieldValue );
			
			
	}
	
	protected String getSummary(String fieldName) {
		
	String text =  get(fieldName);
	if (queryHighLight!=null)
		try	{	
			StringReader reader = 	new StringReader(text);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);
			TokenStream tokenStream = analyzer.tokenStream(fieldName,reader );
			QueryScorer scorer = new QueryScorer(queryHighLight,fieldName);
			Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
			Highlighter highlighter = new Highlighter(scorer);
			highlighter.setTextFragmenter(fragmenter);
			highlighter.setMaxDocCharsToAnalyze(209999999);
			text = highlighter.getBestFragment(tokenStream,text);
					
		}	
	catch (Exception e){			e.printStackTrace();	}
		return text;
	
	}
	
	
	public String get(String fieldName,int indentLevel){
		
		if (indentLevel < 0)
				indentLevel=0;
		String text = getIndent(indentLevel) + get(fieldName);
		return text;
	}
	
	public void setQueryHighLight(Query queryHighLight) {
		this.queryHighLight = queryHighLight;
	}

	

	@Override
	public String toString() {
		
		String display;
		
		
//		System.out.println("			-----				");
//		System.out.println("idDoc :" +  get("idDoc") +  " in Cluster : " +  get("idCluster"));
//		System.out.println("filename :" +  get("filename",level));
//		System.out.println("title :" +  get("title",level));
//		System.out.println("Summary:" +  get("summary",level) );
//		display = "<div>  ";
			display= "<li>\n"+ get("title",level) + "<br/>" + get("summary",level) + "<br/>" + get("filename",level) + " --> <a href=javascript:searchMore(" + get("idDoc") + ");>"+"More Like This"+"</a>" +"	<br/></li>" ;
//			display=display +   "   </div>";
			
		return display;
		
	}
	
	protected String makeUpFields(String fieldName,String fieldValue){
		if ( fieldName == "filename" )
//				fieldValue="<a href=javascript:window.open('readLink.jsp?link=" + fieldValue + "')>"+fieldValue+"</a>";
			fieldValue="<a href=javascript:window.open('readLink.action?link=" + fieldValue + "')>"+fieldValue+"</a>";
		if ( fieldName =="title")
				fieldValue= "<B>" + fieldValue + "</B>";
//		fieldValue=fieldValue + "<br/>";

		return fieldValue;
	}
	
	/*
	  public CocoDocument(org.apache.lucene.document.Document doc){
	 
				// REPETICION DE CODIGO maaaal!!! ==> REFACTORIZAR INMEDIATAMENTE ( si esta solucion es factible	 )
		level=0;
		
		documentFields = new Hashtable<String, String>();
		fieldName=new String();
		fieldValue=new String();
		
		list =  doc.getFields();
		if (list!=null)
			for (int f=0;f < list.size();f++)	{
				fieldName = (	list.get(f)	).name();
				fieldValue = doc.get(fieldName);
				if ( fieldName == "filename" )
					fieldValue="<a href=" + fieldValue + "</a>";
				fieldValue=fieldValue + "<br/>";
				documentFields.put(	fieldName,fieldValue);
			}
		}
*/
	
	
}
