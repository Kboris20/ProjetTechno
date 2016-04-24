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
 * @author christop.francill
 */
public class DoModifier extends HttpServlet {

    private Client client;
    private List<Client> listClients;

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
            if (!HtmlHttpUtils.isAuthenticate(request)){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
            client = new Client();
            client.setId(Integer.parseInt(request.getParameter("id")));
            listClients = ClientDao.research(client);
            if (listClients.size() > 0) {
                client = listClients.get(0);
                client.setLastName(request.getParameter("nom"));
                client.setFirstName(request.getParameter("prenom"));
                client.setAddres(request.getParameter("adresse"));
                client.setCity(request.getParameter("ville"));
                ClientDao.update(client);
                response.sendRedirect(request.getContextPath() + "/modifier?id=" + client.getId() + "&mod=true");
            }
            response.sendRedirect(request.getContextPath() + "/index?mod=error1");
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/index?mod=error2&text=\"" + ex.getMessage() + "\"");
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
