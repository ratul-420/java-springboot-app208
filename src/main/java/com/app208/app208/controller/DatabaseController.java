package com.app208.app208.controller;

import com.app208.app208.repository.MessageRepository;
import com.app208.app208.repository.RoleRepository;
import com.app208.app208.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/database")
public class DatabaseController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MessageRepository messageRepository;

    public DatabaseController(UserRepository userRepository,
                              RoleRepository roleRepository,
                              MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String showData(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("messages", messageRepository.findAll());
        return "database";
    }
}
