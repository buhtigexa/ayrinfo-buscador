package com.view;

import java.util.Vector;

import org.apache.lucene.search.Query;

import com.model.CocoCluster;
import com.model.CocoClusterer;
import com.model.CocoConstraintSpace;
import com.model.CocoFilter;
import com.model.CocoQueryFactory;
import com.model.CocoScoreDocFilter;
import com.model.CocoSearcher;
import com.model.CocoTrueFilter;
import com.model.MoreLikeThisTestCase;
import com.model.SearchTestCase;
import com.model.SimpleCocoQuery;

public class Search extends TaskBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1615447644230905299L;
	private String campoDeBusqueda; //radio
	private String buscarFrase;
	private String buscarPalabras;
	private String buscarAlgunasPalabras;
	private String buscarNingunaPalabras;
	private CocoSearcher searcher;
	private CocoClusterer clusterer;
	private Query queryDeBusqueda;
	private Query queryResaltador;
	private CocoFilter condicion;
	private org.apache.lucene.search.Filter filtroDeEspacioDeBusqueda;

	public Search() {
		this.campoDeBusqueda = "";
		this.buscarFrase = "";
		this.buscarPalabras = "";
		this.buscarAlgunasPalabras = "";
		this.buscarNingunaPalabras = "";
	}
	
	public void setCampos(String c1, String c2, String c3, String c4, String c5) {
		this.buscarPalabras = c1.toLowerCase();
		this.buscarFrase = c2.toLowerCase();
		this.buscarAlgunasPalabras = c3.toLowerCase();
		this.buscarNingunaPalabras = c4.toLowerCase();
		this.campoDeBusqueda = c5;
	}

	public void work() {
		clusterer = new CocoClusterer();
		SearchTestCase searchTestCase = new SearchTestCase(version, analyzador, campoDeBusqueda, buscarFrase, buscarPalabras, buscarAlgunasPalabras, buscarNingunaPalabras);
		queryDeBusqueda  = searchTestCase.getQuery();
		CocoQueryFactory fabricaConsultas = new CocoQueryFactory( campoDeBusqueda,version, analyzador );
		SimpleCocoQuery cocoResaltador =(SimpleCocoQuery) fabricaConsultas.getHighLightQuery(buscarFrase, buscarPalabras, buscarAlgunasPalabras);
		queryResaltador = cocoResaltador.buildQuery();		
		searcher = new CocoSearcher(analyzador,null,indexDir,500);		
		CocoConstraintSpace acotadorDeEspacio = new CocoConstraintSpace(campoDeBusqueda, buscarFrase, buscarPalabras);
		filtroDeEspacioDeBusqueda = acotadorDeEspacio.getFilter();
		condicion=new CocoTrueFilter();
		searcher.addObserver(clusterer);
		searcher.testMatch(	queryDeBusqueda,queryResaltador,condicion,filtroDeEspacioDeBusqueda );		
		clusterer.makeClusters();
	}

	public int getSizeResult() {
		return searcher.getTotalHits();
	}
	
	public String getPage() {
		return searcher.getPage();
	}
	
	public int getSizeCluster(int cluster) {
		return ((CocoCluster)clusterer.getCuster(cluster)).getSize();
	}
	
	public String getDelaySearch() {
		return Long.toString(searcher.getDelaySearch());
	}
	
	public String getDelayClusterer() {
		return Long.toString(clusterer.getDelayClusterer());
	}
	
	public String getLabels() {
		String ret = new String("");
		Vector<CocoCluster> misClusters = clusterer.getVectorCluster();
		 for( int i=0; i < misClusters.size() ;i++) {
			 CocoCluster coco = (CocoCluster)misClusters.elementAt(i);
             ret += coco.getLabel().replaceAll("</a></li>", "") + " (" + coco.getSize() + ")" + "</a></li>";
		 }
		return ret;
	}
	
	public String getDocs(String cluster) {
		Vector<CocoCluster> misClusters = clusterer.getVectorCluster();
		return ((CocoCluster)misClusters.elementAt(Integer.parseInt(cluster)-1)).toString();
	}
	
	public void moreLike(String idDoc) {
		MoreLikeThisTestCase moreLikethis = new MoreLikeThisTestCase(searcher, Integer.parseInt(idDoc),30);
		condicion = new CocoScoreDocFilter(Integer.parseInt(idDoc));
		queryDeBusqueda =	moreLikethis.getQuery();
		searcher.testMatch(	queryDeBusqueda,queryResaltador,condicion,filtroDeEspacioDeBusqueda ) ;
		clusterer.makeClusters();
	}
	
	public void setIndexDir(String indexDir) {
		this.indexDir = indexDir;
	}
	
	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPercent() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void run() {
		work();
	}

}
