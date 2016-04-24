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
    WebUtilities.doHeader(out, "Ajouter un compte", "", request, "ajouterCompte");
    if (HtmlHttpUtils.isAuthenticate(request)) {
        int id = Integer.parseInt(request.getParameter("idCli"));
%>

<div class="row">
    <div class="col-sm-8">
         <form id="formAddAccount" name="formAddAccount" method="post" action="addCompte">
            <input type="hidden" name="clientId" id="clientId" value="<%= id%>"/>
            <table>
                <tr>
                    <td>
                        <label for="nom">Nom</label>
                    </td>
                    <td>
                        <input type="text" name="nom" id="nom" required/>
                    </td>
                <br/>

                </tr>
                <tr>
                    <td>
                        <label for="solde">Solde</label>
                    </td>
                    <td>
                        <input type="text" name="solde" id="solde" required/>
                    </td>
                <br/>
                </tr>
                <tr>
                    <td>
                        <label for="taux">Taux</label>
                    </td>
                    <td>
                        <input type="text" name="taux" id="taux" required/>
                    </td>

                </tr>
            </table>
            <p>
                <button class="btn btn-primary" type="submit"><i class="icon-white icon-plus"></i> Ajouter</button>
                <button class="btn btn-success" type="reset"><i class="icon-white icon-refresh"></i> Vider le formulaire</button>
                <a href="afficherClient?idCli=<%= id%>" class="btn btn-inverse"><i class="icon-white icon-share-alt"></i> Retour au client</a>
            </p>
        </form>
    </div>
    <div class="col-sm-4">
        <img src="http://localhost:8080/crud/theme/img/nouveau_compte2.png" alt="new account"/>
    </div>
</div>


<%
    } else {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    WebUtilities.doFooter(out, "ajouterCompte");
%>