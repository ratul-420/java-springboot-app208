package com.app208.app208.controller;

import com.app208.app208.dto.MessageDto;
import com.app208.app208.model.Message;
import com.app208.app208.repository.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages/manage")
public class MessageCrudController {

    private final MessageRepository messageRepository;

    public MessageCrudController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "messages-manage";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("messageDto", new MessageDto());
        return "messages-form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("messageDto") MessageDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "messages-form";
        }
        Message m = new Message();
        m.setName(dto.getName());
        m.setEmail(dto.getEmail());
        m.setSubject(dto.getSubject());
        m.setContent(dto.getContent());
        messageRepository.save(m);
        return "redirect:/messages/manage";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        if (id == null) {
            return "redirect:/messages/manage";
        }
        Message m = messageRepository.findById(id).orElse(null);
        if (m == null) {
            return "redirect:/messages/manage";
        }
        MessageDto dto = new MessageDto();
        dto.setName(m.getName());
        dto.setEmail(m.getEmail());
        dto.setSubject(m.getSubject());
        dto.setContent(m.getContent());
        model.addAttribute("messageDto", dto);
        model.addAttribute("messageId", id);
        return "messages-form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("messageDto") MessageDto dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messageId", id);
            return "messages-form";
        }
        if (id == null) {
            return "redirect:/messages/manage";
        }
        Message m = messageRepository.findById(id).orElse(null);
        if (m != null) {
            m.setName(dto.getName());
            m.setEmail(dto.getEmail());
            m.setSubject(dto.getSubject());
            m.setContent(dto.getContent());
            messageRepository.save(m);
        }
        return "redirect:/messages/manage";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (id != null) {
            messageRepository.deleteById(id);
        }
        return "redirect:/messages/manage";
    }
}
