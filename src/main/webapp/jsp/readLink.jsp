<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= request.getParameter("link") %></title>
</head>
<body>	
<%		
	String link = request.getParameter("link"); 
// 	link = link.split("//")[1];
	
	File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

    try {
         archivo = new File (link);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
         String linea;
         while((linea=br.readLine())!=null)
            out.println(linea);
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }  
%>
</body>
</html>