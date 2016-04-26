/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Account;

/**
 *
 * @author boris.klett
 */
public class TransactionCheck extends HttpServlet {

    private String amount;
    private String cents;
    private String id;
    private String id1;
    private Integer idCli;
    private Account account;
    private Float total;

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
            if (!HtmlHttpUtils.isAuthenticate(request)) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
            amount = request.getParameter("amount");
            cents = request.getParameter("cents");
            id = request.getParameter("id");
            id1 = request.getParameter("id1");
            idCli = Integer.valueOf(request.getParameter("idCli"));
            account = new Account();
            total = Float.valueOf(amount + "." + cents);

            account.setId(Integer.valueOf(id));
            ArrayList<Account> cptListe = AccountDao.research(account);
            account = cptListe.get(0);
            if (idCli == 0) {
                if (total > account.getBalance()) {
                    response.sendRedirect(request.getContextPath() + "/TransfertFromTransfertManag?error=true&status=allOk&idCompteDeb=" + id + "&idCompteCred=" + id1 + "");
                } else {
                    response.sendRedirect(request.getContextPath() + "/TransfertFromTransfertManag?error=false&status=allOk&amount=" + total + "&idCompteDeb=" + id + "&idCompteCred=" + id1 + "");

                }
            } else {

                if (total > account.getBalance()) {
                    response.sendRedirect(request.getContextPath() + "/transfereCompteACompte?error=true&id=" + id + "&id1=" + id1 + "&idCli=" + idCli + "");
                } else {
                    response.sendRedirect(request.getContextPath() + "/transfereCompteACompte?error=false&amount=" + total + "&id=" + id + "&id1=" + id1 + "&idCli=" + idCli + "");
                }
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
