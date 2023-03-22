package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.DAO;
import ru.kata.spring.boot_security.demo.model.Users;


import java.util.List;
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    private final DAO userDao;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(DAO userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<Users> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Users getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Users getUserByUserName(String name) {
        return userDao.getUserByUserName(name);
    }

    @Override
    public void saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public Users update(Users user) {
        if(!getUserById(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

}
