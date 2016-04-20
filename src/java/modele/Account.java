package modele;

import exception.InsufficientBalanceException;
import exception.NegativeAmmountException;
import java.io.Serializable;

public class Account implements Serializable{
    private Integer id;
    private String  name;
    private Float   balance;
    private Float   rate;
    
    public Account(Integer id,String name,Float balance,Float rate){
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.rate = rate;
    }
    
    public Account(){
        this.id = null;
        this.name = null;
        this.balance = null;
        this.rate = null;
    }

    @Override
    public String toString(){
        return String.valueOf(id) + "," + name + "," + String.valueOf(balance) + "," + String.valueOf(rate);
    }
    
    public void print(){
        System.out.println(this.toString());
    }
    
    public boolean isNull(){
        return name==null && balance==null && rate==null && id==null;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
    
    public void credit(float amount) throws NegativeAmmountException {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new NegativeAmmountException();
        }
    }
    
    public void debit(Float amount) throws NegativeAmmountException, InsufficientBalanceException {
        if (this.balance > amount && amount > 0) {
            this.balance -= amount;
        } else if (amount < 0) {
            throw new NegativeAmmountException();
        } else {
            throw new InsufficientBalanceException();
        }
    }

    public static void transfer(Float amount, Account source, Account target) throws NegativeAmmountException, InsufficientBalanceException {
        if (source.getBalance() > amount && amount > 0) {
            source.debit(amount);
            target.credit(amount);
        } else if (amount < 0) {
            throw new NegativeAmmountException();
        } else {
            throw new InsufficientBalanceException();
        }
    }
}
