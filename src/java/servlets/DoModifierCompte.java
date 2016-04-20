/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Account;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class DoModifierCompte extends HttpServlet {

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

        WebUtilities.doHeader(out, "Modifier un compte", request, "clientDetail", Integer.parseInt(request.getParameter("id")));
        try {
            Client cli = new Client();
            cli.setId(Integer.parseInt(request.getParameter("idCli")));
            ArrayList<Client> cliListe = ClientDao.research(cli);

            if (!cliListe.isEmpty()) {
                cli = cliListe.get(0);

                Account cpt = new Account();
                cpt.setId(Integer.parseInt(request.getParameter("id")));
                if (AccountDao.researchOwnerId(cpt.getId()) == cli.getId()) {
                    cpt.setName(request.getParameter("nom"));
                    cpt.setBalance(Float.valueOf(request.getParameter("solde")));
                    cpt.setRate(Float.valueOf(request.getParameter("taux")));

                    AccountDao.update(cpt);

                    response.sendRedirect(request.getContextPath() + "/afficherClient?&idCli=" + cli.getId() + "&modCpt=true");
                } else {
                    WebUtilities.doHeader(out, "Modifier un compte", request, "clientDetail", Integer.parseInt(request.getParameter("id")));
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Ce compte n'appartient pas au bon client.");
                    out.println("</div>");
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
