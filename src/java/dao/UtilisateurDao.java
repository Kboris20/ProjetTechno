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
import modele.Utilisateur;

/**
 *
 * @author silvio.gutierre
 */
public class UtilisateurDao {
       
    public static ArrayList<Utilisateur> researchAll(){
        ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
        
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            cnx = OracleConnections.getConnection();

            String query = "select numero, username, pwd from employe";

            rs = pstmt.executeQuery(query);

            while (rs.next()) {
                Integer numero = rs.getInt("numero");
                String username = rs.getString("username");
                String pwd = rs.getString("pwd");
                Utilisateur user = new Utilisateur(numero,username,pwd);
                users.add(user);
            }
            return users;
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
    
    public static ArrayList<Utilisateur> researchByUsername(String username){
        ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
        
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            cnx = OracleConnections.getConnection();

            String query = "select numero, username, pwd from employe where username = ?";
            pstmt.setString(1, username);            
            rs = pstmt.executeQuery(query);

            while (rs.next()) {
                Integer numero = rs.getInt("numero");
                String pwd = rs.getString("pwd");
                String user_name = rs.getString("username");
                Utilisateur user = new Utilisateur(numero,user_name,pwd);
                users.add(user);
            }
            return users;
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
}
