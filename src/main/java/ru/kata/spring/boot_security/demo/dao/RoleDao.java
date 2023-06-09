package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleName(String roleName);
    void save(Role role);
    List<Role> getRoles();
}
