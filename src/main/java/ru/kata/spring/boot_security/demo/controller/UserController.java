package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UsersService;


import javax.persistence.*;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    private final UsersService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UsersService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new Users());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") Users user) {
        Role role = new Role("ROLE_USER");
        roleService.save(role);
        user.setRoles(Set.of(role));
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        Long id = userService.getUserByUserName(principal.getName()).getId();
        model.addAttribute("user", userService.getUserById(id));
        return "/user";
    }

}
