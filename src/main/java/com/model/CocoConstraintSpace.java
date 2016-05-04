package com.model;
import java.util.StringTokenizer;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.TermsFilter;


public class CocoConstraintSpace {
	
	protected StringTokenizer tokenizer;
	protected TermsFilter filter;
	protected int countTokens;
	
	
	public CocoConstraintSpace(String fieldWords,String phraseWords,String expectedWords){
	

		filter = null;
//		if ( phraseWords.isEmpty())
//				System.out.println("FRASE VACIA!!!!");
//		if (expectedWords.isEmpty())
//			System.out.println("PALABRAS VACIA!!!!");
		if ( (!phraseWords.isEmpty()) || (!expectedWords.isEmpty()) ){
//			System.out.println("***************************************************************NO SON VACIAS ======>>>> CREANDO EL FILTRO!!");
			filter=new TermsFilter();
			tokenizer=new StringTokenizer(expectedWords + " " +  phraseWords +  " " +  expectedWords,"? ! ¿ ¡ ' \\` ~. ; : * @     °| { [ ] < />- + , · # = ) - _ ( / & % $ #¨    ");
		String tempToken=new String();
		while (tokenizer.hasMoreTokens()){
			tempToken=tokenizer.nextToken();
			((TermsFilter) filter).addTerm(new Term(fieldWords,tempToken));
//			System.out.println(tempToken);
		}
	}	
	}
	
	public Filter getFilter(){
		return filter;
	}
	public int getCountTokens() {
		return tokenizer.countTokens();
	}

	
}