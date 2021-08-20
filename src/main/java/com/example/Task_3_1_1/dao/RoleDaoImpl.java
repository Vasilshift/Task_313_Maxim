package com.example.Task_3_1_1.dao;

import com.example.Task_3_1_1.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        return entityManager
                .createQuery("select r from Role r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

//    @Override
//    public Role getDefaultRole() {
//        return getRoleByName("ROLE_GUEST");
//    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> allRoles() {
        return entityManager.createQuery("From Role", Role.class).getResultList();
    }
}