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
import java.util.LinkedList;
import java.util.List;
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
public class TransfertFromTransfertManag extends HttpServlet {

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

            WebUtilities.doHeader(out, "Nouveau transfère", request, "newTransfert");
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

            try {
                if (request.getParameter("error").equals("false")) {
                    out.println("<div class=\"alert alert-warning alert-dismissible popupAlert\" role=\"alert\">");
                    out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                    out.println("<b><u>Confirmation</u></b>");
                    out.println("<p>Souhaitez vous réellement effectuer le transfert pour le montant de:</p>");
                    out.println("<b> " + request.getParameter("somme") + " CHF?</b>");
                    out.println("<br/>");
                    out.println("<a href=\"transfere?somme=" + request.getParameter("somme") + "&id=" + request.getParameter("idCompteDeb") + "&id1=" + request.getParameter("idCompteCred") + "&idCli=0\" class=\"btn btn-info btn-mini\"> <span class=\"glyphicon glyphicon-ok\"></span> Oui</a>");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            Integer idCompteDeb;
            Integer idCompteCred;
            Account compteDeb = new Account();
            Account compteCred = new Account();
            Account compt = new Account();
            String status = request.getParameter("status");

            if (status.equalsIgnoreCase("deb")) {
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à débiter: </h3>");
//                out.println("<a href=\"TransfertFromTransfertManag?status=deb&debOrCred=deb&transcmpt=true&idCompteDeb=-1&idCompteCred=-1\"class=\"btn btn-primary\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<a class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");

                out.println("<br/>");
            } else if (status.equalsIgnoreCase("cred")) {

                idCompteDeb = Integer.valueOf(request.getParameter("idCompteDeb"));
                out.println("<br/>");
                out.println("<h3>Le compte débité </h3>");
                compt.setId(idCompteDeb);
                compteDeb = AccountDao.research(compt).get(0);
                out.println("<b>Compte: " + compteDeb.getName() + ", solde: " + compteDeb.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idCompteDeb) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à créditer: </h3>");
                out.println("<a class=\"btn btn-primary choisirCompte\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<br/>");
            } else {

                idCompteDeb = Integer.valueOf(request.getParameter("idCompteDeb"));
                idCompteCred = Integer.valueOf(request.getParameter("idCompteCred"));
                out.println("<br/>");
                out.println("<h3>Le compte débité </h3>");
                compt.setId(idCompteDeb);
                compteDeb = AccountDao.research(compt).get(0);
                out.println("<b>Compte: " + compteDeb.getName() + ", solde: " + compteDeb.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idCompteDeb) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + idCompteCred + "&idCompteCred=" + idCompteDeb + "\"><button type=\"button\" class=\"btn btn-default btn-sm\" title=\"Inverser les comptes\"><span class=\"glyphicon glyphicon-sort\"></span></button></a>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Le compte crédité </h3>");
                compt.setId(idCompteCred);
                compteCred = AccountDao.research(compt).get(0);
                out.println("<b>Compte: " + compteCred.getName() + ", solde: " + compteCred.getBalance() + ", propriétaire Mme/M. : " + AccountDao.researchOwner(idCompteCred) + "</b>");
                out.println("<br/>");
                out.println("<br/>");

                out.println("<form method=\"GET\" action=\"transfereCheck\"");
                out.println("<label for=\"amount\">Montant:  CHF </label>");
                out.println("<input type=\"number\" name=\"amount\" id=\"amount\" value=\"00\"/>.");
                out.println("<input type=\"number\" name=\"cents\" id=\"cents\" value=\"00\"/>");
                out.println("<input type=\"hidden\" name=\"id1\" value=\"" + idCompteCred + "\"/>");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + idCompteDeb + "\"/>");
                out.println("<input type=\"hidden\" name=\"idCli\" value=\"0\"/>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<button type=\"submit\" class=\"btn btn-default btn-sm\"><span class=\"glyphicon glyphicon-transfer\"></span> Transférer</button>");
                out.println("</form>");
            }

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
                        if (c.getId() != Integer.valueOf(request.getParameter("idCompteDeb"))) {
                            comptListe.add(c);
                        }
                    }
                    if (!comptListe.isEmpty()) {

                        for (Account compte : comptListe) {
                            if (request.getParameter("status").equalsIgnoreCase("deb")) {
                                out.println("<a href=\"TransfertFromTransfertManag?status=cred&idCompteDeb=" + compte.getId() + "&idCompteCred=" + request.getParameter("idCompteCred") + "\" class=\"list-group-item\">Compte: " + compte.getName() + ", Solde: " + compte.getBalance() + "</a>");
                            } else {
                                out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + request.getParameter("idCompteDeb") + "&idCompteCred=" + compte.getId() + "\" class=\"list-group-item\">Compte: " + compte.getName() + ", Solde: " + compte.getBalance() + "</a>");
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
