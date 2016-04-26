/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dbfactory.OracleConnections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modele.User;

/**
 *
 * @author silvio.gutierre
 */
public class UserDao {

    private UserDao() {
    }

    /**
     *
     * @return
     */
    public static ArrayList<User> researchAll() {
        ArrayList<User> users = new ArrayList<User>();

        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, username, pwd from employe";
            prepStatement = con.prepareStatement(query);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("numero");
                String username = resultSet.getString("username");
                String pwd = resultSet.getString("pwd");
                User user = new User(id, username, pwd);
                users.add(user);
            }
            return users;
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
     *
     * @param username
     * @return
     */
    public static ArrayList<User> researchByUsername(String username) {
        ArrayList<User> users = new ArrayList<User>();

        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, username, pwd from employe where username = ?";
            prepStatement = con.prepareStatement(query);
            prepStatement.setString(1, username);
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("numero");
                String pwd = resultSet.getString("pwd");
                String user_name = resultSet.getString("username");
                User user = new User(id, user_name, pwd);
                users.add(user);
            }
            resultSet.close();
            prepStatement.close();
            con.close();
            return users;

        } catch (SQLException ex) {
            System.out.println("Error SELECT CONNECTION: " + ex.getMessage());
            return null;
        }
    }
}
