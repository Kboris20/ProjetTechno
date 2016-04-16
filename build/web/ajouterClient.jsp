<%-- 
    Document   : ajouterClient
    Created on : 10 déc. 2012, 11:25:40
    Author     : christop.francill
--%>

<%@page import="servlets.HtmlHttpUtils"%>
<%@page import="utilities.WebUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    WebUtilities.doHeader(out, "Ajouter un client", "", request, "ajouterClient");
    if (HtmlHttpUtils.isAuthenticate(request)) {
%>

<div class="row">
    <div class="col-sm-8">
        <form id="form1" name="form1" method="post" action="addClient">

            <table>
                <tr>
                    <td>
                        <label for="nom">Nom</label>
                    </td>
                    <td> 
                        <input type="text" name="nom" id="nom" required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="prenom">Prénom</label>
                    </td>
                    <td>
                        <input type="text" name="prenom" id="prenom" required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="adresse">Adresse</label>
                    </td>
                    <td>
                        <input type="text" name="adresse" id="adresse" required/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="ville">Ville</label>
                    </td>
                    <td>              
                        <input type="text" name="ville" id="ville" required/>
                    </td>
                </tr>
            </table>
            <p>
                <button class="btn btn-primary" type="submit"><i class="icon-white icon-plus"></i> Ajouter</button>
                <button class="btn btn-success" type="reset"><i class="icon-white icon-refresh"></i> Vider le formulaire</button>
                <!--a href="index?trans=false" class="btn btn-inverse"><i class="icon-white icon-share-alt"></i> Retour à la liste</a-->
            </p>
        </form>
    </div>
    <div class="col-sm-4">
        <img src="http://localhost:8080/crud/theme/img/nouveau_client1.png" alt="image nouveau client"/>
    </div>
</div>
<%
    } else {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    WebUtilities.doFooter(out, "ajouterClient");
%>
