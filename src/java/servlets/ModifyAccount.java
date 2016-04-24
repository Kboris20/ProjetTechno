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
public class ModifyAccount extends HttpServlet {

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
            if (!HtmlHttpUtils.isAuthenticate(request)){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        WebUtilities.doHeader(out, "Modifier un compte", request, "compteMod", Integer.valueOf(request.getParameter("id")));
        try {
            Client client = new Client();
            client.setId(Integer.parseInt(request.getParameter("idCli")));
            ArrayList<Client> clients = ClientDao.research(client);

            if (clients.size() > 0) {
                client = clients.get(0);

                Account account = new Account();
                account.setId(Integer.parseInt(request.getParameter("id")));
                ArrayList<Account> accounts = AccountDao.research(account);
                if (accounts.size() > 0) {
                    account = accounts.get(0);
                    if (AccountDao.researchOwnerId(account.getId()) == client.getId()) {
                        out.println("<div class=\"row\"><div class=\"col-sm-8\">");
                        out.println("<form  id=\"form1\" name=\"form1\" method=\"get\"  action=\"doModifierCompte\">");

                        out.println("<input type=\"hidden\" name=\"idCli\" value=\"" + client.getId() + "\"/>");

                        out.println("<input type=\"hidden\" name=\"id\" value=\"" + account.getId() + "\"/>");
                        out.println("<table><tr><td>");
                        out.println("<label for=\"nom\">Compte</label>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<input type=\"text\" name=\"nom\" id=\"nom\" value=\"" + account.getName() + "\" required/>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<label for=\"solde\">Solde</label>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<input type=\"text\" name=\"solde\" id=\"solde\" value=\"" + account.getBalance() + "\" required/>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("<label for=\"taux\">Taux</label>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("<input type=\"text\" name=\"taux\" id=\"taux\" value=\"" + account.getRate() + "\" required/>");
                        out.println("</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<button class=\"btn btn-warning\"><i class=\"icon-white icon-pencil\"></i> Modifier</button>");
                        out.println("</form>");
                        out.println("</div><div class=\"col-sm-4\"><img src=\"http://localhost:8080/crud/theme/img/modifier_compte.png\" alt=\"image nouveau compte\"/></div></div>");
                    } else {
                        out.println("<div class=\"alert alert-warning\">");
                        out.println("Ce compte n'appartient pas au bon client.");
                        out.println("</div>");
                    }
                }
            } else {
                out.println("<div class=\"alert alert-warning\">");
                out.println("Aucun client n'existe avec cet identifiant.");
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
