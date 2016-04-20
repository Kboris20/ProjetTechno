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
import modele.Account;
import modele.Transaction;
import modele.User;

/**
 *
 * @author silvio.gutierre
 */
public class TransactionDao {

    public static void create(Transaction transaction, User user) {
        Connection con = null;
        PreparedStatement prepStatement = null;
        try {
            con = OracleConnections.getConnection();
            String query = "insert into transfert(num_compte_deb,num_compte_cred,montant,num_employe) values (?,?,?,?)";
            prepStatement = con.prepareStatement(query);

            prepStatement.setInt(1, transaction.getAccount_debit().getId());
            prepStatement.setInt(2, transaction.getAccount_credit().getId());
            prepStatement.setFloat(3, transaction.getAmount());
            prepStatement.setInt(4, user.getId());
            prepStatement.executeUpdate();
            con.commit();
            
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
    }

    public static ArrayList<Transaction> researchAll() {
        ArrayList<Transaction> listTr = new ArrayList<Transaction>();
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, num_compte_deb, num_compte_cred, montant, date_trans from transfert";

            prepStatement = con.prepareStatement(query);

            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("numero");
                Account account_debit = new Account();
                Account account_credit = new Account();
                                
                account_debit.setId(resultSet.getInt("num_compte_deb"));
                account_credit.setId(resultSet.getInt("num_compte_cred"));
                float amount = resultSet.getFloat("montant");
                Date date = resultSet.getDate("date_trans");
                           
                Transaction transaction = new Transaction(id,account_debit,account_credit,amount,date);
                listTr.add(transaction);                
            }
            return listTr;
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
    
    public static ArrayList<Transaction> researchByUser(User user) {
        ArrayList<Transaction> listTr = new ArrayList<Transaction>();
        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            String query = "select numero, num_compte_deb, num_compte_cred, montant, date_trans from transfert where num_employe = ?";
            prepStatement = con.prepareStatement(query);
            prepStatement.setInt(1,user.getId());
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("numero");
                Account account_debit = AccountDao.researchById(resultSet.getInt("num_compte_deb"));
                Account account_credit = AccountDao.researchById(resultSet.getInt("num_compte_cred"));
                float amount = resultSet.getFloat("montant");
                Date date = resultSet.getDate("date_trans");
                           
                Transaction transaction = new Transaction(id,account_debit,account_credit,amount,date);
                           
                listTr.add(transaction);                
            }
            return listTr;
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

}
