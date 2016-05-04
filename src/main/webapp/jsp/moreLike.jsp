<jsp:useBean id="taskSearch" scope="session"
    class="com.view.Search">
</jsp:useBean>

<%
	taskSearch.moreLike(request.getParameter("more"));
// 	out.print(request.getParameter("more"));
	response.sendRedirect("showResult.action");
%>

<%-- <% if(o != null) {%>   --%>
<%-- 	<jsp:forward page="resultForm.jsp"></jsp:forward> --%>
<%-- <% } else {%> --%>
<%-- 	<% session.setAttribute("error", "No Cargo o genero un Indice!"); %> --%>
<%-- 	<jsp:forward page="simpleForm.jsp"></jsp:forward> --%>
<%-- <% } %> --%>