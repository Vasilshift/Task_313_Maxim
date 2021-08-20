package com.example.Task_3_1_1.service;

import com.example.Task_3_1_1.model.Role;
import com.example.Task_3_1_1.model.User;
import java.util.List;


public interface RoleService {

    Role getRoleByName(String name);

    void add(Role role);

    List<Role> allRoles();

    void setupRoles(User user);
}