<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        
        <style type="text/css">
        	.message {
				color: red; 				
 				text-align:center;
			}
			
			.center {
				text-align: center;
			}
        </style>
        
        <link rel="stylesheet" type="text/css" href="<s:url value='/css/view.css'/>" media="all"></link>
        <link href="<s:url value='/css/jquery-ui.css'/>" rel="stylesheet" type="text/css"></link>
        
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/view.js'/>"></script>
		<script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.min.js'/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery-ui.min.js'/>"></script>
        <script language="JavaScript" type="text/javascript" src="<s:url value='/js/jquery.blockUI.js'/>"></script>
        
        <script type="text/javascript">
			$(document).ready(function() {
				$("#radio").buttonset();
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
		            	document.forms["form_231820"].action="startAdvancedSearch.action";
		                document.forms["form_231820"].submit();	
		            } 
		        }); 
			}
		</script>
        
		<title>Búsqueda Avanzada</title>
	</head>
	
	<body id="main_body">
		<div id="radio">
			<input type="radio" id="radio1" name="radio" onclick="goTo('indexForm')" /><label for="radio1">Indice</label>
			<input type="radio" id="radio2" name="radio" onclick="goTo('simpleSearch')" checked="checked"/><label for="radio2">Búsqueda</label>
		</div>
		<img id="top" src=<s:url value='/img/top.png'/> alt="">
		<div id="form_container">	
			<h1><a>Búsqueda Avanzada</a></h1>
			<form id="form_231820" class="appnitro"  method="post" action="javascript:search();">
				<div class="form_description">
					<h2>Búsqueda Avanzada</h2>
					<p></p>
				</div>						
				<ul>
					<li id="li_2" >
						<label class="description" >Con TODAS las palabras </label>
						<div>
<%-- 							<s:textfield name="allWords" cssClass="element text large" cssStyle="element text large"/> --%>
							<input name="allWords" class="element text large" type="text" maxlength="255" /> 
						</div><p class="guidelines" id="guide_2"><small>El resultado serán archivos que contengan todas las palabras que especifico</small></p> 
					</li>
					<li id="li_3" >
						<label class="description" for="element_3">Con la FRASE EXACTA </label>
						<div>
							<input name="phrase" class="element text large" type="text" maxlength="255" /> 
						</div><p class="guidelines" id="guide_3"><small>El resultado serán los archivos que contenga texto con la frase tal cual como lo especifico</small></p> 
					</li>
					<li id="li_4" >
						<label class="description" for="element_4">Con ALGUNA de las palabras </label>
						<div>
							<input name="anyWords" class="element text large" type="text" maxlength="255" /> 
						</div><p class="guidelines" id="guide_4"><small>El resultado serán archivos que contengan al menos una de las palabras que especifico</small></p> 
					</li>		
					<li id="li_5" >
						<label class="description" for="element_5">Sin las PALABRAS </label>
						<div>
							<input name="withOut" class="element text large" type="text" maxlength="255" /> 
						</div><p class="guidelines" id="guide_5"><small>El resultado serán archivos que no contengan las palabras que especifico</small></p> 
					</li>		
					<li id="li_6" >
						<label class="description" for="element_6">Buscar en: </label>
						<span>
							<input name="field" class="element radio" type="radio" value="1" />
							<label class="choice" for="element_7_1">Título</label>
							<input name="field" class="element radio" type="radio" value="2" CHECKED/>
							<label class="choice" for="element_7_2">Contenido</label>
							<input name="field" class="element radio" type="radio" value="3" />
							<label class="choice" for="element_7_3">Dirección del Documento</label>
						</span> <p class="guidelines" id="guide_5"><small>El resultado serán archivos que contengan las palabras que especifico en el campo indicado</small></p>
						<span>
						<div class="message">
							<% String error = (String)session.getAttribute("error"); %>
							<% if(error != null && !error.isEmpty()) { %>
								<p class="message"><%= error %></p>
							<% } %>
						</div>
						</span>
					</li>			
					<li class="buttons">			    
						<input type="button" onclick="search();" name="startAdvancedSearch" value="   Buscar   " />
						<input type="button" onclick="javascript:goTo('simpleSearch')" name="simpleSearch" value="   Búsqueda Simple   " />
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