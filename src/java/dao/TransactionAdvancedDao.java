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
import modele.User;

/**
 *
 * @author Silvio
 */
public class TransactionAdvancedDao {

    public static ArrayList<TransactionAdvanced> researchAll() {
        ArrayList<TransactionAdvanced> listTransfer = new ArrayList<TransactionAdvanced>();

        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder query = new StringBuilder("select tr.numero as numero, cl1.nom||' '||cl1.prenom as clientd, co1.nom as compted, cl2.nom||' '||cl2.prenom as clientc, ");
            query.append("co2.nom as comptec, tr.montant as montant, tr.date_trans as date_trans");
            query.append("from transfert tr");
            query.append("inner join compte co1");
            query.append("on tr.num_compte_deb = co1.numero");
            query.append("inner join compte co2");
            query.append("on tr.num_compte_cred = co2.numero");
            query.append("inner join client cl1");
            query.append("on cl1.numero = co1.numero_client");
            query.append("inner join client cl2");
            query.append("on cl2.numero = co2.numero_client");
            query.append("order by tr.date_trans desc, tr.montant");

            prepStatement = con.prepareStatement(query.toString());
            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("numero");
                String client_debit = resultSet.getString("clientd");
                String account_debit = resultSet.getString("compted");
                String client_credit = resultSet.getString("clientc");
                String account_credit = resultSet.getString("comptec");
                float amount = resultSet.getFloat("montant");

                Date transfer_date = resultSet.getDate("date_trans");

                TransactionAdvanced transfer = new TransactionAdvanced(id, client_debit, account_debit, client_credit, account_credit, amount, transfer_date);

                listTransfer.add(transfer);
            }

            return listTransfer;
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

    public static ArrayList<TransactionAdvanced> researchByUser(User user) {

        ArrayList<TransactionAdvanced> listTransfer = new ArrayList<TransactionAdvanced>();

        Connection con = null;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null;

        try {
            con = OracleConnections.getConnection();

            StringBuilder query = new StringBuilder("select tr.numero as numero, cl1.nom||' '||cl1.prenom as clientd, co1.nom as compted, cl2.nom||' '||cl2.prenom as clientc, ");
            query.append("co2.nom as comptec, tr.montant, tr.date_trans ");
            query.append("from transfert tr ");
            query.append("inner join compte co1 ");
            query.append("on tr.num_compte_deb = co1.numero ");
            query.append("inner join compte co2 ");
            query.append("on tr.num_compte_cred = co2.numero ");
            query.append("inner join client cl1 ");
            query.append("on cl1.numero = co1.numero_client ");
            query.append("inner join client cl2 ");
            query.append("on cl2.numero = co2.numero_client ");
            query.append("where num_employe = ?");
            query.append("order by tr.date_trans desc, tr.montant");

            prepStatement = con.prepareStatement(query.toString());

            prepStatement.setInt(1, user.getId());

            resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("numero");
                String client_debit = resultSet.getString("clientd");
                String account_debit = resultSet.getString("compted");
                String client_credit = resultSet.getString("clientc");

                String account_credit = resultSet.getString("comptec");
                float amount = resultSet.getFloat("montant");
                Date transfer_date = resultSet.getDate("date_trans");

                TransactionAdvanced transfer = new TransactionAdvanced(id, client_debit, account_debit, client_credit, account_credit, amount, transfer_date);

                listTransfer.add(transfer);
            }

            return listTransfer;
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
