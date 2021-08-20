package com.example.Task_3_1_1.dao;


import com.example.Task_3_1_1.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);

    Role getDefaultRole();

    void add(Role role);

    List<Role> allRoles();

}