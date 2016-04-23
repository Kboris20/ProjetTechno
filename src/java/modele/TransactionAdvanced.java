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
    private String account_debit;
    private String client_credit;
    private String account_credit;
    private float amount;
    private Date date;

    public TransactionAdvanced() {
    }

    public TransactionAdvanced(int id, String client_debit, String account_debit, String client_credit, String account_credit, float amount, Date date) {
        this.id = id;
        this.client_debit = client_debit;
        this.account_debit = account_debit;
        this.client_credit = client_credit;
        this.account_credit = account_credit;
        this.amount = amount;
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

    public String getAccount_debit() {
        return account_debit;
    }

    public void setAccount_debit(String account_debit) {
        this.account_debit = account_debit;
    }

    public String getClient_credit() {
        return client_credit;
    }

    public void setClient_credit(String client_credit) {
        this.client_credit = client_credit;
    }

    public String getAccount_credit() {
        return account_credit;
    }

    public void setAccount_credit(String account_credit) {
        this.account_credit = account_credit;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
        
}
