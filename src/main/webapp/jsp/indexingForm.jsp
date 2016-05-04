<%@page import="org.apache.struts2.components.ElseIf"%>
<%@page import="org.apache.struts2.components.Else"%>
<jsp:useBean id="task" scope="session"
    class="com.view.Indexer"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

		<title>Indexando...</title>	

		<style type="text/css">
			.rojo {
				color: #FF0000;
 				clear: both;
 				margin: 5px 0;
			}
			
			.negro {
				color: #000000;
 				clear: both;
 				margin: 5px 0;
			}
			
			.green {
				color: green;
 				clear: both;
 				margin: 5px 0;
			}
			
			.blue {
				color: blue;
 				clear: both;
 				margin: 5px 0;
			}
			
			.space {
				margin: 2px 0;
			}
		</style>
        
        <title>Generando Indice</title>
        
        <% if (task.isRunning()) { %>
        <SCRIPT LANGUAGE="JavaScript">
            setTimeout("location='statusIndex.action'", 1000);
         </SCRIPT> 
    	<% } %>

		<script language="JavaScript" type="text/javascript">

			$(document).ready(function() {
				$("#radio").buttonset();
				up();
			});
			
			function up() {
				<% int percent = task.getPercent(); %>
				var val = <%= percent %>;
				var changer = document.getElementById('valor');
				changer.value = <%= percent %>;
				$('#progressbar').progressbar({ value: val });
			}
			
			function goTo(url) {
				window.location=url+".action";	
			}
			</script>
    </head>

	<body id="main_body" >
		<div id="radio">
			<input type="radio" id="radio1" name="radio" onclick="goTo('indexForm')" checked="checked"/><label for="radio1">Indice</label>
			<input type="radio" id="radio2" name="radio" onclick="goTo('simpleSearch')" /><label for="radio2">Búsqueda</label>
		</div>
		<img id="top" src=<s:url value='/img/top.png'/> alt="">
		<div id="form_container">	
			<h1><a>Estado</a></h1>
			<form id="form_231820" class="appnitro"  method="post" action="">
				<div class="form_description">
					<h2>Procesando Información...</h2>
					<p>Estado de la Indexación</p>
					<input id="valor" type="hidden" value="" />
				</div>					
				<ul >
					<% int back = 0; %>
					<% if (task.isRunning()) { %>
						<div class="rojo">Estado: Ejecutando</div>
					<% } else { %>
						<% if(task.isCompleted()) { %>
							<div class="green">Estado: Completado</div>							
							<% back = -2; %>
						<%} else if(!task.isStarted()) {%>
							<div class="blue">Estado: No iniciado</div>
						<%} else {%>
							<div class="blue">Estado: Cancelado</div>
							<% back = -3; %>
						<% }%>
					<%} %>
					<div class="negro" id="crono">Tiempo indexando: <%= task.getTime() %> </div>
					<div class="space">Procesado: <%= percent %>%</div>
					<div id="progressbar"></div>
					<li class="buttons">
					<% if (task.isRunning()) { %>
						<input id="cancelButton"  type="submit" name="action:stopIndex" value="   Cancelar   " />
					<% } else {%>
						<input id="goBack" type="button" name="as"  onclick="javascript:window.location='indexForm.action?form=1';" value="   Atras   " />
					<%} %>
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
