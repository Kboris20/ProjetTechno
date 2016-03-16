<%-- 
    Document   : ajouterClient
    Created on : 10 déc. 2012, 11:25:40
    Author     : christop.francill
--%>

<%@page import="utilities.WebUtilities"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    WebUtilities.doHeader(out, "Ajouter un client");
%>
        
        <form id="form1" name="form1" method="post" action="addClient">
            <p>
              <label for="nom">Nom</label>
              <input type="text" name="nom" id="nom" />
            </p>
            <p>
              <label for="prenom">Prénom</label>
              <input type="text" name="prenom" id="prenom" />
            </p>
            <p>
              <label for="adresse">Adresse</label>
              <input type="text" name="adresse" id="adresse" />
            </p>
            <p>
              <label for="ville">Ville</label>
              <input type="text" name="ville" id="ville" />
            </p>
            <p>
              <button class="btn btn-primary" type="submit"><i class="icon-white icon-plus"></i> Ajouter</button>
              <button class="btn btn-success" type="reset"><i class="icon-white icon-refresh"></i> Vider le formulaire</button>
              <a href="index" class="btn btn-inverse"><i class="icon-white icon-share-alt"></i> Retour à la liste</a>
            </p>
          </form>
<%
    WebUtilities.doFooter(out);
%>
