package modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable{
    private Integer id;
    private String  lastName;
    private String  firstName;
    private String  addres;
    private String  city;
    private ArrayList<Account> listAccount;
    
    public Client(Integer id,String lastName,String firstName,String address,String city,ArrayList<Account> listAccount){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.addres = address;
        this.city = city;
        
        this.listAccount = new ArrayList<Account>();
        this.listAccount.addAll(listAccount);
    }
    
    public Client(Integer id,String lastName,String firstName,String address,String city){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.addres = address;
        this.city = city;
    }
    
    public Client(){
        this.id = null;
        this.lastName = null;
        this.firstName = null;
        this.addres = null;
        this.city = null;
        this.listAccount = new ArrayList<Account>();
    }
    
    @Override
    public String toString(){
        return String.valueOf(id) + "," + lastName + "," + firstName + "," + addres + "," + city;
    }
    
    public void print(){
        System.out.println(this.toString());
    }
    
    public boolean isNull(){
        return lastName==null && firstName==null && addres==null && city==null && id==null && listAccount.size()==0;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Account> getListAccount() {
        return listAccount;
    }

    public void setListAccount(ArrayList<Account> listAccount) {
        this.listAccount = listAccount;
    }
    
    public void addCompte(Account account){
        this.listAccount.add(account);
    }
}
