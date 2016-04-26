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
import modele.Client;
import modele.Account;
import utilities.WebUtilities;

/**
 *
 * @author boris.klett
 */
public class TransactionAccountToAccount extends HttpServlet {

    private Account account;
    private Account accountDest;

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

            WebUtilities.doHeader(out, "Nouveau transfert", request, "transfert", Integer.valueOf(request.getParameter("id")), Integer.valueOf(request.getParameter("id")));
            try {
                if (request.getParameter("trans").equals("ok")) {
                    out.println("<div class=\"alert alert-success popupInformation\">");
                    out.println("Transfert effectué.");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }
            try {
                if (request.getParameter("error").equalsIgnoreCase("true")) {
                    out.println("<div class=\"alert alert-error popupInformation\">");
                    out.println("Transfert non autorisé");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            account = new Account();
            account.setId(Integer.parseInt(request.getParameter("id")));
            ArrayList<Account> cptListe = AccountDao.research(account);

            if (cptListe.size() > 0) {
                account = cptListe.get(0);
                String owner = AccountDao.researchOwner(account.getId());
                out.println("<h3>Compte débité appartenant à Mme/M. " + owner + " </h3>");
                out.println("<table class=\"table table-hover list\" >");
                out.println("<tr>");
                out.println("<td class=\"listRow\">Compte</td>");
                out.println("<td class=\"listRow\">Solde</td>");
                out.println("<td class=\"listRow\">Taux</td>");
                out.println("<td>&nbsp;</td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td>" + account.getName() + "</td>");
                out.println("<td>" + account.getBalance() + "</td>");
                out.println("<td>" + account.getRate() + "</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<br/>");

                if (Integer.valueOf(request.getParameter("id1")) > -1) {
//########################################### Confirmation du transfer ##############################################
                    try {
                        if (request.getParameter("error").equals("false")) {
                            out.println("<div class=\"confirm\"></div>");
                            out.println("<div class=\"confirmPopup\">");
                            out.println("<b><u>Confirmation</u></b>");
                            out.println("<p>Veuillez confirmer le transfert pour le montant de: ");
                            out.println("<b> " + request.getParameter("amount") + " CHF?</b></p>");
                            out.println("<br/>");
                            out.println("<a href=\"transfere?amount=" + request.getParameter("amount") + "&id=" + request.getParameter("id") + "&id1=" + request.getParameter("id1") + "&idCli=" + request.getParameter("idCli") + "\" class=\"btn btn-success btn-mini\"> <span class=\"glyphicon glyphicon-ok\"></span> </a>");
                            out.println("<a href=\"javascript:hidePopup();\" class=\"btn btn-danger btn-mini\"><span class=\"glyphicon glyphicon-remove\"></span></a>");
                            out.println("</div>");
//###################################################################################################################
                        }
                    } catch (Exception ex) {
                    }

                    accountDest = new Account();
                    accountDest.setId(Integer.parseInt(request.getParameter("id1")));
                    ArrayList<Account> accountsDest = AccountDao.research(accountDest);

                    if (accountsDest.size() > 0) {
                        accountDest = accountsDest.get(0);
                        String ownerDest = AccountDao.researchOwner(accountDest.getId());
                        out.println("<a href=\"transfereCompteACompte?id=" + request.getParameter("id1") + "&id1=" + request.getParameter("id") + "\"><button type=\"button\" class=\"btn btn-default btn-sm\" title=\"Inverser les rôles\"><span class=\"glyphicon glyphicon-sort\"></span></button></a>");
                        out.println("<br/>");
                        out.println("<h3>Compte crédité appartenant à Mme/M. " + ownerDest + " </h3>");
                        out.println("<table class=\"table table-hover list\" >");
                        out.println("<tr>");
                        out.println("<td class=\"listRow\">Compte</td>");
                        out.println("<td class=\"listRow\">Solde</td>");
                        out.println("<td class=\"listRow\">Taux</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("</tr>");

                        out.println("<tr>");
                        out.println("<td>" + accountDest.getName() + "</td>");
                        out.println("<td>" + accountDest.getBalance() + "</td>");
                        out.println("<td>" + accountDest.getRate() + "</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("<br/>");

                        out.println("<form method=\"GET\" action=\"transfereCheck\"");
                        out.println("<label for=\"amount\">Montant:  CHF </label>");
                        out.println("<input type=\"number\" name=\"amount\" id=\"amount\" value=\"00\"/>.");
                        out.println("<input type=\"number\" name=\"cents\" id=\"cents\" value=\"00\"/>");
                        out.println("<input type=\"hidden\" name=\"id1\" value=\"" + request.getParameter("id1") + "\"/>");
                        out.println("<input type=\"hidden\" name=\"id\" value=\"" + request.getParameter("id") + "\"/>");
                        out.println("<input type=\"hidden\" name=\"idCli\" value=\"" + request.getParameter("idCli") + "\"/>");
                        out.println("<br/>");
                        out.println("<br/>");
                        out.println("<button type=\"submit\" class=\"btn btn-default btn-sm\"><span class=\"glyphicon glyphicon-transfer\"></span> Transférer</button>");
                        out.println("</form>");
                    } else {
                        out.println("<div class=\"alert alert-warning\">");
                        out.println("Aucun compte n'existe avec cet identifiant.");
                        out.println("</div>");
                    }

                } else {
                    out.println("<h3>Compte à créditer... </h3>");
                    out.println("<a href=\"javascript:showPopup();\" class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                    ArrayList<Client> clients = new ArrayList<Client>();
                    clients.addAll(Clients.clients);

                    //########################################## Popup customiser pour le choix des comptes #################################################
                    out.println("<div class=\"customPopup\">");
                    out.println("<button onclick=\"hidePopup();\" class=\"btn btn-default btn-sm\"><span class=\"glyphicon glyphicon-remove\"></span></button>");
                    out.println("<hr/>");
                    out.println("<br/>");

                    out.println("<div class=\"list-group\">");
                    out.println("<a class=\"list-group-item disabled\">Liste des clients</a>");

                    if (!clients.isEmpty()) {
                        for (Client client : clients) {
                            out.println("<a href=\"#this\" onClick=\"affiche('#compte_" + client.getId() + "')\" href=\"#\" class=\"list-group-item\">" + client.getLastName() + " " + client.getFirstName() + "</a>");
                            ArrayList<Account> comptListe = new ArrayList<Account>();
                            out.println("<div id=\"compte_" + client.getId() + "\" style=\"width:50%; margin:auto;\" class=\"alert alert-info alert-dismissible comptes\" role=\"alert\">");
                            out.println("<div class=\"list-group\">");
                            out.println("<a href=\"#this\" class=\"list-group-item disabled\">Comptes</a>");
                            for (Account c : client.getListAccount()) {
                                if (c.getId() != Integer.valueOf(request.getParameter("id"))) {
                                    comptListe.add(c);
                                }
                            }
                            if (!comptListe.isEmpty()) {

                                for (Account compte : comptListe) {
                                    out.println("<a href=\"transfereCompteACompte?id=" + request.getParameter("id") + "&id1=" + compte.getId() + "&idCli=" + request.getParameter("idCli") + "\" class=\"list-group-item\">Compte: " + compte.getName() + ", Solde: " + compte.getBalance() + "</a>");

                                }
                            } else {
                                out.println("<a href=\"#this\" class=\"list-group-item disabled\"><i>Pas de compte disponible!</i></a>");
                            }
                            out.println("</div></div>");
                        }
                    }

                    out.println("</div>");

                    out.println("</div>");
                    //#################################################################################################

                }

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
