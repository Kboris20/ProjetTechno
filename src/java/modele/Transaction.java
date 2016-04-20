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
    private Account account_debit;
    private Account account_credit;
    private float amount;
    private Date date;

    public Transaction() {
    }
    
    public Transaction(Integer id, Account account_debit, Account account_credit, float amount, Date date) {
        this.id = id;
        this.account_debit = account_debit;
        this.account_credit = account_credit;
        this.amount = amount;
        this.date = date;
    }
    
     public Transaction(Account account_debit, Account account_credit, float amount) {
        this.account_debit = account_debit;
        this.account_credit = account_credit;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount_debit() {
        return account_debit;
    }

    public void setAccount_debit(Account account_debit) {
        this.account_debit = account_debit;
    }

    public Account getAccount_credit() {
        return account_credit;
    }

    public void setAccount_credit(Account account_credit) {
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
