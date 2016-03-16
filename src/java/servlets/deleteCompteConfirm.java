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
public class deleteCompteConfirm extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
            Compte cpt = new Compte();
            cpt.setIdentifiant(Integer.parseInt(request.getParameter("id")));
            ArrayList<Compte> cptListe = CompteDao.research(cpt);
            WebUtilities.doHeader(out, "Supprimer un client");
            if(cptListe.size()>0){
                cpt = cptListe.get(0);
                String owner = CompteDao.researchOwner(cpt.getIdentifiant());
                out.println("<h3>Voulez-vous vraiment supprimer le compte "+ cpt.getNom() + " de " + owner +" ?</h3>");
                out.println("<form action=\"deleteCompte\">");
                    out.println("<input type=\"hidden\" name=\"id\" value=\""+ cpt.getIdentifiant() +"\"/>");
                    out.println("<input type=\"hidden\" name=\"cliId\" value=\""+ request.getParameter("idCli") +"\"/>");
                    out.println("<button class=\"btn btn-danger\" type=\"submit\"><i class=\"icon-white icon-trash\"></i> Supprimer</button>");
                out.println("</form>");
                out.println("<a href=\"afficherClient?id=" + request.getParameter("idCli") + "\" class=\"btn btn-inverse\"><i class=\"icon-white icon-share-alt\"></i> Annuler</a>");
            }else{
                out.println("<div class=\"alert alert-warning\">");
                out.println("Aucun compte n'existe avec cet identifiant.");
                out.println("</div>");
            }
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
