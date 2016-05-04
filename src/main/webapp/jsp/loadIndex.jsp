<% session.removeAttribute("task"); %>

<jsp:useBean id="task" scope="session"
    class="com.view.Indexer">
</jsp:useBean>

<%
 	task.setIndexDir(request.getAttribute("indexDir").toString());
	session.setAttribute("error", "");
	response.sendRedirect("indexForm.action");
%>

<%-- <jsp:forward page="indexForm.jsp"></jsp:forward> --%>