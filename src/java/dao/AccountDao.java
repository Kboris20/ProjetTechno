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
import modele.Account;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

/**
 * @author christop.francill
 */
public class AccountDao {

    public static long create(Account cpt, int client_numero) {
        Connection con = null;
        OraclePreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        long rId = -1;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("insert into compte(nom,solde,taux,numero_client) values (?,?,?,?) returning numero into ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setString(1, cpt.getName());
            prepStatement.setFloat(2, cpt.getBalance());
            prepStatement.setFloat(3, cpt.getRate());
            prepStatement.setInt(4, client_numero);
            prepStatement.registerReturnParameter(5, OracleTypes.NUMBER);
            prepStatement.executeUpdate();
            con.commit();

            resultSet = prepStatement.getReturnResultSet();
            while (resultSet.next()) {
                rId = resultSet.getLong(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error INSERT: " + ex.getMessage());
        } finally {
            try {
                prepStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error INSERT CLOSE: " + ex.getMessage());
            }
        }

        return rId;
    }

    public static ArrayList<Account> research(int client_number) {
        ArrayList<Account> listCpt = new ArrayList<Account>();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSset = null;

        try {
            con = OracleConnections.getConnection();

            String sql = "select numero, nom, solde, taux from compte where numero_client=" + String.valueOf(client_number)+" order by nom";

            statement = con.createStatement();

            resultSset = statement.executeQuery(sql.toString());

            while (resultSset.next()) {
                Account account = new Account();
                account.setId(resultSset.getInt("numero"));
                account.setName(resultSset.getString("nom"));
                account.setBalance(resultSset.getFloat("solde"));
                account.setRate(resultSset.getFloat("taux"));
                listCpt.add(account);
            }
            return listCpt;
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                resultSset.close();
                statement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static Account researchById(int cpt_id) {
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, nom, solde, taux from compte where numero = ?";
            prepStatement = con.prepareStatement(query);
            prepStatement.setInt(1,cpt_id);
            resultSet = prepStatement.executeQuery();

            int numero = resultSet.getInt("numero");
            String nom = resultSet.getString("nom");
            float solde = resultSet.getFloat("solde");
            float taux = resultSet.getFloat("taux");
            
            Account compte = new Account(numero,nom,solde,taux);            
            
            return compte;
            
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                resultSet.close();
                prepStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static ArrayList<Account> research(Account cpt) {
        ArrayList<Account> listCpt = new ArrayList<Account>();

        boolean and = false;

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            //String sql = "select numero, nom, solde, taux from compte where numero_client=" + String.valueOf(client_numero);
            StringBuilder sql = new StringBuilder("select numero, nom, solde, taux from compte");

            if (!cpt.isNull()) {
                sql.append(" where ");
                if (cpt.getId() != -1) {
                    sql.append("numero = '");
                    sql.append(cpt.getId());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getName() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("nom = '");
                    sql.append(cpt.getName());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getBalance() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("solde = '");
                    sql.append(cpt.getBalance());
                    sql.append("'");
                    and = true;
                }
                if (cpt.getRate() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("taux = '");
                    sql.append(cpt.getRate());
                    sql.append("'");
                }
            }

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("numero"));
                account.setName(resultSet.getString("nom"));
                account.setBalance(resultSet.getFloat("solde"));
                account.setRate(resultSet.getFloat("taux"));
                listCpt.add(account);
            }
            return listCpt;
        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL: " + ex.getMessage());
            }

        }
    }

    public static void update(Account cpt) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("UPDATE compte SET NOM = ?, SOLDE = ?, TAUX = ? WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setString(1, cpt.getName());
            prepStatement.setFloat(2, cpt.getBalance());
            prepStatement.setFloat(3, cpt.getRate());
            prepStatement.setLong(4, cpt.getId());
            prepStatement.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            System.out.println("Error UPDATE SET: " + ex.getMessage());
        } finally {
            try {
                prepStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error UPDATE CLOSE: " + ex.getMessage());
            }
        }
    }

    public static void delete(Account cpt) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("DELETE FROM compte WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setLong(1, cpt.getId());
            prepStatement.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            System.out.println("Error DELETE SQL: " + ex.getMessage());
        } finally {
            try {
                prepStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error DELETE CLOSE: " + ex.getMessage());
            }
        }
    }

    public static String researchOwner(int cptId) {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String result = "";
        StringBuilder sql = new StringBuilder();
        System.out.println(cptId);
        try {
            con = OracleConnections.getConnection();
            sql = new StringBuilder("select c.nom, c.prenom from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + cptId);
            System.out.println("SQL Query: " + sql.toString());
            statement = con.createStatement();

            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                result = resultSet.getString("nom") + " " + resultSet.getString("prenom");
            }
        } catch (Exception ex) {
            System.out.println("Error SELECT SQL owner: " + ex.getMessage());
        } finally {
            try {
                resultSet.close();
                statement.close();
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL owner 2: " + ex.getMessage());
            }
            return null;
        }

    }

    public static int researchOwnerId(int cptId) {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        int result = -1;
        StringBuilder sql = new StringBuilder();
        System.out.println(cptId);
        try {
            con = OracleConnections.getConnection();
            sql = new StringBuilder("select c.numero as id from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + cptId);
            System.out.println("SQL Query: " + sql.toString());
            statement = con.createStatement();

            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                result = resultSet.getInt("id");
            }
        } catch (Exception ex) {
            System.out.println("Error SELECT SQL owner: " + ex.getMessage());
        } finally {
            try {
                resultSet.close();
                statement.close();
                con.close();
                return result;
            } catch (SQLException ex) {
                System.out.println("Error SELECT SQL owner 2: " + ex.getMessage());
            }
            return -1;
        }

    }
}
