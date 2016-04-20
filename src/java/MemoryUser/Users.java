/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MemoryUser;

import dao.UtilisateurDao;
import java.util.List;
import modele.User;

/**
 *
 * @author Boris
 */
public class Users {

    public static List users = UtilisateurDao.researchAll();

    public static boolean verifyUser(String user, String pw) {
        User util = new User();
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
