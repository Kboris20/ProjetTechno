package utilities;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.jsp.JspWriter;

public class WebUtilities {
    public static void doHeader(PrintWriter out, String title){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+ title +"</title>");
        out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");    
        out.println("<meta name=\"viewport\" content=\"width=device-width; initial-scale=0.5; maximum-scale=0.5; user-scalable=0;\" />");
        out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
        out.println("<meta names=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />");
        out.println("</head>");
        out.println("<body style=\"padding: 10px;\">");
        out.println("<h1>"+ title +"</h1>");
    }
    
    public static void doHeader(JspWriter out, String title) throws IOException{
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+ title +"</title>");
        out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("</head>");
        out.println("<body style=\"padding: 10px;\">");
        out.println("<h1>"+ title +"</h1>");
    }
    
    public static void doFooter(PrintWriter out){
        out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
        out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }
    
    public static void doFooter(JspWriter out) throws IOException{
        out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
        out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }
    
//    public static void doHeader(PrintWriter out, String title){
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>"+ title +"</title>");
//        out.println("</head>");
//        out.println("<body style=\"padding: 10px;\">");
//        out.println("<h1>"+ title +"</h1>");
//    }
//    
//    public static void doHeader(JspWriter out, String title) throws IOException{
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>"+ title +"</title>");
//        out.println("</head>");
//        out.println("<body style=\"padding: 10px;\">");
//        out.println("<h1>"+ title +"</h1>");
//    }
//    
//    public static void doFooter(PrintWriter out){
//        out.println("</body>");
//        out.println("</html>");
//    }
//    
//    public static void doFooter(JspWriter out) throws IOException{
//        out.println("</body>");
//        out.println("</html>");
//    }
}
