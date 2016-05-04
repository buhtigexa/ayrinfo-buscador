<jsp:useBean id="task" scope="session"
    class="com.view.Indexer"/>

<% 
	task.setRunning(false);
	response.sendRedirect("statusIndex.action");
%>