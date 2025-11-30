package com.app208.app208.controller;

import com.app208.app208.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessagesController {

    private final MessageRepository messageRepository;

    public MessagesController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messages")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAllByOrderByCreatedAtDesc());
        return "messages";
    }
}
