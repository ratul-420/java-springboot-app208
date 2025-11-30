package com.app208.app208.controller;

import com.app208.app208.model.Message;
import com.app208.app208.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {

    private final MessageRepository messageRepository;

    public ChartController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String showChart(Model model) {
        List<Message> messages = messageRepository.findAll();
        Map<LocalDate, Long> counts = new LinkedHashMap<>();
        messages.stream()
                .filter(m -> m.getCreatedAt() != null)
                .map(m -> m.getCreatedAt().toLocalDate())
                .forEach(date -> {
                    long current = counts.containsKey(date) ? counts.get(date) : 0L;
                    counts.put(date, current + 1);
                });

        model.addAttribute("labels", counts.keySet());
        model.addAttribute("data", counts.values());
        return "chart";
    }
}
