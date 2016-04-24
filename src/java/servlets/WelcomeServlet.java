/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ClientDao;
import dao.TransactionDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.User;
import utilities.WebUtilities;

/**
 *
 * @author boris.klett
 */
public class WelcomeServlet extends HttpServlet {

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
        String userConnected = HtmlHttpUtils.getUser(request);

        try {
            HtmlHttpUtils.isAuthenticate(request);
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
            /* TODO output your page here. You may use following sample code. */
            WebUtilities.doHeader(out, "Gestion des clients (CRUD)", request, "home", 0);
            out.println("<hr/>");

            out.println("<center>");
            out.println("<br/>");
            try {
                if (request.getParameter("nbFois") == null) {
                    out.println("<h2>Bonjour Mme/M : " + userConnected + " !</h2>");
                }
            } catch (NullPointerException ex) {
            }

            int nbTransactions = TransactionDao.getNbTransactions();
            int nbTransactionsByUser = TransactionDao.getNbTransactionsByUser(userConnected);
            
            ArrayList<User> users = UserDao.researchAll();

            out.println("<h3><b><u>Statistiques</u></b></h3>");
            out.println("<div class=\"row\">");

            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Nombre de transferts par utilisateur</h3>");
            out.println("<canvas id=\"canvas\" height=\"300\" width=\"600\"></canvas>");
            out.println("<script>");
            out.println("var barChartData = {");
            
            for (User user : users) {
                out.println("labels : [\"" + user.getUsername() + "\",");
            }
            out.println("\"" + users.get(users.size() - 1).getUsername() + "\"],");
            
            out.println("datasets : [ {");
            out.println("label : \"Nombre de transactions\",");
            out.println("fillColor : \"rgba(151,187,205,0.5)\",");
            out.println("strokeColor : \"rgba(151,187,205,0.8)\",");
            out.println("highlightFill : \"rgba(151,187,205,0.75)\",");
            out.println("highlightStroke : \"rgbargba(151,187,205,1)\",");
            out.println("data : [60, 20, 40, 10] } ] };");
            out.println("</script>");
            out.println("</div>");

            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Mes transferts</h3>");
            out.println("<canvas id=\"chart-area\" width=\"300\" height=\"300\"/>");
            out.println("<script>");
            out.println("var pieData = [ {");
            out.println("value: " + nbTransactionsByUser + ",");
            out.println("color:\"#46BFBD\",");
            out.println("highlight: \"#5AD3D1\",");
            out.println("label: \"" + userConnected + "\" },");
            out.println("{");
            out.println("value: " + nbTransactions + ",");
            out.println("color: \"#F7464A\",");
            out.println("highlight: \"#FF5A5E\",");
            out.println("label: \"Total\" } ];");

            out.println("window.onload = function(){");
            out.println("var ctx = document.getElementById(\"chart-area\").getContext(\"2d\");");
            out.println("window.myPie = new Chart(ctx).Pie(pieData);");
            out.println("var ctx = document.getElementById(\"canvas\").getContext(\"2d\");");
            out.println("window.myBar = new Chart(ctx).Bar(barChartData, { responsive : true }); };");
            out.println("</script>");
            out.println(" </div>");

            out.println("</center>");
            out.println(" </div>");

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
