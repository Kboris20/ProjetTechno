package servlets;

import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

    private Client client;
    private List<Client> listClients;
    private String parameterModify;

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
            client = new Client();
            client.setId(Integer.parseInt(request.getParameter("id")));
            listClients = ClientDao.research(client);
            WebUtilities.doHeaderPopup(out, "Modifier un client");

            try {
                parameterModify = request.getParameter("mod");

                if (parameterModify.equals("true")) {
                    out.println("<div class=\"alert alert-success\">");
                    out.println("Client modifié.");
                    out.println("</div>");
                }
            } catch (Exception ex) {
            }

            if (listClients.size() > 0) {
                client = listClients.get(0);
                out.println("<form  id=\"form1\" name=\"form1\" method=\"post\"  action=\"doModifier\">");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + client.getId() + "\"/>");
                out.println("<p>");

                out.println("<table>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<label for=\"nom\">Nom</label>");
                out.println("</td>");
                out.println("<td>");
                out.println("<input type=\"text\" name=\"nom\" id=\"nom\" value=\"" + client.getLastName() + "\" required/>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<label for=\"prenom\">Prénom</label>");
                out.println("</td>");
                out.println("<td>");
                out.println("<input type=\"text\" name=\"prenom\" id=\"prenom\" value=\"" + client.getFirstName() + "\" required/>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<label for=\"adresse\">Adresse</label>");
                out.println("</td>");
                out.println("<td>");
                out.println("<input type=\"text\" name=\"adresse\" id=\"adresse\" value=\"" + client.getAddres() + "\" required/>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<label for=\"ville\">Ville</label>");
                out.println("</td>");
                out.println("<td>");
                out.println("<input type=\"text\" name=\"ville\" id=\"ville\" value=\"" + client.getCity() + "\" required/>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<button title=\"Modifier\" class=\"btn btn-warning\"><i class=\"icon-white icon-pencil\"></i></button>");
                out.println("<a href=\"javascript:hidePopup();\" class=\"btn btn-danger btn-mini\"><span class=\"glyphicon glyphicon-remove\"></span></a>");

                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
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
