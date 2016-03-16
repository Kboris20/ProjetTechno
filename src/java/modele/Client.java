package modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable{
    private Integer identifiant;
    private String  nom;
    private String  prenom;
    private String  adresse;
    private String  ville;
    private ArrayList<Compte> listeCompte;
    
    public Client(Integer _identifiant,String _nom,String _prenom,String _adresse,String _ville,ArrayList<Compte> _listeCompte){
        this.identifiant = _identifiant;
        this.nom = _nom;
        this.prenom = _prenom;
        this.adresse = _adresse;
        this.ville = _ville;
        
        this.listeCompte = new ArrayList<Compte>();
        listeCompte.addAll(_listeCompte);
    }
    
    public Client(Integer _identifiant,String _nom,String _prenom,String _adresse,String _ville){
        this.identifiant = _identifiant;
        this.nom = _nom;
        this.prenom = _prenom;
        this.adresse = _adresse;
        this.ville = _ville;
    }
    
    public Client(){
        this.identifiant = null;
        this.nom = null;
        this.prenom = null;
        this.adresse = null;
        this.ville = null;
        this.listeCompte = new ArrayList<Compte>();
    }
    
    @Override
    public String toString(){
        return String.valueOf(identifiant) + "," + nom + "," + prenom + "," + adresse + "," + ville;
    }
    
    public void print(){
        System.out.println(this.toString());
    }
    
    public boolean isNull(){
        return nom==null && prenom==null && adresse==null && ville==null && identifiant==null && listeCompte.size()==0;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public ArrayList<Compte> getListeCompte() {
        return listeCompte;
    }

    public void setListeCompte(ArrayList<Compte> listeCompte) {
        this.listeCompte = listeCompte;
    }
    
    public void addCompte(Compte cpt){
        this.listeCompte.add(cpt);
    }
}
