<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="task" scope="session"
    class="com.view.Indexer"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        
        <link rel="stylesheet" type="text/css" href="<s:url value='/css/view.css'/>" media="all"></link>
        <link href="<s:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"></link>
        
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/view.js'/>"></script>
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.min.js'/>"></script>
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-ui.min.js'/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.blockUI.js'/>"></script>
		
		<script>
			$(document).ready(function() {
				$("#radio").buttonset();
				$.unblockUI();
			});
			
			function goTo(url) {
				window.location=url+".action";	
			}
			
			function search() {
				$.blockUI({
					message: '<p><img src=<s:url value='/img/AjaxBusy.gif'/> /></p>',
		            fadeIn: 2000, 
		            timeout:   3000,
		            onBlock: function() { 
		            	document.forms["form_231820"].action="startSimpleSearch.action";
		                document.forms["form_231820"].submit();
		            } 
		        });
			}
		</script>
		
		<title>Búsqueda Simple</title>
	</head>

	<body id="main_body">
		<img src=<s:url value='/img/AjaxBusy.gif'/>  style="display:none"/>
		<div id="radio">
			<input type="radio" id="radio1" name="radio" onclick="goTo('indexForm')" /><label for="radio1">Indice</label>
			<input type="radio" id="radio2" name="radio" onclick="goTo('simpleSearch')" checked="checked"/><label for="radio2">Búsqueda</label>
		</div>
		<img id="top" src=<s:url value='/img/top.png'/> alt="">
		<div id="form_container">	
			<h1><a>Búsqueda Simple</a></h1>
			<form id="form_231820" class="appnitro"  method="post" action="javascript:search();">
				<div class="form_description">
					<h2>Búsqueda Simple</h2>
					<p></p>
				</div>						
				<ul>			
					<li id="li_1" >
						<label class="description" for="element_1">Palabras a buscar </label>
						<div>
							<input name="allWords" class="element text large" type="text" maxlength="255" value=""/> 
						</div><p class="guidelines" id="guide_1"><small>Ingrese el texto que desea buscar</small></p> 
					</li>
					<div class="message">
							<% String error = (String)session.getAttribute("error"); %>
							<% if(error != null && !error.isEmpty()) { %>
								<p class="message"><%= error %></p>
							<% } %>
						</div>
					<li class="buttons">
						<input id="saveForm" type="button" name="startSimpleSearch" onclick="search();" value="   Buscar   " />
						<input id="advancedSearch" type="button" name="advancedSearch" onclick="javascript:goTo('advancedSearch')" value="   Búsqueda Avanzada   " />
					</li>
				</ul>
			</form>	
			<div id="footer">
				Trabajo para <a href="javascript:window.open('http://www.exa.unicen.edu.ar/catedras/ayrdatos/index.html')">Análisis y recuperación de Información</a>
			</div>
		</div>
		<img id="bottom" src=<s:url value='/img/bottom.png'/> alt="">
	</body>
</html>