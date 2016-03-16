<%-- 
    Document   : ajouterCompte
    Created on : 11 déc. 2012, 13:36:02
    Author     : christop.francill
--%>

<%@page import="utilities.WebUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    WebUtilities.doHeader(out, "Ajouter un compte");

    int id = Integer.parseInt(request.getParameter("idCli"));
%>
  
        <form id="form1" name="form1" method="post" action="addCompte">
            <input type="hidden" name="clientId" id="clientId" value="<%= id %>"/>
            <p>
              <label for="nom">Nom</label>
              <input type="text" name="nom" id="nom" />
            </p>
            <p>
              <label for="solde">Solde</label>
              <input type="text" name="solde" id="solde" />
            </p>
            <p>
              <label for="taux">Taux</label>
              <input type="text" name="taux" id="taux" />
            </p>
            <p>
              <button class="btn btn-primary" type="submit"><i class="icon-white icon-plus"></i> Ajouter</button>
              <button class="btn btn-success" type="reset"><i class="icon-white icon-refresh"></i> Vider le formulaire</button>
              <a href="afficherClient?id=<%= id %>" class="btn btn-inverse"><i class="icon-white icon-share-alt"></i> Retour au client</a>
            </p>
          </form>

<%
    WebUtilities.doFooter(out);
%>