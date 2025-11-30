package com.app208.app208.controller;

import com.app208.app208.dto.UserDto;
import com.app208.app208.model.Role;
import com.app208.app208.model.User;
import com.app208.app208.repository.RoleRepository;
import com.app208.app208.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @GetMapping("/")
    public String home() {
        // Redirect root to the authenticated home page
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER");
        // Ensure the role exists so the join table insert doesn't fail
        if (userRole == null) {
            userRole = roleRepository.save(new Role("ROLE_USER"));
        }
        user.getRoles().add(userRole);

        userRepository.save(user);
        return "redirect:/login?registered=true";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
