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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Compte;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class displayClient extends HttpServlet {

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

            WebUtilities.doHeader(out, "Afficher un client", request, "clientDetail", Integer.parseInt(request.getParameter("idCli")));
            Client cli = new Client();
            cli.setIdentifiant(Integer.parseInt(request.getParameter("idCli")));
            ArrayList<Client> cliListe = new ArrayList<Client>();
            cliListe.addAll(ClientDao.research(cli));

            if (cliListe.size() > 0) {
                cli = cliListe.get(0);
                /* TODO output your page here. You may use following sample code. */
                try {
                    if (request.getParameter("add").equals("true")) {
                        out.println("<div class=\"alert alert-success\">");
                        out.println("Client crée.");
                        out.println("</div>");
                    }
                } catch (Exception ex) {
                }
                out.println("<fieldset><legend>" + cli.getNom() + " " + cli.getPrenom() + "</legend>");
                out.println(cli.getAdresse() + "<br/>");
                out.println(cli.getVille());

                try {
                    if (request.getParameter("dele").equalsIgnoreCase("true")) {
                        out.println("<div id=\"popupDeleteClientDisplayClient\" class=\"alert alert-warning alert-dismissible\" role=\"alert\">");
                        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                        ArrayList<Compte> listeCmpt = new ArrayList<Compte>();
                        Compte c = new Compte();
                        c.setIdentifiant(Integer.valueOf(request.getParameter("id")));
                        listeCmpt.addAll(CompteDao.research(c));
                        out.println("<b><u>Confirmation</u></b>");
                        out.println("<p>Voulez vous réellement supprimer le compte</p>");
                        out.println("<b> " + listeCmpt.get(0).getNom() + ", solde: " + listeCmpt.get(0).getSolde() + "</b>");
                        out.println("<br/>");
                        out.println("<a href=\"deleteCompte?id=" + request.getParameter("id") + "&cliId=" + request.getParameter("idCli") + "\" class=\"btn btn-danger btn-mini\"> <span class=\"glyphicon glyphicon-trash\"></span></a>");
                        out.println("</div>");
                    }
                } catch (Exception ex) {
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher("allComptes?idCli=" + cli.getIdentifiant());
                dispatcher.include(request, response);

                out.println("</fieldset>");
            } else {
                out.println("<div class=\"alert\">");
                out.println("Aucun client n'existe avec cet identifiant.");
                out.println("</div>");
            }
            out.println("<br/><br/>");
        } catch (Exception ex) {
            out.println(ex.getMessage());
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
