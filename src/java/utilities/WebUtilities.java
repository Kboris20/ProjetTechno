package utilities;

import dao.ClientDao;
import dao.CompteDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import modele.Client;
import modele.Compte;
import servlets.HtmlHttpUtils;

public class WebUtilities {

    private static void doHeader(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");

        out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("<!-- Latest compiled and minified CSS -->");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
        out.println("<!-- Optional theme -->");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">");
        out.println("<!-- Latest compiled and minified JavaScript -->");
        out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");

        out.println("<meta name=\"viewport\" content=\"width=device-width; initial-scale=0.5; maximum-scale=0.5; user-scalable=0;\" />");
        out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
        out.println("<meta names=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />");
        out.println("</head>");
        out.println("<body style=\"background-color: #C0C0C0; padding-bottom:30px\">");
        out.println("<div style=\" position: relative; margin:0px ; background-color: #C0C0C0;\">");

        out.println("<div class=\"row\">");
        out.println("<div style=\"padding-left:30px\" class=\"col-md-6\">");
        out.println("<h1>" + title + "</h1>");
    }

    private static void doUser(PrintWriter out, HttpServletRequest request) {
        out.println("<div style=\"text-align: right; padding-right:30px\" class=\"col-md-6\">");
        out.println("<p>Utilisateur: " + HtmlHttpUtils.getUser(request) + "<span class=\"glyphicon glyphicon-user\"></span>,");
        out.println("<a href=\"" + request.getContextPath() + "/logout\">Deconnecter <span class=\"glyphicon glyphicon-log-out\"></span></a>");
        out.println("</p>");
        out.println("</div>");
    }

    private static void doDivBody(PrintWriter out) {
        out.println("<br/><center>");
        out.println("<div style=\"width:80%; border-radius: 25px; padding:30px; height:100%; text-align:left; background-color:#f2f2f2\">");
    }

    public static void doHeader(PrintWriter out, String title, HttpServletRequest request) {
        doHeader(out, title);
        out.println("</div>");
        doUser(out, request);
        out.println("</div>");
        doDivBody(out);
    }

    public static void doHeader(PrintWriter out, String title, HttpServletRequest request, String page, Integer id) {
        doHeader(out, title);
        out.println("</div>");
        doUser(out, request);
        out.println("</div>");

        out.println("<br/>");

        out.println("<ul class=\"nav nav-tabs\">");
        if (page.equalsIgnoreCase("home")) {
            out.println("<ul class=\"nav nav-tabs\">");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"gestionTransaction\">Transferts</a></li>");
        } else if (page.equalsIgnoreCase("clients")) {

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"index?trans=false\">Clients</a></li>");
        } else if (page.equalsIgnoreCase("clientDetail") && id > 0) {
            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(id);
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"afficherClient?trans=false&id=" + id + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
        } else if (page.equalsIgnoreCase("compteMod") && id > 0) {
            Compte cpt = new Compte();
            Compte cpte = new Compte();
            cpt.setIdentifiant(id);
            cpte = CompteDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(CompteDao.researchOwnerId(cpte.getIdentifiant()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"modifierCompte?id=" + id + "&idCli=" + cli.getIdentifiant() + "\">" + cpte.getNom() + "</a></li>");

        } else if (page.equalsIgnoreCase("tranfere") && id > 0) {
            Compte cpt = new Compte();
            Compte cpte = new Compte();
            cpt.setIdentifiant(id);
            cpte = CompteDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(CompteDao.researchOwnerId(cpte.getIdentifiant()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"modifierCompte?id=" + id + "&idCli=" + cli.getIdentifiant() + "\">" + cpte.getNom() + "</a></li>");

        }
        out.println("</ul>");
        out.println("<br/>");
        out.println("</div>");
        doDivBody(out);
    }

    public static void doHeader(PrintWriter out, String title, String sous_title, HttpServletRequest request, String page, Integer id, Integer id1) {
        doHeader(out, title);
        if (!sous_title.equalsIgnoreCase("")) {
            out.println("<h3><i>" + sous_title + "</i></h3>");
        }

        out.println("</div>");
        doUser(out, request);
        out.println("</div>");

        out.println("<br/>");

        out.println("<ul class=\"nav nav-tabs\">");
        if (page.equalsIgnoreCase("transfere")) {
            Compte cpt = new Compte();
            Compte cpte = new Compte();
            cpt.setIdentifiant(id);
            cpte = CompteDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(CompteDao.researchOwnerId(cpte.getIdentifiant()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"transfereCompteACompte?id=" + id + "&id1=-1&idCli=" + cli.getIdentifiant() + "\">" + cpte.getNom() + "</a></li>");

        } else if (page.equalsIgnoreCase("choixCli")) {
            Compte cpt = new Compte();
            Compte cpte = new Compte();
            cpt.setIdentifiant(id);
            cpte = CompteDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(CompteDao.researchOwnerId(cpte.getIdentifiant()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
            out.println("<li role=\"presentation\"><a href=\"transfereCompteACompte?id=" + id + "&id1=-1&idCli=" + cli.getIdentifiant() + "\">" + cpte.getNom() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"index?idCli=" + cli.getIdentifiant() + "&id1=" + id + "&trans=true\">Clients</a></li>");

        } else if (page.equalsIgnoreCase("choixCpt")) {
            Compte cpt = new Compte();
            Compte cpte = new Compte();
            cpt.setIdentifiant(id1);
            cpte = CompteDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setIdentifiant(CompteDao.researchOwnerId(cpte.getIdentifiant()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?trans=false&id=" + cli.getIdentifiant() + "\">" + cli.getNom() + "_" + cli.getPrenom() + "</a></li>");
            out.println("<li role=\"presentation\"><a href=\"transfereCompteACompte?id=" + cpte.getIdentifiant() + "&id1=-1&idCli=" + cli.getIdentifiant() + "\">" + cpte.getNom() + "</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?idCli=" + cli.getIdentifiant() + "&id1=" + id1 + "&trans=true\">Clients</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"afficherClient?id1=" + id1 + "&trans=true&id=" + id + "\">Comptes</a></li>");

        }
        out.println("</ul>");
        out.println("<br/>");
        out.println("</div>");
        doDivBody(out);
    }

    public static void doHeader(PrintWriter out, String title, String sous_title, HttpServletRequest request) {
        doHeader(out, title);
        out.println("<h2>" + sous_title + "</h2>");
        out.println("</div>");
        doUser(out, request);
        out.println("</div>");
        doDivBody(out);
    }

    public static void doHeader(JspWriter out, String title, HttpServletRequest request, String page) throws IOException {
        if (page.equalsIgnoreCase("logging")) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");
            out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
            out.println("</head>");
            out.println("<body style=\"padding: 10px;\">");
            out.println("<h1>" + title + "</h1>");
        } else {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");

            out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
            out.println("<!-- Latest compiled and minified CSS -->");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
            out.println("<!-- Optional theme -->");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">");
            out.println("<!-- Latest compiled and minified JavaScript -->");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");

            out.println("<meta name=\"viewport\" content=\"width=device-width; initial-scale=0.5; maximum-scale=0.5; user-scalable=0;\" />");
            out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
            out.println("<meta names=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />");
            out.println("</head>");
            out.println("<body style=\"background-color: #C0C0C0; padding-bottom:30px\">");
            out.println("<div style=\" position: relative; margin:0px ; background-color: #C0C0C0;\">");

            out.println("<div class=\"row\">");
            out.println("<div style=\"padding-left:30px\" class=\"col-md-6\">");
            out.println("<h1>" + title + "</h1>");
            out.println("</div>");
            out.println("<div style=\"text-align: right; padding-right:30px\" class=\"col-md-6\">");
            out.println("<p>Utilisateur: " + HtmlHttpUtils.getUser(request) + "<span class=\"glyphicon glyphicon-user\"></span>,");
            out.println("<a href=\"" + request.getContextPath() + "/logout\">Deconnecter <span class=\"glyphicon glyphicon-log-out\"></span></a>");
            out.println("</p>");
            out.println("</div>");
            out.println("<br/>");
            out.println("<br/>");
            out.println("<ul class=\"nav nav-tabs\">");
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?trans=false\">Clients</a></li>");
            out.println("</ul>");
            out.println("<br/>");
            out.println("</div>");

            out.println("<br><center>");

            out.println("<div style=\"width:80%; border-radius: 25px; margin:0px; padding:30px; height:100%; text-align:left; background-color:#f2f2f2\">");
        }
    }

    // Les footers
    public static void doFooter(PrintWriter out) {
        out.println("</center>");
        out.println("</div>");
        out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
        out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }

    public static void doFooter(JspWriter out, String page) throws IOException {
        if (page.equalsIgnoreCase("logging")) {
            out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
            out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            out.println("</body>");
            out.println("</html>");
        } else {
            out.println("</center>");
            out.println("</div>");
            out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
            out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            out.println("</body>");
            out.println("</html>");
        }
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
