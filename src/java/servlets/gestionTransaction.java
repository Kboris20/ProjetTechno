/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.TransactionDao;
import static dao.UtilisateurDao.researchByUsername;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Transaction;
import modele.Utilisateur;
import utilities.WebUtilities;

/**
 *
 * @author silvio.gutierre
 */
public class gestionTransaction extends HttpServlet {

    private ArrayList<Transaction> listeTra;

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

        listeTra = new ArrayList<Transaction>();
        Utilisateur current_user = researchByUsername(HtmlHttpUtils.getUser(request)).get(0);
        listeTra.addAll(TransactionDao.researchByUser(current_user));        

        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        WebUtilities.doHeader(out, "Liste des transactions", request, "transactions",0);

        try {
            
            out.println("<a href=\"TransfertFromTransfertManag?transcmpt=true\" class=\"btn btn-primary\"><i class=\"icon-white icon-plus\"></i> Nouvelle transaction</a>");
            if (listeTra.isEmpty()) {
                out.println("<div class=\"alert alert-info\">");
                out.println("Vous n'avez fait encore aucune transaction");
                out.println("</div>");
            } else {
                out.println("<table class=\"table table-hover\" style=\"width: 100%;\">");
                out.println("<tr>");
                out.println("<td>Compte débité</td>");
                out.println("<td>Compte crédité</td>");
                out.println("<td>Montant</td>");
                out.println("<td>Date</td>");
                out.println("</tr>");
                for (Transaction tra : listeTra) {
                    out.println("<tr>");
                    out.println("<td>" + tra.getCompte_credit().getNom() + "</td>");
                    out.println("<td>" + tra.getCompte_debit().getNom() + "</td>");
                    out.println("<td>" + tra.getMontant() + "</td>");
                    out.println("<td>" + tra.getDate() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

        } finally {
            WebUtilities.doFooter(out);
            out.close();
        }

        WebUtilities.doFooter(out);

        out.close();

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
