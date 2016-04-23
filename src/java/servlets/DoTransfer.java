/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.AccountDao;
import dao.TransactionDao;
import dao.UtilisateurDao;
import exception.InsufficientBalanceException;
import exception.NegativeAmmountException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Account;
import modele.Transaction;
import modele.User;

/**
 *
 * @author boris.klett
 */
public class DoTransfer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws exception.NegativeAmmountException
     * @throws exception.InsufficientBalanceException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NegativeAmmountException, InsufficientBalanceException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
            Float somme = Float.valueOf(request.getParameter("somme"));
            String id = request.getParameter("id");
            String id1 = request.getParameter("id1");
            Integer idCli = Integer.valueOf(request.getParameter("idCli"));
            Account cpt = new Account();
            Account cptDest = new Account();
            User user = UtilisateurDao.researchByUsername(HtmlHttpUtils.getUser(request)).get(0);

            cpt.setId(Integer.valueOf(id));
            cptDest.setId(Integer.valueOf(id1));
            ArrayList<Account> cptListe = AccountDao.research(cpt);
            ArrayList<Account> cptListeDest = AccountDao.research(cptDest);

            cpt = cptListe.get(0);
            cptDest = cptListeDest.get(0);
            Transaction transaction = new Transaction(cpt, cptDest, somme);
            TransactionDao.create(transaction, user);

            cpt.debit(somme);
            cptDest.credit(somme);

            /*dans le cardre de l'exercice nous nous permettons de laisser l'opération tel quel mais 
             normalement l'opération ci-dessous devrait être atomique hors ce n'est pas le cas ici
             */
            AccountDao.update(cpt);
            AccountDao.update(cptDest);

            if (idCli == 0) {
                response.sendRedirect(request.getContextPath() + "/TransfertFromTransfertManag?trans=ok&status=allOk&idCompteDeb=" + id + "&idCompteCred=" + id1 + "");
            } else {
                response.sendRedirect(request.getContextPath() + "/transfereCompteACompte?trans=ok&id=" + id + "&id1=" + id1 + "&idCli=" + idCli + "");
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
        try {
            processRequest(request, response);
        } catch (NegativeAmmountException ex) {
            Logger.getLogger(DoTransfer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InsufficientBalanceException ex) {
            Logger.getLogger(DoTransfer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NegativeAmmountException ex) {
            Logger.getLogger(DoTransfer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InsufficientBalanceException ex) {
            Logger.getLogger(DoTransfer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
