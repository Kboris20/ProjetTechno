/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;

/**
 *
 * @author silvio.gutierre
 */
public class TransactionAdvanced {
    
    private int id;
    private String client_debit;
    private String compte_debit;
    private String client_credit;
    private String compte_credit;
    private float montant;
    private Date date;

    public TransactionAdvanced() {
    }

    public TransactionAdvanced(int id, String client_debit, String compte_debit, String client_credit, String compte_credit, float montant, Date date) {
        this.id = id;
        this.client_debit = client_debit;
        this.compte_debit = compte_debit;
        this.client_credit = client_credit;
        this.compte_credit = compte_credit;
        this.montant = montant;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_debit() {
        return client_debit;
    }

    public void setClient_debit(String client_debit) {
        this.client_debit = client_debit;
    }

    public String getCompte_debit() {
        return compte_debit;
    }

    public void setCompte_debit(String compte_debit) {
        this.compte_debit = compte_debit;
    }

    public String getClient_credit() {
        return client_credit;
    }

    public void setClient_credit(String client_credit) {
        this.client_credit = client_credit;
    }

    public String getCompte_credit() {
        return compte_credit;
    }

    public void setCompte_credit(String compte_credit) {
        this.compte_credit = compte_credit;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        
}
