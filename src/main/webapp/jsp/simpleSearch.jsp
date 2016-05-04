<%@page import="com.view.Indexer"%>
<% session.removeAttribute("taskSearch"); %>

<jsp:useBean id="taskSearch" scope="session"
    class="com.view.Search">
</jsp:useBean>

<%
 	Object o = session.getAttribute("task");
 	if(o != null) {	
 		taskSearch.setCampos(request.getParameter("allWords").toString(), "", "", "", "contents");
 		taskSearch.setIndexDir(((Indexer)o).getIndexDir());
 		taskSearch.work();
 	// 	new Thread(taskSearch).start();
 		response.sendRedirect("showResult.action");
 	} 	
 	else {
 		session.setAttribute("error", "No Cargo o genero un Indice!");
 		response.sendRedirect("simpleSearch.action");
 	}
%>