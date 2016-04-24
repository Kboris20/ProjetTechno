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
import modele.Client;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Silvio
 */
public class ClientDao {

    public static long create(Client p_client) {
        Connection con = null;
        OraclePreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        long rId = -1;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("insert into client(nom,prenom,adresse,ville) values (?,?,?,?) returning numero into ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setString(1, p_client.getLastName());
            prepStatement.setString(2, p_client.getFirstName());
            prepStatement.setString(3, p_client.getAddres());
            prepStatement.setString(4, p_client.getCity());
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
        System.out.println(rId);
        return rId;
    }

    public static ArrayList<Client> researchAll() {

        ArrayList<Client> listClient = new ArrayList<Client>();
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("select numero, nom, prenom, adresse, ville from client order by nom, prenom, adresse, ville");
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("numero"));
                client.setLastName(resultSet.getString("nom"));
                client.setFirstName(resultSet.getString("prenom"));

                client.setAddres(resultSet.getString("adresse"));
                client.setCity(resultSet.getString("ville"));
                client.setListAccount(AccountDao.research(client.getId()));
                listClient.add(client);
            }
            return listClient;
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

    public static ArrayList<Client> research(Client p_client) {
        ArrayList<Client> listClient = new ArrayList<Client>();
        boolean and = false;

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("select numero, nom, prenom, adresse, ville from client");

            if (!p_client.isNull()) {
                sql.append(" where ");

                if (p_client.getId() != -1) {
                    sql.append("numero = '");
                    sql.append(p_client.getId());
                    sql.append("'");
                    and = true;
                }
                if (p_client.getLastName() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("nom = '");

                    sql.append(p_client.getLastName());
                    sql.append("'");
                    and = true;
                }
                if (p_client.getFirstName() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("prenom = '");

                    sql.append(p_client.getFirstName());
                    sql.append("'");
                    and = true;
                }
                if (p_client.getAddres() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("adresse = '");

                    sql.append(p_client.getAddres());
                    sql.append("'");
                    and = true;
                }
                if (p_client.getCity() != null) {
                    if (and) {
                        sql.append(" and ");
                    }
                    sql.append("ville = '");

                    sql.append(p_client.getCity());
                    sql.append("'");
                }
            }

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("numero"));
                client.setLastName(resultSet.getString("nom"));
                client.setFirstName(resultSet.getString("prenom"));

                client.setAddres(resultSet.getString("adresse"));
                client.setCity(resultSet.getString("ville"));
                client.setListAccount(AccountDao.research(client.getId()));
                listClient.add(client);
            }
            return listClient;
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

    public static void update(Client p_client) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("UPDATE client SET NOM = ?, PRENOM = ?, ADRESSE = ?, VILLE = ? WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setString(1, p_client.getLastName());
            prepStatement.setString(2, p_client.getFirstName());
            prepStatement.setString(3, p_client.getAddres());
            prepStatement.setString(4, p_client.getCity());
            prepStatement.setLong(5, p_client.getId());
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

    public static boolean delete(Client p_client) {
        Connection con = null;
        PreparedStatement prepStatement = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder sql = new StringBuilder("DELETE FROM client WHERE numero = ?");
            prepStatement = (OraclePreparedStatement) con.prepareStatement(sql.toString());

            prepStatement.setLong(1, p_client.getId());
            prepStatement.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error DELETE SQL: " + ex.getMessage());
            return false;
        } finally {
            try {
                prepStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error DELETE CLOSE: " + ex.getMessage());
            }
        }
    }
}
