package com.example.Task_3_1_1.service;

import com.example.Task_3_1_1.dao.RoleDao;
import com.example.Task_3_1_1.model.Role;
import com.example.Task_3_1_1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Transactional
    @Override
    public void setupRoles(User user){
        Set<Role> rolesSet = new HashSet<>();

        if (user.getRoles().contains(getRoleByName("ROLE_ADMIN"))) {
            rolesSet.add(getRoleByName("ROLE_ADMIN"));
        }
        if (user.getRoles().contains(getRoleByName("ROLE_USER"))) {
            rolesSet.add(getRoleByName("ROLE_USER"));
        }
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.add(role);
    }

    @Transactional
    @Override
    public List<Role> allRoles() {
        return roleDao.allRoles();
    }
}