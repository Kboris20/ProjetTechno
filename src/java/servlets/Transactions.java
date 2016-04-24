/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.TransactionAdvancedDao;
import dao.TransactionDao;
import static dao.UtilisateurDao.researchByUsername;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Transaction;
import modele.TransactionAdvanced;
import modele.User;
import utilities.WebUtilities;

/**
 *
 * @author silvio.gutierre
 */
public class Transactions extends HttpServlet {

    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("dd MMMM yyyy");
    private static final DecimalFormat MY_FORMATER = new DecimalFormat("###.00 CHF");

    private List<Transaction> listTransfers;
    private List<TransactionAdvanced> joinedTransfers;
    private User current_user;

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

        current_user = researchByUsername(HtmlHttpUtils.getUser(request)).get(0);

        listTransfers = new ArrayList<Transaction>();
        listTransfers.addAll(TransactionDao.researchAll());
        joinedTransfers = new ArrayList<TransactionAdvanced>();
        joinedTransfers.addAll(TransactionAdvancedDao.researchByUser(current_user));

       try {
            if (!HtmlHttpUtils.isAuthenticate(request)){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        WebUtilities.doHeader(out, "Liste des transactions", request, "transactions", 0);

        out.println("<div class=\"panel panel-default\">");
        out.println("<div class=\"panel-heading\">");
        out.println("<a href=\"TransfertFromTransfertManag?status=deb&idCompteDeb=-1&idCompteCred=-1\"class=\"btn btn-primary\"><i class=\"icon-white icon-plus\" title=\"Nouvelle transaction\"></i></a>");
        out.println("</div>");

        try {
            if (joinedTransfers.isEmpty()) {
                out.println("<div class=\"alert alert-info\">");
                out.println("Vous n'avez fait encore aucune transaction");
                out.println("</div>");
            } else {

                out.println("<table class=\"table table-hover\" style=\"width: 100%;\">");
                out.println("<tr>");
                out.println("<td class=\"listRow\">Client débit</td>");
                out.println("<td class=\"listRow\">Compte débité</td>");
                out.println("<td class=\"listRow\">Client crédit</td>");
                out.println("<td class=\"listRow\">Compte crédit</td>");
                out.println("<td class=\"listRow\">Montant</td>");
                out.println("<td class=\"listRow\">Date</td>");
                out.println("</tr>");
                for (TransactionAdvanced tra : joinedTransfers) {
                    out.println("<tr>");
                    out.println("<td>" + tra.getClient_debit() + "</td>");
                    out.println("<td>" + tra.getAccount_debit() + "</td>");
                    out.println("<td>" + tra.getClient_credit() + "</td>");
                    out.println("<td>" + tra.getAccount_credit() + "</td>");
                    out.println("<td>" + MY_FORMATER.format(tra.getAmount()) + "</td>");
                    out.println("<td>" + FORMATER.format(tra.getDate()) + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
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