package modele;

import java.io.Serializable;

public class Compte implements Serializable{
    private Integer identifiant;
    private String  nom;
    private Float   solde;
    private Float   taux;
    
    public Compte(Integer _identifiant,String _nom,Float _solde,Float _taux){
        this.identifiant = _identifiant;
        this.nom = _nom;
        this.solde = _solde;
        this.taux = _taux;
    }
    
    public Compte(){
        this.identifiant = null;
        this.nom = null;
        this.solde = null;
        this.taux = null;
    }

    @Override
    public String toString(){
        return String.valueOf(identifiant) + "," + nom + "," + String.valueOf(solde) + "," + String.valueOf(taux);
    }
    
    public void print(){
        System.out.println(this.toString());
    }
    
    public boolean isNull(){
        return nom==null && solde==null && taux==null && identifiant==null;
    }
    
    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getSolde() {
        return solde;
    }

    public void setSolde(Float solde) {
        this.solde = solde;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float taux) {
        this.taux = taux;
    }
}
