/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MemoryUser;

import dao.UserDao;
import java.util.Hashtable;
import java.util.List;
import modele.User;

/**
 *
 * @author francill
 */
public class Users {

   private static final List users = UserDao.researchAll();

    public static boolean verifyUser(String p_user, String pw) {
        User user;
        if (!UserDao.researchByUsername(p_user).isEmpty()) {
            user = UserDao.researchByUsername(p_user).get(0);
            if (user.getPwd().equals(pw)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
