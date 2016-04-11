/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import dao.CompteDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Compte;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class allComptes extends HttpServlet {

    private Integer nombreComptes;

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
            Enumeration parameters = request.getParameterNames();
            ArrayList<String> aParam = new ArrayList<String>();

            if (request.getParameter("idCli") != null) {
                out.println("<h3>Liste des comptes</h3>");

                Client cli = new Client();
                cli.setIdentifiant(Integer.parseInt(request.getParameter("idCli")));
                ArrayList<Client> cliListe = new ArrayList<Client>();
                cliListe.addAll(ClientDao.research(cli));

                if (cliListe.size() > 0) {
                    cli = cliListe.get(0);
                    cli.setListeCompte(CompteDao.research(cli.getIdentifiant()));

                    try {
                        if (request.getParameter("addCompte").equals("true")) {
                            out.println("<div class=\"alert alert-success\">");
                            out.println("Compte crée.");
                            out.println("</div>");
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (request.getParameter("modCpt").equals("true")) {
                            out.println("<div class=\"alert alert-success\">");
                            out.println("Compte modifié.");
                            out.println("</div>");
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        if (request.getParameter("del").equals("true")) {
                            out.println("<div class=\"alert alert-success\">");
                            out.println("Compte supprimé.");
                            out.println("</div>");
                        }
                    } catch (Exception ex) {
                    }
                    out.println("<a href=\"ajouterCompte.jsp?idCli=" + cli.getIdentifiant() + "\" class=\"btn btn-primary\"><i class=\"icon-white icon-plus\" title=\"Nouveau compte\"></i></a>");
                    out.println("<br/>");
                    if (cli.getListeCompte().size() > 0) {
                        out.println("<form action=\"deleteMultiCompteConfirm\">");
                        out.println("<table class=\"table table-hover inset\" id=\"tableClientAllComptes\">");
                        out.println("<tr>");
                        out.println("<td>&nbsp;</td>");
                        out.println("<td class=\"listRow\">Nom</td>");
                        out.println("<td class=\"listRow\">Solde</td>");
                        out.println("<td class=\"listRow\">Taux</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("<td>&nbsp;</td>");
                        out.println("</tr>");
                        nombreComptes = 0;
                        for (Compte compt : cli.getListeCompte()) {
                            out.println("<tr class=\"trCompte\">");

                            out.println("<td>" + ++nombreComptes + "</td>");
                            out.println("<td>" + compt.getNom() + "</td>");
                            out.println("<td>" + compt.getSolde() + "</td>");
                            out.println("<td>" + compt.getTaux() + "</td>");
                            out.println("<td><a href=\"transfereCompteACompte?id=" + compt.getIdentifiant() + "&id1=-1&idCli=" + cli.getIdentifiant() + "\" class=\"btn btn-primary btn-mini\"><span class=\"glyphicon glyphicon-transfer\" title=\"Transférer\"></span></a>");
                            out.println("<a href=\"modifierCompte?id=" + compt.getIdentifiant() + "&idCli=" + cli.getIdentifiant() + "\" class=\"btn btn-warning btn-mini\"><i class=\"icon-white icon-pencil\" title=\"Modifier\"></i></a>");
                            out.println("<a href=\"afficherClient?id=" + compt.getIdentifiant() + "&idCli=" + cli.getIdentifiant() + "&dele=true\" class=\"btn btn-danger btn-mini\"><i class=\"icon-white icon-trash\" title=\"Supprimer\"></i></a></td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.println("");
                        out.println("</form>");
                    } else {
                        out.println("<div class=\"alert\">");
                        out.println("Aucun compte n'existe pour ce client.");
                        out.println("</div>");
                    }

                } else {
                    out.println("<div class=\"alert\">");
                    out.println("Aucun client n'existe avec cet identifiant.");
                    out.println("</div>");
                }
            } else {
                WebUtilities.doHeader(out, "Afficher tous les comptes", request, "clientDetail", Integer.valueOf(request.getParameter("id")));

                ArrayList<Compte> listeCpt = new ArrayList<Compte>();
                listeCpt.addAll(CompteDao.research(new Compte()));

                if (listeCpt.size() > 0) {
                    out.println("<table class=\"table table-hover\" id=\"tableComptesAllComptes\">");
                    out.println("<tr>");
                    out.println("<td class=\"listRow\">Nom</td>");
                    out.println("<td class=\"listRow\">Solde</td>");
                    out.println("<td class=\"listRow\">Taux</td>");
                    out.println("<td class=\"listRow\">Client</td>");
                    out.println("</tr>");
                    for (Compte cpt : listeCpt) {
                        out.println("<tr>");
                        out.println("<td>" + cpt.getNom() + "</td>");
                        out.println("<td>" + cpt.getSolde() + "</td>");
                        out.println("<td>" + cpt.getTaux() + "</td>");
                        out.println("<td>" + CompteDao.researchOwner(cpt.getIdentifiant()) + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<div class=\"alert\">");
                    out.println("Aucun compte n'existe.");
                    out.println("</div>");
                }
                out.println("<a href=\"index?trans=false\" class=\"btn btn-inverse\"><i class=\"icon-white icon-share-alt\"></i> Retour à la liste</a>");

                WebUtilities.doFooter(out);
                out.close();
            }
        } finally {
            //out.close();
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
