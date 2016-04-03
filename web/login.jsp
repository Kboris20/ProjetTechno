<%-- 
    Document   : login
    Created on : 6 janv. 2010, 14:19:14
    Author     : termine
--%>

<%@page import="utilities.WebUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
    WebUtilities.doHeader(out, "Connection - gestion des personnes (CRUD)", request, "logging");
%>
<form method="POST" action="login">
    username : <input type="text" name="username"><br>
    password : <input type="password" name="password"><br>

    <input type="submit" value="Se connecter">
</form>
<%
    WebUtilities.doFooter(out, "logging");
%>
