<%@page import="com.view.Indexer"%>
<jsp:useBean id="task" scope="session"
    class="com.view.Indexer"/>
<% 
	if(task.isRunning() || task.isCompleted()) {
		Object o = request.getParameter("form");
		if(o == null)
			response.sendRedirect("statusIndex.action");
	} 
%>
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

		<script type="text/javascript">
			$(document).ready(function() {
				$("#radio").buttonset();
				var cookie = readCookie("status");
				if(cookie == 'load')
    				load();
				else generate();
			});
			
			function load() {
				$('#label1').hide('');
				$('#dataDir').hide('');
    			$('#btn_search1').hide('');
    			$('#btn_generate').hide('');
    			$('#btn_load').show('');
    			$('#generate').show('');
    			$('#load').hide();
    			createCookie('status','load',1);
			}
			
			function generate() {
				$('#label1').show('');
				$('#dataDir').show('');
    			$('#btn_search1').show('');
    			$('#btn_generate').show('');
    			$('#btn_load').hide('');
    			$('#generate').hide('');
    			$('#load').show();
    			createCookie('status','generate',1);
			}

			function createCookie(name,value,days) {
				if (days) {
					var date = new Date();
					date.setTime(date.getTime()+(days*24*60*60*1000));
					var expires = "; expires="+date.toGMTString();
				}
				else var expires = "";
				document.cookie = name+"="+value+expires+"; path=/";
			}

			function readCookie(name) {
				var nameEQ = name + "=";
				var ca = document.cookie.split(';');
				for(var i=0;i < ca.length;i++) {
					var c = ca[i];
					while (c.charAt(0)==' ') c = c.substring(1,c.length);
					if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
				}
				return null;
			}

			function eraseCookie(name) {
				createCookie(name,"",-1);
			}
			
			function goTo(url) {
				window.location=url+".action";	
			}
		</script>

        <title>Indexación</title>
    </head>
	<body id="main_body" >
	<div id="radio">
		<input type="radio" id="radio1" name="radio" onclick="goTo('indexForm')" checked="checked"/><label for="radio1">Indice</label>
		<input type="radio" id="radio2" name="radio" onclick="goTo('simpleSearch')" /><label for="radio2">Búsqueda</label>
	</div>
	<div id="prss">
	
		<img id="top" src=<s:url value='/img/top.png'/> alt="">
		<div id="form_container">
	
			<h1><a></a></h1>
			<form id="form_231820" class="appnitro"  method="post" action="">
				<div class="form_description">
					<h2>Indexación</h2>
					<p>Especificación de directorios</p>
				</div>						
				<ul >
					<li id="li_1" >
						<label id="label1" class="description" for="element_1">Directorio a Indexar</label>
						<div>
							<input id="dataDir" name="dataDir" class="element text mediummax" type="text" maxlength="255" value="<s:property value="dataDir" />" readonly="readonly"/> 
							<input id="btn_search1" name="action:dataDirPath" value="Buscar" type="submit" />
						</div><p class="guidelines" id="guide_1"><small>Especifique el directorio que contiene los archivos a Indexar. Puede buscar en sus directorios con el boton Buscar</small></p>
					</li>
					<li id="li_2">
						<label class="description" for="element_1">Directorio del Indice</label>
						<div>
							<input name="indexDir" class="element text mediummax" type="text" maxlength="255" value="<s:property value="indexDir" />" readonly="readonly"/> 
							<input name="action:indexDirPath" value="Buscar" type="submit" />
						</div><p class="guidelines" id="guide_2"><small>Especifique el directorio donde se guardara el Indice. Puede buscar en sus directorios con el boton Buscar</small></p>
					</li>
					
					<li>
							<% if(task.isIndexLoaded()) 
								out.println("Indice Cargado correctamente en \""+((Indexer)session.getAttribute("task")).getIndexDir()+"\"");
							%>
					</li>
			
					<li class="buttons">
						<input id="btn_generate" name="action:startIndex" value="   Generar Indice   " type="submit"/>
						<input id="btn_load" name="action:loadIndex" value="   Cargar Indice   " type="submit" />
						<a id="generate" href="javascript:generate();">Generar Indice</a>
						<a id="load" href="javascript:load();">Cargar Indice</a>
					</li>
				</ul>
			</form>	
			<div id="footer">
				Trabajo para <a href="javascript:window.open('http://www.exa.unicen.edu.ar/catedras/ayrdatos/index.html')">Análisis y recuperación de Información</a>
			</div>
		</div>
		<img id="bottom" src=<s:url value='/img/bottom.png'/> alt="">

	</div>
	</body>

</html>
