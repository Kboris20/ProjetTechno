package utilities;

import dao.ClientDao;
import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import modele.Client;
import modele.Account;
import servlets.HtmlHttpUtils;

public class WebUtilities {

    private static void doHeader(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        
        out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js\"></script>");

        out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("<link href=\"theme/css/style.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("<!-- Latest compiled and minified CSS -->");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">");
        out.println("<!-- Optional theme -->");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">");
        out.println("<!-- Latest compiled and minified JavaScript -->");
        out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>");
        out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js\"></script>");

        out.println("<meta name=\"viewport\" content=\"width=device-width; initial-scale=0.5; maximum-scale=0.5; user-scalable=0;\" />");
        out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
        out.println("<meta names=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\" />");
        out.println("</head>");
        out.println("<body class=\"bodySite\">");

    }

    private static void doDivBody(PrintWriter out, String title, HttpServletRequest request) {
        out.println("<center>");
        out.println("<div class=\"divContainer\">");
        out.println("<div  class=\"divCommonHeader\">");

        out.println("<div class=\"row\">");
        out.println("<div id=\"divPageTitle\" class=\"col-md-8;\">");
        out.println("<h1>" + title + "</h1>");
        out.println("</div>");

        out.println("<div id=\"divPageUser\" class=\"col-md-4\">");
        out.println("<p><span class=\"glyphicon glyphicon-user\" title=\"Utilisateur\"></span> " + HtmlHttpUtils.getUser(request) + ",");
        out.println("<a href=\"" + request.getContextPath() + "/logout\"><span class=\"glyphicon glyphicon-log-out\" title=\"Déconnection\"></span></a>");
        out.println("</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<br/>");
    }

    public static void doHeader(PrintWriter out, String title, HttpServletRequest request, String page) {
        doHeader(out, title);
        doDivBody(out, title, request);

        out.println("<ul class=\"nav nav-tabs\">");
        if (page.equalsIgnoreCase("transfertManag")) {
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"transferts\">Transferts</a></li>");
        } else {
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"gestionTransaction\">Transferts</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"TransfertFromTransfertManag?status=deb\">New transfert</a></li>");
        }
        out.println("</ul>");
        out.println("</div>");
        out.println("<div class=\"divContent\">");
    }

    public static void doHeader(PrintWriter out, String title, HttpServletRequest request, String page, Integer id) {
        doHeader(out, title);
        doDivBody(out, title, request);

        out.println("<ul class=\"nav nav-tabs\">");
        if (page.equalsIgnoreCase("home")) {
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"gestionTransaction\">Transferts</a></li>");
        } else if (page.equalsIgnoreCase("clients")) {
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"index\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"gestionTransaction\">Transferts</a></li>");
        } else if (page.equalsIgnoreCase("clientDetail") && id > 0) {
            Client c = new Client();
            Client cli = new Client();
            c.setId(id);
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index\">Clients</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"afficherClient?idCli=" + id + "\">" + cli.getLastName() + " " + cli.getFirstName() + "</a></li>");
        } else if (page.equalsIgnoreCase("compteMod") && id > 0) {
            Account cpt = new Account();
            Account cpte = new Account();
            cpt.setId(id);
            cpte = AccountDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setId(AccountDao.researchOwnerId(cpte.getId()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index?\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?idCli=" + cli.getId() + "\">" + cli.getLastName() + " " + cli.getFirstName() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"modifierCompte?id=" + id + "&idCli=" + cli.getId() + "\">" + cpte.getName() + "</a></li>");

        } else if (page.equalsIgnoreCase("tranfere") && id > 0) {
            Account cpt = new Account();
            Account cpte = new Account();
            cpt.setId(id);
            cpte = AccountDao.research(cpt).get(0);

            Client c = new Client();
            Client cli = new Client();
            c.setId(AccountDao.researchOwnerId(cpte.getId()));
            cli = ClientDao.research(c).get(0);

            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index\">Clients</a></li>");
            out.println("<li role=\"presentation\"><a href=\"afficherClient?idCli=" + cli.getId() + "\">" + cli.getLastName() + "_" + cli.getFirstName() + "</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"modifierCompte?id=" + id + "&idCli=" + cli.getId() + "\">" + cpte.getName() + "</a></li>");

        } else if (page.equalsIgnoreCase("transactions")) {
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\" class=\"active\"><a href=\"gestionTransaction\">Transferts</a></li>");
        }
        out.println("</ul>");
        out.println("</div>");
        out.println("<div class=\"divContent\">");
    }

    public static void doHeader(PrintWriter out, String title, HttpServletRequest request, String page, Integer id_account1, Integer id_account2) {
        doHeader(out, title);
        doDivBody(out, title, request);

        Account cpt = new Account();
        Account cpte = new Account();
        cpt.setId(id_account1);
        cpte = AccountDao.research(cpt).get(0);

        Client c = new Client();
        Client cli = new Client();
        c.setId(AccountDao.researchOwnerId(cpte.getId()));
        cli = ClientDao.research(c).get(0);      
        
        
        out.println("<ul class=\"nav nav-tabs\">");
        out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
        out.println("<li role=\"presentation\"><a href=\"index\">Clients</a></li>");
        out.println("<li role=\"presentation\"><a href=\"afficherClient?idCli=" + cli.getId() + "\">" + cli.getLastName() + "_" + cli.getFirstName() + "</a></li>");
        out.println("<li role=\"presentation\" class=\"active\"><a href=\"transfereCompteACompte?id=" + id_account1 + "&id1=-1&idCli=" + cli.getId() + "\">" + cpte.getName() + "</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<div class=\"divContent\">");

    }

    public static void doHeader(JspWriter out, String title, String subtitle, HttpServletRequest request, String page) throws IOException {

        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");

        out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">");
        out.println("<link href=\"theme/css/style.css\" rel=\"stylesheet\" media=\"screen\">");
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

        if (page.equalsIgnoreCase("logging")) {
            out.println("<body id=\"bodyConnexion\">");

            out.println("<div id=\"divConnexionTitle\">");
            out.println("<h1 id=\"titleConnexion\"><b>" + title + "</b></h1>");
            out.println("<h2>" + subtitle + "</h2>");
            out.println("</div>");

            out.println("<div id=\"divConnexionBody\">");

        } else {
            out.println("<body class=\"bodySite\">");
            out.println("<center>");
            out.println("<div class=\"divContainer\">");
            out.println("<div class=\"divCommonHeader\">");

            out.println("<div class=\"row\">");
            out.println("<div id=\"divPageTitleForm\" class=\"col-md-8\">");
            out.println("<h1>" + title + "</h1>");
            out.println("</div>");

            out.println("<div id=\"divPageUserForm\" class=\"col-md-4\">");
            out.println("<p><span class=\"glyphicon glyphicon-user\" title=\"Utilisateur\"></span> " + HtmlHttpUtils.getUser(request) + ",");
            out.println("<a href=\"" + request.getContextPath() + "/logout\"><span class=\"glyphicon glyphicon-log-out\" title=\"Déconnection\"></span></a>");
            out.println("</p>");
            out.println("</div>");
            out.println("</div>");
            out.println("<br/>");
            out.println("<ul class=\"nav nav-tabs\">");
            out.println("<li role=\"presentation\"><a href=\"" + request.getContextPath() + "/welcomeServlet?nbFois=1\">Home</a></li>");
            out.println("<li role=\"presentation\"><a href=\"index\">Clients</a></li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("<div class=\"divContent\">");
        }
    }

    // Les footers
    public static void doFooter(PrintWriter out) {
        out.println("</div>");
        out.println("</center>");
        out.println("</div>");
        out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
        out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"theme/js/Jquery.js\"></script>");
        out.println("</body>");
        out.println("</html>");
    }

    public static void doFooter(JspWriter out, String page) throws IOException {
        if (page.equalsIgnoreCase("logging")) {
            out.println("</div>");
            out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
            out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"theme/js/Jquery.js\"></script>");
            out.println("</body>");
            out.println("</html>");
        } else {
            out.println("</div>");
            out.println("</center>");
            out.println("</div>");
            out.println("<script src=\"http://code.jquery.com/jquery-latest.js\"></script>");
            out.println("<script src=\"bootstrap/js/bootstrap.min.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"theme/js/Jquery.js\"></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
