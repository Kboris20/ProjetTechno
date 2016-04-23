/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author boris.klett
 */
public class TransfereCompteACompte extends HttpServlet {

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

            WebUtilities.doHeader(out, "Transfère compte à compte", request, "transfere", Integer.valueOf(request.getParameter("id")), Integer.valueOf(request.getParameter("id")));
            try {
                if (request.getParameter("trans").equals("ok")) {
                    out.println("<div class=\"alert alert-success\">");
                    out.println("Transfère effectué.");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }
            try {
                if (request.getParameter("error").equalsIgnoreCase("true")) {
                    out.println("<div class=\"alert alert-error\">");
                    out.println("Veuillez entrer un montant valide !");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            Account cpt = new Account();
            cpt.setId(Integer.parseInt(request.getParameter("id")));
            ArrayList<Account> cptListe = AccountDao.research(cpt);
            Integer id1 = -1;
            id1 = cpt.getId();

            if (cptListe.size() > 0) {
                cpt = cptListe.get(0);
                String owner = AccountDao.researchOwner(cpt.getId());
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
                out.println("<td>" + cpt.getName() + "</td>");
                out.println("<td>" + cpt.getBalance() + "</td>");
                out.println("<td>" + cpt.getRate() + "</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<br/>");

                if (Integer.valueOf(request.getParameter("id1")) > -1) {
                    try {
                        if (request.getParameter("error").equals("false")) {
                            out.println("<div class=\"alert alert-warning alert-dismissible popupAlert\" role=\"alert\">");
                            out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                            out.println("<b><u>Confirmation</u></b>");
                            out.println("<p>Souhaitez vous réellement effectuer de de: ");
                            out.println("<b> " + request.getParameter("somme") + " CHF?</b></p>");
                            out.println("<br/>");
                            out.println("<a href=\"transfere?somme=" + request.getParameter("somme") + "&id=" + request.getParameter("id") + "&id1=" + request.getParameter("id1") + "&idCli=" + request.getParameter("idCli") + "\" class=\"btn btn-info btn-mini\"> <span class=\"glyphicon glyphicon-ok\"></span> Oui</a>");
                            out.println("</div>");
                        }
                    } catch (Exception ex) {
                    }

                    Account cptDest = new Account();
                    cptDest.setId(Integer.parseInt(request.getParameter("id1")));
                    ArrayList<Account> cptListeDest = AccountDao.research(cptDest);

                    Client cliDest = new Client();
                    if (cptListeDest.size() > 0) {
                        cptDest = cptListeDest.get(0);
                        String ownerDest = AccountDao.researchOwner(cptDest.getId());
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
                        out.println("<td>" + cptDest.getName() + "</td>");
                        out.println("<td>" + cptDest.getBalance() + "</td>");
                        out.println("<td>" + cptDest.getRate() + "</td>");
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
                    out.println("<a class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");

                    out.println("<div class=\"alert alert-warning alert-dismissible clients popupAlert\" role=\"alert\">");
                    out.println("<button type=\"button\" class=\"fermer\">Annuler</button>");
                    ArrayList<Client> listeCli = new ArrayList<Client>();
                    listeCli.addAll(WelcomeServlet.listeCli);
                    out.println("<div class=\"list-group\">");
                    out.println("<a class=\"list-group-item disabled\">Liste des clients</a>");

                    if (!listeCli.isEmpty()) {
                        for (Client cli : listeCli) {
                            out.println("<a href=\"#this\" onClick=\"Affiche('#compte_" + cli.getId() + "')\" href=\"#\" class=\"list-group-item\">" + cli.getLastName() + " " + cli.getFirstName() + "</a>");
                            ArrayList<Account> comptListe = new ArrayList<Account>();
                            out.println("<div id=\"compte_" + cli.getId() + "\" style=\"width:50%; margin:auto;\" class=\"alert alert-info alert-dismissible comptes\" role=\"alert\">");
                            out.println("<div class=\"list-group\">");
                            out.println("<a href=\"#this\" class=\"list-group-item disabled\">Comptes</a>");
                            for (Account c : cli.getListAccount()) {
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
                out.println("</div>");}

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
