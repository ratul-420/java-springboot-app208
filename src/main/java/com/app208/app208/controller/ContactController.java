package com.app208.app208.controller;

import com.app208.app208.dto.MessageDto;
import com.app208.app208.model.Message;
import com.app208.app208.repository.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final MessageRepository messageRepository;

    public ContactController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("messageDto", new MessageDto());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@Valid @ModelAttribute("messageDto") MessageDto dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "contact";
        }

        Message message = new Message();
        message.setName(dto.getName());
        message.setEmail(dto.getEmail());
        message.setSubject(dto.getSubject());
        message.setContent(dto.getContent());

        messageRepository.save(message);

        model.addAttribute("success", true);
        model.addAttribute("messageDto", new MessageDto());
        return "contact";
    }
}
