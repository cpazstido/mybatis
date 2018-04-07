package com.cf;

import com.cf.dao.Impl.UserDaoImpl;
import com.cf.dao.UserDao;
import com.cf.domain.User;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserById(7);
        System.out.println(user);
    }
}
