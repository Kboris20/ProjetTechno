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

    private AccountDao() {
    }

    /**
     * Allow to create a new account for a client
     *
     * @param p_account The new account
     * @param client_id The owner id
     * @return The account which has been created
     * @throws SQLException
     */
    public static long create(Account p_account, int client_id) throws SQLException {
        Connection con = null;
        OraclePreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        long rId = -1;

        try {
            con = OracleConnections.getConnection();

            String sql = "insert into compte(nom,solde,taux,numero_client) values (?,?,?,?) returning numero into ?";
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql);

            prepStatement.setString(1, p_account.getName());
            prepStatement.setFloat(2, p_account.getBalance());
            prepStatement.setFloat(3, p_account.getRate());
            prepStatement.setInt(4, client_id);
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

    /**
     * Search the accounts of a client in the database
     *
     * @param client_id the id of the client for who we want to get the accounts
     * @return the searched accoutn
     */
    public static ArrayList<Account> research(int client_id) {
        ArrayList<Account> listAccount = new ArrayList<Account>();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String sql = "select numero, nom, solde, taux from compte where numero_client=" + String.valueOf(client_id) + " order by nom";

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("numero"));
                account.setName(resultSet.getString("nom"));
                account.setBalance(resultSet.getFloat("solde"));
                account.setRate(resultSet.getFloat("taux"));
                listAccount.add(account);
            }
            return listAccount;
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

    /**
     * Get the id of a client
     *
     * @param account_id the account id for which we want to get owner id
     * @return the id of a client
     */
    public static Account researchById(int account_id) {
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, nom, solde, taux from compte where numero = ?";
            prepStatement = con.prepareStatement(query);

            prepStatement.setInt(1, account_id);
            resultSet = prepStatement.executeQuery();

            int id = resultSet.getInt("numero");
            String name = resultSet.getString("nom");
            float balance = resultSet.getFloat("solde");
            float rate = resultSet.getFloat("taux");

            Account account = new Account(id, name, balance, rate);

            return account;

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

    /**
     * Used to get a searched account if it exist in the data base
     *
     * @param p_account the account that is looked for
     * @return the searched account
     */
    public static ArrayList<Account> research(Account p_account) {
        ArrayList<Account> listAccount = new ArrayList<Account>();

        boolean and = false;

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("select numero, nom, solde, taux from compte");

            if (!p_account.isNull()) {
                sql.append(" where ");
                if (p_account.getId() != -1) {
                    sql.append("numero = '");
                    sql.append(p_account.getId());
                    sql.append("'");
                    and = true;
                }
                if (p_account.getName() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("nom = '");
                    sql.append(p_account.getName());
                    sql.append("'");
                    and = true;
                }
                if (p_account.getBalance() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("solde = '");
                    sql.append(p_account.getBalance());
                    sql.append("'");
                    and = true;
                }
                if (p_account.getRate() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("taux = '");
                    sql.append(p_account.getRate());
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
                listAccount.add(account);
            }
            return listAccount;
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

    /**
     *
     * @param p_account the account that we want to update in the DB
     */
    public static void update(Account p_account) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("UPDATE compte SET NOM = ?, SOLDE = ?, TAUX = ? WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setString(1, p_account.getName());
            prepStatement.setFloat(2, p_account.getBalance());
            prepStatement.setFloat(3, p_account.getRate());
            prepStatement.setLong(4, p_account.getId());
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

    /**
     *
     * @param p_account
     */
    public static void delete(Account p_account) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("DELETE FROM compte WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setLong(1, p_account.getId());
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

    public static String researchOwner(int account_id) {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String result = "";
        StringBuilder sql = new StringBuilder();
        System.out.println(account_id);
        try {
            con = OracleConnections.getConnection();
            sql = new StringBuilder("select c.nom, c.prenom from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + account_id);
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

    /**
     *
     * @param account_id
     * @return
     */
    public static int researchOwnerId(int account_id) {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        int result = -1;
        StringBuilder sql = new StringBuilder();
        System.out.println(account_id);
        try {
            con = OracleConnections.getConnection();
            sql = new StringBuilder("select c.numero as id from client c inner join compte cpt on cpt.numero_client=c.numero where cpt.numero=" + account_id);
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
