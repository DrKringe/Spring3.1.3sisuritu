package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Users;


import java.util.List;

public interface DAO {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByUserName(String name);
    void saveUser(Users user);
    Users update(Users user);
    void delete(Long id);
}
