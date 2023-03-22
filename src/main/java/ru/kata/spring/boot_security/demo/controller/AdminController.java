package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final RoleService roleService;
    private final UsersService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UsersService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "/editUser";
    }

    @PatchMapping(value = "/{id}")
    public String updateUser(@ModelAttribute("user") Users user, @RequestParam(value = "nameRole") String nameRole) {

        Role role = new Role(nameRole);
        roleService.save(role);
        user.setRoles(Set.of(role));
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") Users user, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") Users user) {
        userService.saveUser(user);
        return  "redirect:/admin";
    }


}
