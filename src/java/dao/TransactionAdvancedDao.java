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
import java.util.ArrayList;
import java.util.Date;
import modele.TransactionAdvanced;
import modele.Utilisateur;

/**
 *
 * @author christop.francill
 */
public class TransactionAdvancedDao {

    public static ArrayList<TransactionAdvanced> researchAll() {
        ArrayList<TransactionAdvanced> listTrans = new ArrayList<TransactionAdvanced>();

        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select tr.numero as numero, cl1.nom||' '||cl1.prenom as clientd, co1.nom as compted, cl2.nom||' '||cl2.prenom as clientc, "
                    + "co2.nom as comptec, tr.montant as montant, tr.date_trans as date_trans\n"
                    + "from transfert tr\n"
                    + "inner join compte co1\n"
                    + "on tr.num_compte_deb = co1.numero\n"
                    + "inner join compte co2\n"
                    + "on tr.num_compte_cred = co2.numero\n"
                    + "inner join client cl1\n"
                    + "on cl1.numero = co1.numero_client\n"
                    + "inner join client cl2\n"
                    + "on cl2.numero = co2.numero_client";

            pstmt = cnx.prepareStatement(query);

            rs = pstmt.executeQuery();

            while (rs.next()){                
                int id = rs.getInt("numero");
                String client_debit = rs.getString("clientd");
                String compte_debit = rs.getString("compted");
                String client_credit = rs.getString("clientc");
                String compte_credit = rs.getString("comptec");
                float montant = rs.getFloat("montant");
                Date date_trans = rs.getDate("date_trans");
                                               
                TransactionAdvanced tr = new TransactionAdvanced(id,client_debit,compte_debit,client_credit,compte_credit,montant,date_trans);

                listTrans.add(tr);
            }

            return listTrans;
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
    
    public static ArrayList<TransactionAdvanced> researchByUser(Utilisateur user) {
            
        ArrayList<TransactionAdvanced> listTrans = new ArrayList<TransactionAdvanced>();

        Connection cnx = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            cnx = OracleConnections.getConnection();

            String query = "select tr.numero as numero, cl1.nom||' '||cl1.prenom as clientd, co1.nom as compted, cl2.nom||' '||cl2.prenom as clientc, "
                    + "co2.nom as comptec, tr.montant, tr.date_trans "
                    + "from transfert tr "
                    + "inner join compte co1 "
                    + "on tr.num_compte_deb = co1.numero "
                    + "inner join compte co2 "
                    + "on tr.num_compte_cred = co2.numero "
                    + "inner join client cl1 "
                    + "on cl1.numero = co1.numero_client "
                    + "inner join client cl2 "
                    + "on cl2.numero = co2.numero_client "
                    + "where num_employe = ?";

            pstmt = cnx.prepareStatement(query);
            
            pstmt.setInt(1,user.getIdentifiant());

            rs = pstmt.executeQuery();

            while (rs.next()){                
                int id = rs.getInt("numero");
                String client_debit = rs.getString("clientd");
                String compte_debit = rs.getString("compted");
                String client_credit = rs.getString("clientc");
                String compte_credit = rs.getString("comptec");               
                float montant = rs.getFloat("montant");
                Date date_trans = rs.getDate("date_trans");
                                               
                TransactionAdvanced tr = new TransactionAdvanced(id,client_debit,compte_debit,client_credit,compte_credit,montant,date_trans);

                listTrans.add(tr);
            }

            return listTrans;
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
