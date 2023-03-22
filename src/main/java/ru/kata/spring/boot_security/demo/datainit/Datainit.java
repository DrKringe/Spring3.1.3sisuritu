package ru.kata.spring.boot_security.demo.datainit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.util.HashSet;
import java.util.Set;

@Component
public class Datainit {
    private final UsersService usersService;
    private final RoleService roleService;

    @Autowired
    public Datainit (UsersService usersService, RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent () {
        roleService.save(new Role("USER"));
        roleService.save(new Role("ADMIN"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleName("USER"));
        usersService.saveUser(new Users("user", "100", "user@mail.ru", roles));
        roles.add(roleService.getRoleName("ADMIN"));
        usersService.saveUser(new Users("adin", "100", "admin@mail.ru", roles));
    }
}
