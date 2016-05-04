package com.model;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;


public class SearchTestCase {

	String campoDeBusqueda ="contents";
	String buscarFrase ="";
	String buscarPalabras="review";
	String buscarAlgunasPalabras="";//"rain andy";
	String buscarNingunaPalabras="";
	CocoQueryFactory fabricaConsultas;
	CompositeCocoQuery queryCompuestaBuscar;
	
	
	
	public SearchTestCase(Version version,Analyzer analyzador,String campoDeBusqueda,String frase,
																					 String todasPal,String algunasPal,String ningunaPal){
		
		this.campoDeBusqueda=campoDeBusqueda;
		this.buscarFrase=frase;
		this.buscarPalabras=todasPal;
		this.buscarAlgunasPalabras=algunasPal;
		this.buscarNingunaPalabras=ningunaPal;
		
		fabricaConsultas = new CocoQueryFactory(campoDeBusqueda,version, analyzador);
		queryCompuestaBuscar =(CompositeCocoQuery) fabricaConsultas.getCompositeCocoQuery();
		
	}			
	
	public Query getQuery(){
		
		/*-----------------------------------------------CONSULTAS POR FRASE -----------------------*/
		

		if (!buscarFrase.isEmpty() ){
			SimpleCocoQuery  consultaFrase = (SimpleCocoQuery) fabricaConsultas.getPhraseQuery(buscarFrase); 
			//queryBusqueda = consultaFrase.buildQuery();
			queryCompuestaBuscar.addQuery(consultaFrase,Occur.MUST);
		}
		
		
		
		
		/*-----------------------------------------------CONSULTAS POR TODAS LAS PALABRAS -----------------------*/
		
		
		if (!buscarPalabras.isEmpty()){
			SimpleCocoQuery consultaTodasPalabras = (SimpleCocoQuery) fabricaConsultas.getFullWordsQuery(buscarPalabras); 
//			queryBuscar = consultaTodasPalabras.buildQuery();
			queryCompuestaBuscar.addQuery(consultaTodasPalabras,BooleanClause.Occur.MUST);
		}
		
		
		
		/*-----------------------------------------------CONSULTAS POR ALGUNAS -----------------------*/
		
		
		if ( !buscarAlgunasPalabras.isEmpty()	){
			SimpleCocoQuery  consultaAlgunas = (SimpleCocoQuery) fabricaConsultas.getOptionalWordsQuery(buscarAlgunasPalabras); 
			//queryBusqueda = consultaAlgunas.buildQuery();
			queryCompuestaBuscar.addQuery(consultaAlgunas, BooleanClause.Occur.SHOULD);
		}
		
		
		

		/*-----------------------------------------------CONSULTAS POR NINGUNA -----------------------*/
		
		if (!buscarNingunaPalabras.isEmpty() )	{	 
			SimpleCocoQuery  consultaNinguna = (SimpleCocoQuery) fabricaConsultas.getNoneWordsQuery(buscarNingunaPalabras); 
			//queryBusqueda = consultaNinguna.buildQuery();
			queryCompuestaBuscar.addQuery(consultaNinguna,BooleanClause.Occur.MUST_NOT); 
			
		}

		return queryCompuestaBuscar.buildQuery();
	}
}
