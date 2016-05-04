<%@page import="com.view.Indexer"%>
<% session.removeAttribute("taskSearch"); %> 

<jsp:useBean id="taskSearch" scope="session"
    class="com.view.Search">
</jsp:useBean>

<%
 	Object o = session.getAttribute("task");
 	if(o != null) {
 		int radioField = Integer.parseInt(request.getParameter("field"));
 		String field = new String();
 		if(radioField == 1) field = "title";
 		else if(radioField == 2) field = "contents";
 		else field = "filename";
 		taskSearch.setCampos(request.getParameter("allWords").toString(), request.getParameter("phrase").toString(), request.getParameter("anyWords").toString(), request.getParameter("withOut").toString(), field);
 		taskSearch.setIndexDir(((Indexer)o).getIndexDir());
 		taskSearch.work();
 		response.sendRedirect("showResult.action");
 	}		

 	else {
		session.setAttribute("error", "No Cargo o genero un Indice!");
	 	response.sendRedirect("advancedSearch.action");
 	}
%>
