package servlets;

import dao.AccountDao;
import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Account;
import modele.Client;
import utilities.WebUtilities;

/**
 *
 * @author christop.francill
 */
public class DoModifierCompte extends HttpServlet {

    private Client client;
    private List<Client> listClients;
    private Account account;

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
            WebUtilities.doHeader(out, "Modifier un compte", request, "clientDetail", Integer.parseInt(request.getParameter("idCli")));

            client = new Client();
            client.setId(Integer.parseInt(request.getParameter("idCli")));
            listClients = ClientDao.research(client);

            if (!listClients.isEmpty()) {
                client = listClients.get(0);

                account = new Account();
                account.setId(Integer.parseInt(request.getParameter("id")));
                account.setName(request.getParameter("nom"));
                account.setBalance(Float.valueOf(request.getParameter("solde")));
                account.setRate(Float.valueOf(request.getParameter("taux")));

                AccountDao.update(account);

                response.sendRedirect(request.getContextPath() + "/afficherClient?&idCli=" + client.getId() + "&modCpt=true");

            } else {
                out.println("Aucun client n'existe avec cet identifiant.");
            }
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
