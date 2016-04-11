/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dbfactory.OracleConnections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modele.Compte;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

/**
 * @author christop.francill
 */
public class CompteDao {

    public static long create(Compte cpt, int client_numero) {
        Connection c = null;
        OraclePreparedStatement pstmt = null;
        ResultSet rs = null;

        long rId = -1;

        try {
            c = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("insert into compte(nom,solde,taux,numero_client) values (?,?,?,?) returning numero into ?");
            pstmt = (OraclePreparedStatement) c.prepareStatement(sql.toString());

            pstmt.setString(1, cpt.getNom());
            pstmt.setFloat(2, cpt.getSolde());
            pstmt.setFloat(3, cpt.getTaux());
            pstmt.setInt(4, client_numero);
            pstmt.registerReturnParameter(5, OracleTypes.NUMBER);
            pstmt.executeUpdate();
            c.commit();

            rs = pstmt.getReturnResultSet();
            while (rs.next()) {
                rId = rs.getLong(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error INSERT: " + ex.getMessage());
        } finally {
            try {
                pstmt.close();
                c.close();
            } catch (SQLException ex) {
                System.out.println("Error INSERT CLOSE: " + ex.getMessage());
            }
        }

        return rId;
    }

    public static ArrayList<Compte> research(int client_numero) {
        ArrayList<Compte> listCpt = new ArrayList<Compte>();

        Connection cnx = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String sql = "select numero, nom, solde, taux from compte where numero_client=" + String.valueOf(client_numero)+" order by nom";

            stmt = cnx.createStatement();

            rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                Compte c = new Compte();
                c.setIdentifiant(rs.getInt("numero"));
                c.setNom(rs.getString("nom"));
                c.setSolde(rs.getFloat("solde"));
                c.setTaux(rs.getFloat("taux"));
                listCpt.add(c);
            }
            return listCpt;
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                rs.close();
                stmt.close();
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static Compte researchById(int cpt_id) {
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select numero, nom, solde, taux from compte where numero = ?";
            pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1,cpt_id);
            rs = pstmt.executeQuery();

            int numero = rs.getInt("numero");
            String nom = rs.getString("nom");
            float solde = rs.getFloat("solde");
            float taux = rs.getFloat("taux");
            
            Compte compte = new Compte(numero,nom,solde,taux);            
            
            return compte;
            
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                rs.close();
                pstmt.close();
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static ArrayList<Compte> research(Compte cpt) {
        ArrayList<Compte> listCpt = new ArrayList<Compte>();

        boolean and = false;

        Connection cnx = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            //String sql = "select numero, nom, solde, taux from compte where numero_client=" + String.valueOf(client_numero);
            StringBuilder sql = new StringBuilder("select numero, nom, solde, taux from compte");

            if (!cpt.isNull()) {
                sql.append(" where ");
                if (cpt.getIdentifiant() != -1) {
                    sql.append("numero = '");
                    sql.append(cpt.getIdentifiant());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getNom() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("nom = '");
                    sql.append(cpt.getNom());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getSolde() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("solde = '");
                    sql.append(cpt.getSolde());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getTaux() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("taux = '");
                    sql.append(cpt.getTaux());
                    sql.append("'");
                }
            }

            stmt = cnx.createStatement();

            rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                Compte c = new Compte();
                c.setIdentifiant(rs.getInt("numero"));
                c.setNom(rs.getString("nom"));
                c.setSolde(rs.getFloat("solde"));
                c.setTaux(rs.getFloat("taux"));
                listCpt.add(c);
            }
            return listCpt;
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                rs.close();
                stmt.close();
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static void update(Compte cpt) {
        Connection cnx = null;
        PreparedStatement pstmt = null;

        try {
            cnx = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("UPDATE compte SET NOM = ?, SOLDE = ?, TAUX = ? WHERE numero = ?");
            pstmt = (OraclePreparedStatement) cnx.prepareStatement(sql.toString());

            pstmt.setString(1, cpt.getNom());
            pstmt.setFloat(2, cpt.getSolde());
            pstmt.setFloat(3, cpt.getTaux());
            pstmt.setLong(4, cpt.getIdentifiant());
            pstmt.executeUpdate();
            cnx.commit();
        } catch (SQLException ex) {
            System.out.println("Error UPDATE SET: " + ex.getMessage());
        } finally {
            try {
                pstmt.close();
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error UPDATE CLOSE: " + ex.getMessage());
            }
        }
    }

    public static void delete(Compte cpt) {
        Connection cnx = null;
        PreparedStatement pstmt = null;

        try {
            cnx = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("DELETE FROM compte WHERE numero = ?");
            pstmt = (OraclePreparedStatement) cnx.prepareStatement(sql.toString());

            pstmt.setLong(1, cpt.getIdentifiant());
            pstmt.executeUpdate();
            cnx.commit();
        } catch (SQLException ex) {
            System.out.println("Error DELETE SQL: " + ex.getMessage());
        } finally {
            try {
                pstmt.close();
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Error DELETE CLOSE: " + ex.getMessage());
            }
        }
    }

    public static String researchOwner(int cptId) {
        Connection cnx = null;
        Statement stmt = null;
        ResultSet rs = null;

        String result = "";
        StringBuilder sql = new StringBuilder();
        System.out.println(cptId);
        try {
            cnx = OracleConnections.getConnection();
            sql = new StringBuilder("select c.nom, c.prenom from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + cptId);
            System.out.println("SQL Query: " + sql.toString());
            stmt = cnx.createStatement();

            rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                result = rs.getString("nom") + " " + rs.getString("prenom");
            }
        } catch (Exception ex) {
            System.out.println("Error SELECT SQL owner: " + ex.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                cnx.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL owner 2: " + ex.getMessage());
            }
            return null;
        }

    }

    public static int researchOwnerId(int cptId) {
        Connection cnx = null;
        Statement stmt = null;
        ResultSet rs = null;

        int result = -1;
        StringBuilder sql = new StringBuilder();
        System.out.println(cptId);
        try {
            cnx = OracleConnections.getConnection();
            sql = new StringBuilder("select c.numero as id from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + cptId);
            System.out.println("SQL Query: " + sql.toString());
            stmt = cnx.createStatement();

            rs = stmt.executeQuery(sql.toString());

            while (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (Exception ex) {
            System.out.println("Error SELECT SQL owner: " + ex.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
                cnx.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL owner 2: " + ex.getMessage());
            }
            return -1;
        }

    }
}
