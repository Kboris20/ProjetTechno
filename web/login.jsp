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
    WebUtilities.doHeader(out, "ICM", "IG - Counts - Management", request, "logging");
    try {
        if (request.getParameter("error").equalsIgnoreCase("true")) {
            out.println("<div class=\"alert alert-error\">");
            out.println("Veuillez réessayer !");
            out.println("</div>");
        }
    } catch (Exception ex) {
    }
%>
<form method="POST" action="login" class="form-signin">
    <h3 class="h3 form-signin-heading"><span class="glyphicon glyphicon-user"></span> S'identifier</h3>
    <hr/>
    <br/>
    <table class="tablePageConnection">
        <tr>
        <label for="username" class="sr-only">Nom d'utilisateur</label>
        <td class="td"><input type="text" id="username" name="username" class="form-control" placeholder="Nom d'utilisateur" required autofocus></td>
        </tr>
        <tr>
        <label for="password" class="sr-only">Mot de passe  </label>
        <td class="td"><input type="password" id="password" name="password" class="form-control" placeholder="Mot de passe" required></td>
        </tr>
    </table>
    <br/>
    <br/>
    <button id="connectionButton" class="btn btn-lg btn-primary btn-block" type="submit">Se connecter</button>
</form>
<div id="divImgConnexion">
    <img id="imgStyle" src="http://localhost:8080/crud/theme/img/imgidentification.jpg" alt="image d'identificaiton"/>
</div>
<%
    WebUtilities.doFooter(out, "logging");
%>
