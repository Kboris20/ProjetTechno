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
import modele.Compte;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class doModifierCompte extends HttpServlet {

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

        WebUtilities.doHeader(out, "Modifier un compte");
        try {
            Client cli = new Client();
            cli.setIdentifiant(Integer.parseInt(request.getParameter("idCli")));
            ArrayList<Client> cliListe = ClientDao.research(cli);

            if (cliListe.size() > 0) {
                cli = cliListe.get(0);

                Compte cpt = new Compte();
                cpt.setIdentifiant(Integer.parseInt(request.getParameter("id")));
                if (CompteDao.researchOwnerId(cpt.getIdentifiant()) == cli.getIdentifiant()) {
                    cpt.setNom(request.getParameter("nom"));
                    cpt.setSolde(Float.valueOf(request.getParameter("solde")));
                    cpt.setTaux(Float.valueOf(request.getParameter("taux")));

                    CompteDao.update(cpt);

                    response.sendRedirect(request.getContextPath() + "/afficherClient?id=" + cli.getIdentifiant() + "&modCpt=true");
                } else {
                    WebUtilities.doHeader(out, "Modifier un compte");
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Ce compte n'appartient pas au bon client.");
                    out.println("</div>");
                    out.println("<a href=\"index\" class=\"btn btn-inverse\"><i class=\"icon-white icon-share-alt\"></i> Retour à la liste</a>");
                    WebUtilities.doFooter(out);
                }
            } else {
                out.println("Aucun client n'existe avec cet identifiant.");
            }
        } finally {
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
