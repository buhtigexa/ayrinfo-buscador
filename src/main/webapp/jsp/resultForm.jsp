<jsp:useBean id="taskSearch" scope="session"
    class="com.view.Search">
</jsp:useBean>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="<s:url value='/css/view.css'/>" media="all">
        <link href="<s:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"/>
        
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/view.js'/>"></script>
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.min.js'/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-ui.min.js'/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.blockUI.js'/>"></script>

        <title>Resultados de Búsqueda</title>
        
        <script type="text/javascript">
        	$(document).ready(function() {
				$("#radio").buttonset();		
			});
        	
        	function searchMore(cluster) {
				$.blockUI({
					message: '<p><img src=<s:url value='/img/AjaxBusy.gif'/> /></p>',
		            fadeIn: 2000, 
		            timeout:   4000,
		            onBlock: function() { 
		            	window.location="moreLike.action?more="+cluster;
		            } 
		        }); 
			}
        	
        	function search() {
				$.blockUI({
					message: '<p><img src=<s:url value='/img/AjaxBusy.gif'/> /></p>',
		            fadeIn: 2000, 
		            timeout:   13000,
		            onBlock: function() { 
		            	document.forms["form_231820"].action="startAdvancedSearch.action";
		                document.forms["form_231820"].submit();	
		            } 
		        }); 
			}
        	
        	function goTo(url) {
				window.location=url+".action";	
			}
        </script>
        
        <style type="text/css">
			#caja2 {
				position:absolute;
				width:57%;
				left:36%;
				background:#fff;
				text-align:left;
				margin-top: 20px;
			}
			
			#top_caja2 {
				position:absolute;
				width:57%;
				left:36%;
				height: 10px;
				margin-top: 10px;
			}
			
			#top_caja1 {
				position:absolute;
				width:24%;
				left:4%;
				height: 10px;
				margin-left: 5px;
				margin-top: 10px;
			}
			
			#bottom_caja1 {
				position:absolute;
				width:21%;
				left:1%;
				height: 10px;
				margin: auto 0;
			}
			
        	#caja1 {
        		position:absolute;
				width:24%;
				left:4%;
				background:#fff;
				text-align:left;
				margin-left: 5px;
				margin-top: 20px;
			}
			
			.centro {
				color: red; 				
 				text-align:center;
			}
			
        </style>

    </head>
    
	<body id="main_body" >
		<div id="radio">
			<input type="radio" id="radio1" name="radio" onclick="goTo('simpleSearch')" /><label for="radio1">Búsqueda Simple</label>
			<input type="radio" id="radio2" name="radio" onclick="goTo('advancedSearch')" /><label for="radio2">Búsqueda Avanzada</label>
		</div>
		<div id="prss">
			<img id="top_caja1" src=<s:url value='/img/top.png'/> alt="">
			<img id="top_caja2" src=<s:url value='/img/top.png'/> alt="">
			<div id="caja1">
				<h1><a></a></h1>
				<form id="form_231820" class="appnitro"  method="post" action="">
					<div class="form_description">
						<h2>Categorías</h2>						
					</div>
					<ul>
<!-- 					<a href="javascript:llenarDIV();">Prueba 1</a> -->
						<%= taskSearch.getLabels() %>
					</ul>
				</form>
			</div>
<!-- 			<img id="bottom_caja1" src=<s:url value='/img/bottom.png'/> alt=""> -->
		
			<div id="caja2">
				<h1><a></a></h1>
				<form id="form_231820" class="appnitro"  method="post" action="">
					<div class="form_description">
						<h2>Resultados de la Búsqueda</h2>
						<% String cluster = request.getParameter("cluster"); %>
						<% if(cluster != null) { %>
							<p>Hits encontrados: <%= taskSearch.getSizeCluster(Integer.parseInt(cluster)-1) %> &nbsp &nbsp Tiempo de Búsqueda: <%= taskSearch.getDelaySearch() %>msegs &nbsp &nbsp Tiempo de Categorización: <%= taskSearch.getDelayClusterer() %>msegs</p>
						<% } else { %>
							<p>Hits encontrados: <%= taskSearch.getSizeResult() %> &nbsp &nbsp Tiempo de Búsqueda: <%= taskSearch.getDelaySearch() %>msegs &nbsp &nbsp Tiempo de Categorización: <%= taskSearch.getDelayClusterer() %>msegs</p></p>
						<% } %>
					</div>
<!-- 					<div> -->
<!-- 						<p class="centro"><a href="javascript:history.go(-1)" class="centro">Atras</a></p> -->
<!-- 					</div> -->
					<ul>
					
					<% if(cluster != null) { %>
						<% if(!cluster.isEmpty()) { %>
							<%= taskSearch.getDocs(cluster) %>
						<% } %>
					<% } else {%>
						<%= taskSearch.getPage() %>
					<% } %>
					</ul>
<!-- 				<div> -->
<!-- 					<p class="centro"><a href="javascript:history.go(-1)" class="centro">Atras</a></p> -->
<!-- 				</div> -->
				</form>			
				<div id="footer">
					Trabajo para <a href="javascript:window.open('http://www.exa.unicen.edu.ar/catedras/ayrdatos/index.html')">Análisis y recuperación de Información</a>
				</div>
			</div>
<!-- 			<img id="bottom" src=<s:url value='/img/bottom.png'/> alt="">				 -->
		</div>
	</body>

</html>
