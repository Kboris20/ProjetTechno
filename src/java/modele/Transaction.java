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
public class Transaction {
    
    private Integer id;
    private Compte compte_debit;
    private Compte compte_credit;
    private float montant;
    private Date date;

    public Transaction() {
    }
    
    public Transaction(Integer id, Compte compte_debit, Compte compte_credit, float montant, Date date) {
        this.id = id;
        this.compte_debit = compte_debit;
        this.compte_credit = compte_credit;
        this.montant = montant;
        this.date = date;
    }
    
     public Transaction(Compte compte_debit, Compte compte_credit, float montant) {
        this.compte_debit = compte_debit;
        this.compte_credit = compte_credit;
        this.montant = montant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compte getCompte_debit() {
        return compte_debit;
    }

    public void setCompte_debit(Compte compte_debit) {
        this.compte_debit = compte_debit;
    }

    public Compte getCompte_credit() {
        return compte_credit;
    }

    public void setCompte_credit(Compte compte_credit) {
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
