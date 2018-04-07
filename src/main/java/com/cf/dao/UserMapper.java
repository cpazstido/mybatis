package com.cf.dao;

import com.cf.domain.User;

import java.util.List;

public interface UserMapper {
    public User findUserById(int id);
    public List<User> findUserAll();
    public void insertUser(User user);
    public void deleteUserById(int id);
    public void updateUserPassword(User user);
}
