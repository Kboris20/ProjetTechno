/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.TransactionDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            if (!HtmlHttpUtils.isAuthenticate(request)){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (NullPointerException ex) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        try {
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

            double amountTransactions = TransactionDao.getAmountTransactions();
            double amountTransactionsByUser = TransactionDao.getAmountTransactionsByUser(userConnected);
            //amountTransactionsByUser = (amountTransactionsByUser * 100) / amountTransactions;
            int nbTransactions = TransactionDao.getNbTransactions();
            int nbTransactionsByUser = TransactionDao.getNbTransactionsByUser(userConnected);
            //nbTransactionsByUser = (nbTransactionsByUser * 100) / nbTransactions;

            //ArrayList<User> users = UserDao.researchAll();

            out.println("<div class=\"performancesBox\"><h2 class=\"performancesTitle\">Mes performances</h2></div>");
            
            out.println("<div class=\"row\">");
            //Line Chart : Chacune des barres affiche le nombre de transferts pour un utilisateur
            /*out.println("<div class=\"col-md-4\" style=\"width: 50%\">");
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
            out.println("</div>");*/
            
            //Pie Chart : La part en "rouge" affiche le montant des transferts au total ; tout utilisateur compris
            //            et la part en "bleu" affiche le montant des transferts cumulé ; de l'utilisateur connecté (sa performance)
            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Mes transferts (en francs)</h3>");
            out.println("<canvas id=\"chart-area-2\" height=\"300\" width=\"300\"/>");
            out.println("<script>");
            out.println("var pieData2 = [ {");
            out.println("value: " + amountTransactionsByUser + ",");
            out.println("color:\"#46BFBD\",");
            out.println("highlight: \"#5AD3D1\",");
            out.println("label: \"" + userConnected + "\" },");
            out.println("{");
            out.println("value: " + amountTransactions + ",");
            out.println("color: \"#F7464A\",");
            out.println("highlight: \"#FF5A5E\",");
            out.println("label: \"Total\" } ];");
            out.println("</script>");
            out.println(" </div>");

            //Pie Chart : La part en "rouge" affiche le nombre de transferts au total ; tout utilisateur compris
            //            et la part en "bleu" affiche le nombre de transferts calculé ; de l'utilisateur connecté (sa performance)
            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Mes transferts (en quantité)</h3>");
            out.println("<canvas id=\"chart-area-1\" height=\"300\" width=\"300\"/>");
            out.println("<script>");
            out.println("var pieData1 = [ {");
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
            out.println("var ctx1 = document.getElementById(\"chart-area-1\").getContext(\"2d\");");
            out.println("window.myPie1 = new Chart(ctx1).Pie(pieData1);");
            out.println("var ctx2 = document.getElementById(\"chart-area-2\").getContext(\"2d\");");
            out.println("window.myPie2 = new Chart(ctx2).Pie(pieData2); };");
            /*out.println("var ctx3 = document.getElementById(\"canvas\").getContext(\"2d\");");
            out.println("window.myBar = new Chart(ctx3).Bar(barChartData, { responsive : true }); };");*/
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
