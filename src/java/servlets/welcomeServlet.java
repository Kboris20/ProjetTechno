/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
public class welcomeServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            WebUtilities.doHeader(out, "Gestion des clients (CRUD)", request, "home", 0);
            out.println("<hr/>");
            
            out.println("<center>");
            out.println("<br/>");
            try {
                if (request.getParameter("nbFois") == null) {
                    out.println("<h2>Bonjour Mme/M : " + HtmlHttpUtils.getUser(request) + " !</h2>");
                }
            } catch (NullPointerException ex) {
            }
            
            out.println("<h3><b><u>Statistiques</u></b></h3>");
            out.println("<div class=\"row\">");
            
            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Bar Chart</h3>");
            out.println("<canvas id=\"canvas\" height=\"450\" width=\"600\"></canvas>");
            out.println("<script>");
            out.println("var randomScalingFactor = function(){ return Math.round(Math.random()*100)};");
            out.println("var barChartData = {");
            out.println("labels : [\"January\",\"February\",\"March\",\"April\",\"May\",\"June\",\"July\"],");
            out.println("datasets : [ {");
            out.println("fillColor : \"rgba(297,125,49,1)\",");
            out.println("strokeColor : \"rgba(0,0,0,1)\",");
            out.println("highlightFill: \"rgba(220,220,220,0.75)\",");
            out.println("highlightStroke: \"rgba(0,0,0,1)\",");
            out.println("data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()] },");
            out.println("{");
            out.println("fillColor : \"rgba(217,217,217,1)\",");
            out.println("strokeColor : \"rgba(0,0,0,1)\",");
            out.println("highlightFill : \"rgba(165,165,165,1)\",");
            out.println("highlightStroke : \"rgbargba(0,0,0,1)\",");
            out.println("data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()] } ] }");
            out.println("</script>");
            out.println("</div>");
            
            out.println("<div class=\"col-md-6\" style=\"width: 50%\">");
            out.println("<h3>Pie Chart</h3>");
            out.println("<canvas id=\"chart-area\" width=\"300\" height=\"300\"/>");
            out.println("<script>");
            out.println("var pieData = [ {");
            out.println("value: 300,");
            out.println("color:\"#ff1800\",");
            out.println("highlight: \"#ffa49a\",");
            out.println("label: \"Red\" },");
            out.println("{");
            out.println("value: 50,");
            out.println("color: \"#00960b\",");
            out.println("highlight: \"#80d286\",");
            out.println("label: \"Green\" },");
            out.println("{");
            out.println("value: 100,");
            out.println("color: \"#ed7d31\",");
            out.println("highlight: \"#ecad83\",");
            out.println("label: \"Orange\" } ];");
            
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
