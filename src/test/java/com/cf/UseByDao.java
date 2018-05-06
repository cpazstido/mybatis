package com.cf;

import com.cf.dao.Impl.UserDaoImpl;
import com.cf.dao.UserDao;
import com.cf.domain.User;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UseByDao {

    @Test
    public void testFindUserById() throws Exception {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserById(7);
        System.out.println(user);
    }

    @Test
    public void testFindAllUser() throws Exception {
        UserDao userDao = new UserDaoImpl();
        List<User> findAllUsers = userDao.findAllUsers();
        for (User user2 : findAllUsers) {
            System.out.println(user2);
        }
    }

    @Test
    public void testInsertUser() throws Exception {
        UserDao userDao = new UserDaoImpl();
        User user = new User();
        user.setUsername("张三");
        user.setPassword("lalal");
        user.setAge(12);
        userDao.insertUser(user);
    }

    @Test
    public void testDeleteUserById() throws Exception {
        UserDao userDao = new UserDaoImpl();
        userDao.deleteUserById(3);
    }

    @Test
    public void testUpdateUserPassword() throws Exception {
        UserDao userDao = new UserDaoImpl();
        User user = new User();
        user.setId(9);
        user.setPassword("newpassword");
        userDao.updateUserPassword(user);
    }



}
