<% session.removeAttribute("task"); %>

<jsp:useBean id="task" scope="session"
    class="com.view.Indexer" >
</jsp:useBean>

<%
 	task.setDataDir(request.getAttribute("dataDir").toString());
 	task.setIndexDir(request.getAttribute("indexDir").toString());
 	new Thread(task).start();
 	response.sendRedirect("statusIndex.action");
%>

<%-- <jsp:forward page="indexingForm.jsp"></jsp:forward> --%>