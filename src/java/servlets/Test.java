/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import dao.UtilisateurDao;
import modele.Utilisateur;

/**
 *
 * @author boris.klett
 */
public class Test {
    
    public static void main(String[] args){
    Utilisateur u = UtilisateurDao.researchByUsername("boris").get(0);
    
        System.out.println(u.getIdentifiant()+", "+u.getUsername()+", "+u.getPwd());
    }
    
}
