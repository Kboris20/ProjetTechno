<%-- 
    Document   : ajouterCompte
    Created on : 11 déc. 2012, 13:36:02
    Author     : christop.francill
--%>

<%@page import="servlets.HtmlHttpUtils"%>
<%@page import="utilities.WebUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    WebUtilities.doHeader(out, "Ajouter un compte", request, "ajouterCompte");
    if (HtmlHttpUtils.isAuthenticate(request)) {
        int id = Integer.parseInt(request.getParameter("idCli"));
%>

<form id="form1" name="form1" method="post" action="addCompte">
    <input type="hidden" name="clientId" id="clientId" value="<%= id%>"/>
    <table>
        <tr>
            <td>
                <label for="nom">Nom</label>
            </td>
            <td style="text-align:center">
                <input type="text" name="nom" id="nom" />
            </td>
        <br/>

        </tr>
        <tr>
            <td>
                <label for="solde">Solde</label>
            </td>
            <td style="text-align:center">
                <input type="text" name="solde" id="solde" />
            </td>
        <br/>
        </tr>
        <tr>
            <td>
                <label for="taux">Taux</label>
            </td>
            <td style="text-align:center">
                <input type="text" name="taux" id="taux" />
            </td>

        </tr>
    </table>
    <p>
        <button class="btn btn-primary" type="submit"><i class="icon-white icon-plus"></i> Ajouter</button>
        <button class="btn btn-success" type="reset"><i class="icon-white icon-refresh"></i> Vider le formulaire</button>
        <a href="afficherClient?idCli=<%= id%>" class="btn btn-inverse"><i class="icon-white icon-share-alt"></i> Retour au client</a>
    </p>
</form>

<%
    } else {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    WebUtilities.doFooter(out, "ajouterCompte");
%>