package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByUserName(String name);
    void saveUser(Users user);
    Users update(Users user);
    void delete(Long id);
}
