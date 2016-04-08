/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import dao.CompteDao;
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
import modele.Compte;
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
                if (request.getParameter("transcmpt").equalsIgnoreCase("true")) {
                    out.println("<div style=\"border: 1px; border-radius: 25px\" class=\"alert alert-warning alert-dismissible\" role=\"alert\">");
                    out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                    ArrayList<Client> listeCli = new ArrayList<Client>();
                    listeCli.addAll(ClientDao.researchAll());
                    out.println("<div class=\"list-group\">");
                    out.println("<a href=href=\"#\" class=\"list-group-item disabled\">Liste des clients</a>");

                    if (!listeCli.isEmpty()) {
                        for (Client cli : listeCli) {
                            out.println("<a href=\"TransfertFromTransfertManag?status="+request.getParameter("status")+"&debOrCred=" + request.getParameter("debOrCred") + "&cliDest=" + cli.getIdentifiant() + "&idCompteDeb=" + request.getParameter("idCompteDeb") + "&idCompteCred=" + request.getParameter("idCompteCred") + "\" class=\"list-group-item\">" + cli.getNom() + " " + cli.getPrenom() + "</a>");

                        }
                    }
                    out.println("</div>");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            try {
                if (request.getParameter("cliDest") != null) {
                    out.println("<div style=\"border: 1px; border-radius: 25px\" class=\"alert alert-warning alert-dismissible\" role=\"alert\">");
                    out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>");
                    Client cli = new Client();
                    cli.setIdentifiant(Integer.parseInt(request.getParameter("cliDest")));
                    ArrayList<Client> cliListe = new ArrayList<Client>();
                    List<Compte> comptListe = new LinkedList<Compte>();
                    cliListe.addAll(ClientDao.research(cli));

                    if (cliListe.size() > 0) {
                        cli = cliListe.get(0);
                        out.println("<div class=\"list-group\">");
                        out.println("<a href=\"#\" class=\"list-group-item disabled\">Liste des comptes de " + cli.getNom() + " " + cli.getPrenom() + "</a>");

                        cli.setListeCompte(CompteDao.research(cli.getIdentifiant()));
                        for (Compte c : cli.getListeCompte()) {
                            if (c.getIdentifiant() != Integer.valueOf(request.getParameter("idCompteDeb"))) {
                                comptListe.add(c);
                            }
                        }
                        if (!comptListe.isEmpty()) {

                            for (Compte compte : comptListe) {
                                if (request.getParameter("debOrCred").equalsIgnoreCase("deb")) {
                                    out.println("<a href=\"TransfertFromTransfertManag?status=cred&idCompteDeb=" + compte.getIdentifiant() + "&idCompteCred=" + request.getParameter("idCompteCred") + "\" class=\"list-group-item\">Compte: " + compte.getNom() + ", Solde: " + compte.getSolde() + "</a>");
                                } else {
                                    out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + request.getParameter("idCompteDeb") + "&idCompteCred=" + compte.getIdentifiant() + "\" class=\"list-group-item\">Compte: " + compte.getNom() + ", Solde: " + compte.getSolde() + "</a>");
                                }
                            }
                        } else {
                            out.println("<a href=\"#\" class=\"list-group-item disabled\"><i>Pas de compte disponible!</i></a>");
                        }
                    }
                    out.println("</div>");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }
            
            

            Integer idCompteDeb;
            Integer idCompteCred;
            Compte compteDeb = new Compte();
            Compte compteCred = new Compte();
            Compte compte = new Compte();
            String status = request.getParameter("status");

            if (status.equalsIgnoreCase("deb")) {
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à débiter: </h3>");
                out.println("<a href=\"TransfertFromTransfertManag?status=deb&debOrCred=deb&transcmpt=true&idCompteDeb=-1&idCompteCred=-1\"class=\"btn btn-primary\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à créditer...</h3>");
                out.println("<br/>");
            } else if (status.equalsIgnoreCase("cred")) {

                idCompteDeb = Integer.valueOf(request.getParameter("idCompteDeb"));
                out.println("<br/>");
                out.println("<h3>Le compte à débiter: </h3>");
                compte.setIdentifiant(idCompteDeb);
                compteDeb = CompteDao.research(compte).get(0);
                out.println("<b>Nom: " + compteDeb.getNom() + ", solde: " + compteDeb.getSolde() + ", propriétaire: " + CompteDao.researchOwner(idCompteDeb) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Veuillez entrer le compte à créditer: </h3>");
                out.println("<a href=\"TransfertFromTransfertManag?status=cred&debOrCred=cred&transcmpt=true&idCompteDeb=" + idCompteDeb + "&idCompteCred=-1\"class=\"btn btn-primary\"><i class=\"icon-white icon-plus\"></i>Choisir un compte</a>");
                out.println("<br/>");
            } else {

                idCompteDeb = Integer.valueOf(request.getParameter("idCompteDeb"));
                idCompteCred = Integer.valueOf(request.getParameter("idCompteCred"));
                out.println("<br/>");
                out.println("<h3>Le compte à débiter: </h3>");
                compte.setIdentifiant(idCompteDeb);
                compteDeb = CompteDao.research(compte).get(0);
                out.println("<b>Nom: " + compteDeb.getNom() + ", solde: " + compteDeb.getSolde() + ", propriétaire: " + CompteDao.researchOwner(idCompteDeb) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<a href=\"TransfertFromTransfertManag?status=allOk&idCompteDeb=" + idCompteCred + "&idCompteCred=" + idCompteDeb + "\"><button type=\"button\" class=\"btn btn-default btn-sm\" title=\"Inverser les comptes\"><span class=\"glyphicon glyphicon-sort\"></span></button></a>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<h3>Le compte à créditer: </h3>");
                compte.setIdentifiant(idCompteCred);
                compteCred = CompteDao.research(compte).get(0);
                out.println("<b>Nom: " + compteCred.getNom() + ", solde: " + compteCred.getSolde() + ", propriétaire: " + CompteDao.researchOwner(idCompteCred) + "</b>");
                out.println("<br/>");
                out.println("<br/>");
                out.println("<a href=\"transfereCheck?id1=" + idCompteDeb + "&id=" + idCompteCred + "\">");
                out.println("<button class=\"btn btn-default btn-sm\"><span class=\"glyphicon glyphicon-transfer\"></span> Transférer</button>");
                out.println("</a>");
                out.println("<br/>");
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
