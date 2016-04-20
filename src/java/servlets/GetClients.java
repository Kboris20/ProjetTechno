/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;

/**
 *
 * @author boris.klett
 */
public class GetClients extends HttpServlet {

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
        
        List<Client> listeCli = new ArrayList<Client>();
        listeCli.addAll(ClientDao.researchAll());
        Integer nombreClient;
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<table class=\"table table-hover\" id=\"tableClientsListAll\">");
            out.println("<tr>");
            out.println("<td>&nbsp;</td>");
            out.println("<td class=\"listRow\">Nom</td>");
            out.println("<td class=\"listRow\">Prenom</td>");
            out.println("<td class=\"listRow\">Adresse</td>");
            out.println("<td class=\"listRow\">Ville</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("<td>&nbsp;</td>");
            out.println("</tr>");

            if (listeCli.isEmpty()) {
                out.println("<div class=\"alert alert-info\">");
                out.println("Il n'y a pas de client");
                out.println("</div>");
            } else {
                nombreClient = 0;
                for (Client cli : listeCli) {
                    out.println("<tr>");
                    out.println("<td>" + ++nombreClient + "</td>");
                    out.println("<td>" + cli.getLastName() + "</td>");
                    out.println("<td>" + cli.getFirstName() + "</td>");
                    out.println("<td>" + cli.getAddres() + "</td>");
                    out.println("<td>" + cli.getCity() + "</td>");
                    out.println("<td></td>");
                    out.println("<td></td>");
                    out.println("<td><a href=\"afficherClient?idCli=" + cli.getId() + "\" class=\"btn btn-info btn-mini\"><i class=\"icon-white icon-eye-open\" title=\"Détailler\"></i></a>");
                    out.println("<a href=\"modifier?id=" + cli.getId() + "\" class=\"btn btn-warning btn-mini\"><i class=\"icon-white icon-pencil\" title=\"Modifier\"></i></a>");
                    out.println("<a href=\"index?dele=true&id=" + cli.getId() + "\" class=\"btn btn-danger btn-mini\"><i class=\"icon-white icon-trash\" title=\"Supprimer\"></i></a></td>");
                    out.println("</tr>");
                }

            }
            out.println("</table>");
            out.println("</html>");
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
