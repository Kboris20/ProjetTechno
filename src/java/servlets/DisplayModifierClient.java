/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class DisplayModifierClient extends HttpServlet {

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
            Client cli = new Client();
            cli.setId(Integer.parseInt(request.getParameter("id")));
            ArrayList<Client> cliListe = ClientDao.research(cli);
            WebUtilities.doHeader(out, "Modifier un client", request, "clientDetail", Integer.parseInt(request.getParameter("id")));

            try {
                String modParam = request.getParameter("mod");

                if (modParam.equals("true")) {
                    out.println("<div class=\"alert alert-success\">");
                    out.println("Client modifié.");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            if (cliListe.size() > 0) {
                cli = cliListe.get(0);
                out.println("<form  id=\"form1\" name=\"form1\" method=\"post\"  action=\"doModifier\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + cli.getId() + "\"/>");
                out.println("<p>");
                out.println("<label for=\"nom\">Nom</label>");
                out.println("<input type=\"text\" name=\"nom\" id=\"nom\" value=\"" + cli.getLastName() + "\"/>");
                out.println("</p>");
                out.println("<p>");
                out.println("<label for=\"prenom\">Prénom</label>");
                out.println("<input type=\"text\" name=\"prenom\" id=\"prenom\" value=\"" + cli.getFirstName() + "\"/>");
                out.println("</p>");
                out.println("<p>");
                out.println("<label for=\"adresse\">Adresse</label>");
                out.println("<input type=\"text\" name=\"adresse\" id=\"adresse\" value=\"" + cli.getAddress() + "\"/>");
                out.println("</p>");
                out.println("<p>");
                out.println("<label for=\"ville\">Ville</label>");
                out.println("<input type=\"text\" name=\"ville\" id=\"ville\" value=\"" + cli.getCity() + "\"/>");
                out.println("</p>");
                out.println("<button class=\"btn btn-warning\"><i class=\"icon-white icon-pencil\"></i> Modifier</button>");
                out.println("</form>");
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
