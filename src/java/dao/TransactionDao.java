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
import java.util.Date;
import modele.Compte;
import modele.Transaction;
import modele.Utilisateur;

/**
 *
 * @author silvio.gutierre
 */
public class TransactionDao {

    public static void create(Transaction transaction, Utilisateur user) {
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = OracleConnections.getConnection();
            String query = "insert into transfert(num_compte_deb,num_compte_cred,montant,num_employe) values (?,?,?,?)";
            pstmt = c.prepareStatement(query);

            pstmt.setInt(1, transaction.getCompte_debit().getIdentifiant());
            pstmt.setInt(2, transaction.getCompte_credit().getIdentifiant());
            pstmt.setFloat(3, transaction.getMontant());
            pstmt.setInt(4, user.getIdentifiant());
            pstmt.executeUpdate();
            c.commit();
            
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
    }

    public static ArrayList<Transaction> researchAll() {
        ArrayList<Transaction> listTr = new ArrayList<Transaction>();
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select numero, num_compte_deb, num_compte_cred, montant, date_trans from transfert";

            pstmt = cnx.prepareStatement(query);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("numero");
                Compte cmpt_deb = new Compte();
                Compte cmpt_cred = new Compte();
                                
                cmpt_deb.setIdentifiant(rs.getInt("num_compte_deb"));//CompteDao.researchById(rs.getInt("num_compte_deb"));
                cmpt_cred.setIdentifiant(rs.getInt("num_compte_cred"));//CompteDao.researchById(rs.getInt("num_compte_cred"));
                float montant = rs.getFloat("montant");
                Date date = rs.getDate("date_trans");
                           
                Transaction transaction = new Transaction(id,cmpt_deb,cmpt_cred,montant,date);
                listTr.add(transaction);                
            }
            return listTr;
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
    
        /*public static ArrayList<Transaction> researchWithAccountNameByUser(Utilisateur user) {
        ArrayList<Transaction> listTr = new ArrayList<Transaction>();
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select t.numero as numero, c1.nom as nom_compte_debit, c2.nom as nom_compte_credit, t.montant as montant, t.date_trans as date "
                    + "from transfert t where t.num_employe = ? "
                    + "inner join compte c1 "
                    + "on t.num_compte_deb = c1.numero "
                    + "inner join compte c2 "
                    + "on t.num_compte_cred = c2.numero";
            pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1,user.getIdentifiant());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                Integer id = rs.getInt("numero");
                
                Compte cmpt_deb = new Compte();
                cmpt_deb.setNom(rs.getString("nom_compte_debit"));
                Compte cmpt_cred = new Compte();
                cmpt_cred.setNom(rs.getString("nom_compte_credit"));
                
                float montant = rs.getFloat("montant");
                Date date = rs.getDate("date_trans");
                           
                Transaction transaction = new Transaction(id,cmpt_deb,cmpt_cred,montant,date);
                           
                listTr.add(transaction);                
            }
            return listTr;
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
    }*/

    public static ArrayList<Transaction> researchByUser(Utilisateur user) {
        ArrayList<Transaction> listTr = new ArrayList<Transaction>();
        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select numero, num_compte_deb, num_compte_cred, montant, date_trans from transfert where num_employe = ?";
            pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1,user.getIdentifiant());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("numero");
                Compte cmpt_deb = CompteDao.researchById(rs.getInt("num_compte_deb"));
                Compte cmpt_cred = CompteDao.researchById(rs.getInt("num_compte_cred"));
                float montant = rs.getFloat("montant");
                Date date = rs.getDate("date_trans");
                           
                Transaction transaction = new Transaction(id,cmpt_deb,cmpt_cred,montant,date);
                           
                listTr.add(transaction);                
            }
            return listTr;
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
