/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MemoryUser;

import dao.UtilisateurDao;
import java.util.Hashtable;
import java.util.List;
import modele.Utilisateur;

/**
 *
 * @author francill
 */
public class Utilisateurs {

   private static final List users = UtilisateurDao.researchAll();

    public static boolean verifyUser(String user, String pw) {
        Utilisateur util;
        if (!UtilisateurDao.researchByUsername(user).isEmpty()) {
            util = UtilisateurDao.researchByUsername(user).get(0);
            if (util.getPwd().equals(pw)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
