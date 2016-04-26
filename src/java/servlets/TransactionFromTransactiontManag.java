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
public class TransactionFromTransactiontManag extends HttpServlet {

    private ArrayList<Client> clients;
    private Integer idAccountDebit;
    private Integer idAccountCredit;
    private Account accountDebit;
    private Account accountCredit;
    private Account account;
    private String status;

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
            WebUtilities.doHeader(out, "Nouveau transfert", request, "newTransfert");
            try {
                if (request.getParameter("trans").equals("ok")) {
                    out.println("<div class=\"alert alert-success popupInformation\">");
                    out.println("Transfert effectué!");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }
            try {
                if (request.getParameter("error").equalsIgnoreCase("true")) {
                    out.println("<div class=\"alert alert-error popupInformation\">");
                    out.println("Tansfert non autorisé!");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            //########################################### Confirmation du transfer ##############################################
            try {
                if (request.getParameter("error").equals("false")) {
                    out.println("<div class=\"confirm\">hello</div>");
                    out.println("<div class=\"confirmPopup\">");
                    out.println("<b><u>Confirmation</u></b>");
                    out.println("<p>Veuillez confirmer le transfert pour le montant de: ");
                    out.println("<b> " + request.getParameter("amount") + " CHF?</b></p>");
                    out.println("<br/>");
                    out.println("<a href=\"transfere?amount=" + request.getParameter("amount") + "&id=" + request.getParameter("idCompteDeb") + "&id1=" + request.getParameter("idCompteCred") + "&idCli=0\" class=\"btn btn-success btn-mini\"> <span class=\"glyphicon glyphicon-ok\"></span> </a>");
                    out.println("<a href=\"javascript:hidePopup();\" class=\"btn btn-danger btn-mini\"><span class=\"glyphicon glyphicon-remove\"></span></a>");
                    out.println("</div>");
//###################################################################################################################
                }
            } catch (Exception ex) {
            }

            accountDebit = new Account();
            accountCredit = new Account();
            account = new Account();
            status = request.getParameter("status");

            if (status.equalsIgnoreCase("deb")) {
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à débiter: </h3>");
                out.println("<a href=\"javascript:showPopup();\" class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<br/>");
            } else if (status.equalsIgnoreCase("cred")) {

                idAccountDebit = Integer.valueOf(request.getParameter("idCompteDeb"));
                out.println("<br/>");
                out.println("<h3>Le compte débité </h3>");
                account.setId(idAccountDebit);
                accountDebit = AccountDao.research(account).get(0);
                out.println("<b>Compte: " + accountDebit.getName() + ", solde: " + accountDebit.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idAccountDebit) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à créditer: </h3>");
                out.println("<a href=\"javascript:showPopup();\" class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<br/>");
            } else {

                idAccountDebit = Integer.valueOf(request.getParameter("idCompteDeb"));
                idAccountCredit = Integer.valueOf(request.getParameter("idCompteCred"));
                out.println("<br/>");
                out.println("<h3>Le compte débité </h3>");
                account.setId(idAccountDebit);
                accountDebit = AccountDao.research(account).get(0);
                out.println("<b>Compte: " + accountDebit.getName() + ", solde: " + accountDebit.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idAccountDebit) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + idAccountCredit + "&idCompteCred=" + idAccountDebit + "\"><button type=\"button\" class=\"btn btn-default btn-sm\" title=\"Inverser les comptes\"><span class=\"glyphicon glyphicon-sort\"></span></button></a>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Le compte crédité </h3>");
                account.setId(idAccountCredit);
                accountCredit = AccountDao.research(account).get(0);
                out.println("<b>Compte: " + accountCredit.getName() + ", solde: " + accountCredit.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idAccountCredit) + "</b>");
                out.println("<br/>");
                out.println("<br/>");

                out.println("<form method=\"GET\" action=\"transfereCheck\"");
                out.println("<label for=\"amount\">Montant:  CHF </label>");
                out.println("<input type=\"number\" name=\"amount\" id=\"amount\" value=\"00\"/>.");
                out.println("<input type=\"number\" name=\"cents\" id=\"cents\" value=\"00\"/>");
                out.println("<input type=\"hidden\" name=\"id1\" value=\"" + idAccountCredit + "\"/>");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + idAccountDebit + "\"/>");
                out.println("<input type=\"hidden\" name=\"idCli\" value=\"0\"/>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<button type=\"submit\" class=\"btn btn-default btn-sm\"><span class=\"glyphicon glyphicon-transfer\"></span> Transférer</button>");
                out.println("</form>");
            }

            clients = new ArrayList<Client>();
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
                    ArrayList<Account> accounts = new ArrayList<Account>();
                    out.println("<div id=\"compte_" + client.getId() + "\" style=\"width:50%; margin:auto;\" class=\"alert alert-info alert-dismissible comptes\" role=\"alert\">");
                    out.println("<div class=\"list-group\">");
                    out.println("<a href=\"#this\" class=\"list-group-item disabled\">Comptes</a>");
                    for (Account a : client.getListAccount()) {
                        if (a.getId() != Integer.valueOf(request.getParameter("idCompteDeb"))) {
                            accounts.add(a);
                        }
                    }
                    if (!accounts.isEmpty()) {

                        for (Account a : accounts) {
                            if (request.getParameter("status").equalsIgnoreCase("deb")) {
                                out.println("<a href=\"TransfertFromTransfertManag?status=cred&idCompteDeb=" + a.getId() + "&idCompteCred=" + request.getParameter("idCompteCred") + "\" class=\"list-group-item\">Compte: " + a.getName() + ", Solde: " + a.getBalance() + "</a>");
                            } else {
                                out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + request.getParameter("idCompteDeb") + "&idCompteCred=" + a.getId() + "\" class=\"list-group-item\">Compte: " + a.getName() + ", Solde: " + a.getBalance() + "</a>");
                            }
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
