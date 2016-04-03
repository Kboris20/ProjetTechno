/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import dao.ClientDao;
import dao.CompteDao;
import modele.Client;
import modele.Compte;

/**
 *
 * @author boris.klett
 */
public class Test {
    
    public static void main(String[] args){
    Compte c = new Compte();
    c.setNom("Tst");
    c.setSolde(new Float(3000.5));
    c.setTaux(new Float(0.5));
    
        System.out.println(CompteDao.create(c, 1));
    }
    
}
