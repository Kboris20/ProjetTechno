package servlets;

import dao.AccountDao;
import dao.ClientDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Account;
import modele.Client;
import utilities.WebUtilities;

/**
 *
 * @author Boris
 */
public class DisplayClient extends HttpServlet {

    private Client client;
    private List<Client> listClients;
    private List<Account> listAccounts;
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

            WebUtilities.doHeader(out, "Afficher un client", request, "clientDetail", Integer.parseInt(request.getParameter("idCli")));

            client = new Client();
            client.setId(Integer.parseInt(request.getParameter("idCli")));
            listClients = new ArrayList<Client>();
            listClients.addAll(ClientDao.research(client));

            if (listClients.size() > 0) {
                client = listClients.get(0);
                try {
                    if (request.getParameter("add").equals("true")) {
                        Clients.clients = new ArrayList<Client>();
                        Clients.clients.addAll(ClientDao.researchAll());
                        out.println("<div class=\"alert alert-success popupInformation\">");
                        out.println("Client crée.");
                        out.println("</div>");
                    }
                } catch (Exception ex) {
                }

                try {
                    if (request.getParameter("mod").equals("true")) {
                        Clients.clients = new ArrayList<Client>();
                        Clients.clients.addAll(ClientDao.researchAll());
                        out.println("<div class=\"alert alert-success popupInformation\">");
                        out.println("Client modifié.");
                        out.println("</div>");
                    }
                } catch (Exception ex) {
                }

                out.println("<fieldset><legend>" + client.getLastName() + " " + client.getFirstName() + "</legend>");
                out.println(client.getAddres() + "<br/>");
                out.println(client.getCity());

                //########################################### Confirmation de suppression d'un compte ##############################################
                try {
                    if (request.getParameter("dele").equals("true")) {
                        out.println("<div class=\"confirm\"></div>");
                        out.println("<div class=\"confirmPopup\">");

                        listAccounts = new ArrayList<Account>();
                        account = new Account();
                        account.setId(Integer.valueOf(request.getParameter("id")));
                        listAccounts.addAll(AccountDao.research(account));

                        out.println("<b><u>Confirmation</u></b>");
                        out.println("<p>Voulez vous réellement supprimer le compte</p>");
                        out.println("<b> " + listAccounts.get(0).getName() + ", solde: " + listAccounts.get(0).getBalance() + "</b>");
                        out.println("<br/>");

                        out.println("<a href=\"deleteCompte?id=" + request.getParameter("id") + "&cliId=" + request.getParameter("idCli") + "\" class=\"btn btn-success btn-mini\"> <span class=\"glyphicon glyphicon-ok\"></span> </a>");
                        out.println("<a href=\"javascript:hidePopup();\" class=\"btn btn-danger btn-mini\"><span class=\"glyphicon glyphicon-remove\"></span></a>");
                        out.println("</div>");
//###################################################################################################################
                    }
                } catch (Exception ex) {
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher("allComptes?idCli=" + client.getId());
                dispatcher.include(request, response);

                out.println("</fieldset>");
            } else {
                out.println("<div class=\"alert\">");
                out.println("Aucun client n'existe avec cet identifiant.");
                out.println("</div>");
            }
            out.println("<br/><br/>");
        } catch (Exception ex) {
            out.println(ex.getMessage());
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
