/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author silvio.gutierre
 */
public class Utilisateur {
    
    private Integer identifiant;
    private String username;
    private String pwd;
    
    public Utilisateur() {
    }

    public Utilisateur(Integer identifiant, String username, String pwd) {
        this.identifiant = identifiant;
        this.username = username;
        this.pwd = pwd;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }   
    
}
