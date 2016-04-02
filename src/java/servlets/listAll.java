/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import dao.CompteDao;
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
public class listAll extends HttpServlet {

    private ArrayList<Client> listeCli;

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
        listeCli.addAll(ClientDao.researchAll());

//        String transfereToClient = new String();
//        transfereToClient = request.getParameter("trans");
        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        if (request.getParameter("trans").equalsIgnoreCase("true")) {

            WebUtilities.doHeader(out, "Transfère compte à compte", "Choisir un client");

            try {
                if (listeCli.isEmpty()) {
                    out.println("<div class=\"alert alert-info\">");
                    out.println("Il n'y a pas de client");
                    out.println("</div>");
                } else {
                    out.println("<table class=\"table table-hover\" style=\"width: 100%;\">");
                    out.println("<tr>");
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>Nom</td>");
                    out.println("<td>Prenom</td>");
                    out.println("<td>Adresse</td>");
                    out.println("<td>Ville</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("</tr>");
                    for (Client cli : listeCli) {
                        out.println("<tr>");
                        out.println("<td><a href=\"afficherClient?id1=" + request.getParameter("id1") + "&trans=true&id=" + cli.getIdentifiant() + "\" class=\"btn btn-info btn-mini\"><i class=\"icon-white icon-user\"></i>Choisir</a>");
                        out.println("<td>" + cli.getNom() + "</td>");
                        out.println("<td>" + cli.getPrenom() + "</td>");
                        out.println("<td>" + cli.getAdresse() + "</td>");
                        out.println("<td>" + cli.getVille() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
                out.println("<a href=\"transfereCompteACompte?id=" + request.getParameter("id1") + "&id1=-1&idCli=" + CompteDao.researchOwnerId(Integer.valueOf(request.getParameter("id1"))) + "\"class=\"btn btn-inverse\"><i class=\"icon-white icon-share-alt\"></i>Annuler</a>");
            } finally {
                WebUtilities.doFooter(out);
                out.close();
            }

        } else {

            WebUtilities.doHeader(out, "Liste des clients");

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

                if (listeCli.isEmpty()) {
                    out.println("<div class=\"alert alert-info\">");
                    out.println("Il n'y a pas de client");
                    out.println("</div>");
                } else {
                    out.println("<table class=\"table table-hover\" style=\"width: 100%;\">");
                    out.println("<tr>");
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>Nom</td>");
                    out.println("<td>Prenom</td>");
                    out.println("<td>Adresse</td>");
                    out.println("<td>Ville</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("<td>&nbsp;</td>");
                    out.println("</tr>");
                    for (Client cli : listeCli) {
                        out.println("<tr>");
                        out.println("<td><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\" class=\"btn btn-info btn-mini\"><i class=\"icon-white icon-eye-open\"></i>Voir</a>");
                        out.println("<td>" + cli.getNom() + "</td>");
                        out.println("<td>" + cli.getPrenom() + "</td>");
                        out.println("<td>" + cli.getAdresse() + "</td>");
                        out.println("<td>" + cli.getVille() + "</td>");
                        out.println("<td><a href=\"modifier?id=" + cli.getIdentifiant() + "\" class=\"btn btn-warning btn-mini\"><i class=\"icon-white icon-pencil\"></i>Modifier</a>");
                        out.println("<td><a href=\"deleteConfirm?id=" + cli.getIdentifiant() + "\" class=\"btn btn-danger btn-mini\"><i class=\"icon-white icon-trash\"></i>Supprimer</a>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }

                //out.println("<h3><a href=\"" + request.getContextPath() + "/ajouterClient.jsp\">Ajouter un client</a></h3>");
                out.println("<a href=\"ajouterClient.jsp\" class=\"btn btn-primary\"><i class=\"icon-white icon-plus\"></i> Ajouter un client</a>");

            } finally {
                WebUtilities.doFooter(out);
                out.close();
            }
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
