package com.example.Task_3_1_1.dao;

import com.example.Task_3_1_1.model.User;

import java.util.List;

public interface UserDao {

    List<User> allUsers();

    User get(int id);

    void add(User user);

    void delete(int id);

    void update(User user);

    User getUserByUsername(String username);

}