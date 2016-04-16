/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class ListAll extends HttpServlet {

    private ArrayList<Client> listeCli;
    private Integer nombreClient;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        listeCli = new ArrayList<Client>();
        listeCli.addAll(WelcomeServlet.listeCli);

//        String transfereToClient = new String();
//        transfereToClient = request.getParameter("trans");
        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        WebUtilities.doHeader(out, "Liste des clients", request, "clients", 0);

        try {
            if (request.getParameter("dele").equalsIgnoreCase("true")) {
                out.println("<div id=\"popupDelClientListAll\" class=\"alert alert-warning alert-dismissible\" role=\"alert\">");
                out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                ArrayList<Client> listeCli = new ArrayList<Client>();
                Client c = new Client();
                c.setIdentifiant(Integer.valueOf(request.getParameter("id")));
                listeCli.addAll(ClientDao.research(c));
                out.println("<b><u>Confirmation</u></b>");
                out.println("<p>Voulez vous réellement supprimer</p>");
                out.println("<b> " + listeCli.get(0).getNom() + " " + listeCli.get(0).getPrenom() + "</b>");
                out.println("<br/>");
                out.println("<a href=\"delete?id=" + request.getParameter("id") + "\" class=\"btn btn-danger btn-mini\"> <span class=\"glyphicon glyphicon-trash\"></span></a>");
                out.println("</div>");
            }
        } catch (Exception ex) {
        }

        try {
            try {
                String delParam = request.getParameter("del");

                if (delParam.equals("true")) {
                    out.println("<div class=\"alert alert-success\">");
                    out.println("Client supprimé.");
                    out.println("</div>");
                } else if (delParam.equals("false")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Suppression impossible !");
                    out.println("</div>");
                } else if (delParam.equals("error1")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Client introuvable, suppression impossible !");
                    out.println("</div>");
                } else if (delParam.equals("error2")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Client non suprimé.<br/>");
                    out.println("Erreur rencontrée: " + request.getParameter("text"));
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            try {
                String modParam = request.getParameter("mod");

                if (modParam.equals("error1")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Client introuvable, modification impossible !");
                    out.println("</div>");
                } else if (modParam.equals("error2")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Client non modifié.<br/>");
                    out.println("Erreur rencontrée: " + request.getParameter("text"));
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            out.println("<div class=\"panel panel-default\">");
            out.println("<div class=\"panel-heading\">");
            out.println("<a class=\"btn btn-primary addC\"><i class=\"icon-white icon-plus\" title=\"Ajouter un client\"></i></a>");
            out.println("<div class=\"add\">\n"
                    + "<form id=\"form1\" name=\"form1\" method=\"post\" action=\"addClient\">"
                    + "<table><tr>"
                    + "<td><button title=\"Sauvegarder\" type=\"submit\"><span class=\"glyphicon glyphicon-ok\"></span></button>"
                    + "<a class=\"retour\"><span class=\"glyphicon glyphicon-share-alt\" title=\"Annuler\"></span></a></td>"
                    + "</tr><tr>"
                    + "<td><input type=\"text\" name=\"nom\" placeholder=\"Nom\" required/></td>"
                    + "<td><input type=\"text\" name=\"prenom\" placeholder=\"Prenom\" required/></td>"
                    + "<td><input type=\"text\" name=\"adresse\" placeholder=\"Adresse\" required/></td>"
                    + "<td><input type=\"text\" name=\"ville\" placeholder=\"Ville\" required/></td>"
                    + "<td><img src=\"http://localhost:8080/crud/theme/img/nouveau_client1.png\" alt=\"image nouveau client\"/></td>"
                    
                    + "</tr></table></form>"
                    + "</div>");

            out.println("</div>");

            out.println("<table class=\"table table-hover\" id=\"tableClientsListAll\">");
            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            out.println("<td class=\"listRow\">Nom</td>");
            out.println("<td class=\"listRow\">Prenom</td>");
            out.println("<td class=\"listRow\">Adresse</td>");
            out.println("<td class=\"listRow\">Ville</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");

            if (listeCli.isEmpty()) {
                out.println("<div class=\"alert alert-info\">");
                out.println("Il n'y a pas de client");
                out.println("</div>");
            } else {
                nombreClient = 0;
                for (Client cli : listeCli) {
                    out.println("<tr>");
                    out.println("<td>" + ++nombreClient + "</td>");
                    out.println("<td>" + cli.getNom() + "</td>");
                    out.println("<td>" + cli.getPrenom() + "</td>");
                    out.println("<td>" + cli.getAdresse() + "</td>");
                    out.println("<td>" + cli.getVille() + "</td>");
                    out.println("<td></td>");
                    out.println("<td></td>");
                    out.println("<td><a href=\"afficherClient?idCli=" + cli.getIdentifiant() + "\" class=\"btn btn-info btn-mini\"><i class=\"icon-white icon-eye-open\" title=\"Détailler\"></i></a>");
                    out.println("<a href=\"modifier?id=" + cli.getIdentifiant() + "\" class=\"btn btn-warning btn-mini\"><i class=\"icon-white icon-pencil\" title=\"Modifier\"></i></a>");
                    out.println("<a href=\"index?dele=true&id=" + cli.getIdentifiant() + "\" class=\"btn btn-danger btn-mini\"><i class=\"icon-white icon-trash\" title=\"Supprimer\"></i></a></td>");
                    out.println("</tr>");
                }

            }
            out.println("</table>");
            out.println("</div>");
        } finally {
            WebUtilities.doFooter(out);
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
