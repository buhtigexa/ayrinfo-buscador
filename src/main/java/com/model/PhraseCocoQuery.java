package com.model;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

public class PhraseCocoQuery extends SimpleCocoQuery {
	
		//stringParsed = new String("\"\\" + "\""  + terms + "\\" + "\"\"");
	
		protected int tokens;
		
		public PhraseCocoQuery(Version matchVersion,String fieldToSearch,Analyzer analyzer,String lookFor,String prefix,String suffix) {
			super(matchVersion, fieldToSearch, analyzer, lookFor,prefix,suffix);
		}
		
		
		
		public String tokenize(){
				
				queryParser.setPhraseSlop(30);
				String parsed = new String("\"\\" + "\""    +  super.tokenize() + "*\\" + "\"\"");
				
//				System.out.println("String parseada :" + parsed);
				
				return parsed;
			
		}
		

	
}
