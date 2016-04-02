/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.CompteDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Compte;
import utilities.WebUtilities;

/**
 *
 * @author boris.klett
 */
public class transfereCompteACompte extends HttpServlet {

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
        
        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
            try {
                if (request.getParameter("trans").equals("ok")) {
                    out.println("<div class=\"alert alert-success\">");
                    out.println("Transfère effectué.");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }
            try {
                if (request.getParameter("error").equalsIgnoreCase("true")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Veuillez entrer un montant valide !");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            WebUtilities.doHeader(out, "Transfère compte à compte");
            Compte cpt = new Compte();
            cpt.setIdentifiant(Integer.parseInt(request.getParameter("id")));
            ArrayList<Compte> cptListe = CompteDao.research(cpt);
            Integer id1 = -1;
            id1 = cpt.getIdentifiant();
            
            //WebUtilities.doHeader(out, "Supprimer un client");
            if (cptListe.size() > 0) {
                cpt = cptListe.get(0);
                String owner = CompteDao.researchOwner(cpt.getIdentifiant());
                out.println("<h3>Transfère du compte de " + owner + " </h3>");
                out.println("<table class=\"table table-hover\" style=\"width: 50%;\">");
                out.println("<tr>");
                out.println("<td>Nom</td>");
                out.println("<td>Solde</td>");
                out.println("<td>Taux</td>");
                out.println("<td>&nbsp;</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>" + cpt.getNom() + "</td>");
                out.println("<td>" + cpt.getSolde() + "</td>");
                out.println("<td>" + cpt.getTaux() + "</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<br/>");

                if (Integer.valueOf(request.getParameter("id1")) > -1) {

                    Compte cptDest = new Compte();
                    cptDest.setIdentifiant(Integer.parseInt(request.getParameter("id1")));
                    ArrayList<Compte> cptListeDest = CompteDao.research(cptDest);

                    Client cliDest = new Client();
                    if (cptListeDest.size() > 0) {
                        cptDest = cptListeDest.get(0);
                        String ownerDest = CompteDao.researchOwner(cptDest.getIdentifiant());
                        out.println("<h3>Au compte de " + ownerDest + " </h3>");
                        out.println("<table class=\"table table-hover\" style=\"width: 50%;\">");
                        out.println("<tr>");
                        out.println("<td>Nom</td>");
                        out.println("<td>Solde</td>");
                        out.println("<td>Taux</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td>" + cptDest.getNom() + "</td>");
                        out.println("<td>" + cptDest.getSolde() + "</td>");
                        out.println("<td>" + cptDest.getTaux() + "</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<br/>");

                        out.println("<form method=\"POST\" action=\"transfereCheck\"");
                        out.println("<label for=\"montant\">Montant: </label>");
                        out.println("<input type=\"number\" name=\"montant\" id=\"montant\" value=\"00\" style=\"height:30px;width:80px;\"/>.");
                        out.println("<input type=\"number\" name=\"centimes\" id=\"centimes\" value=\"00\" style=\"height:30px;width:80px;\"/>");
                        out.println("<input type=\"hidden\" name=\"id1\" value=\"" + request.getParameter("id1") + "\"/>");
                        out.println("<input type=\"hidden\" name=\"id\" value=\"" + request.getParameter("id") + "\"/>");
                        out.println("<input type=\"hidden\" name=\"idCli\" value=\"" + request.getParameter("idCli") + "\"/>");
                        out.println("<br/>");
                        out.println("<br/>");
                        out.println("<input type=\"submit\"  style=\"height:30px;width:80px;\" class=\"btn btn-primary btn-mini\" value=\"Transfèrer\"/>");
                        out.println("</form>");
                    } else {
                        out.println("<div class=\"alert alert-warning\">");
                        out.println("Aucun compte n'existe avec cet identifiant.");
                        out.println("</div>");
                    }

                } else {
                    out.println("<h3>Au compte... </h3>");
                    out.println("<a href=\"index?idCli=" + request.getParameter("idCli") + "&id1=" + id1 + "&trans=true \"class=\"btn btn-primary btn-mini\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");

                }
                out.println("<a href=\"afficherClient?trans=false&id=" + CompteDao.researchOwnerId(cpt.getIdentifiant()) + " \"class=\"btn btn-inverse\"><i class=\"icon-white icon-share-alt\"></i>Annuler</a>");
            } else {
                out.println("<div class=\"alert alert-warning\">");
                out.println("Aucun compte n'existe avec cet identifiant.");
                out.println("</div>");
            }
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
