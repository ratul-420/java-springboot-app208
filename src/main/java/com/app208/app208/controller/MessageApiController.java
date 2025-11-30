package com.app208.app208.controller;

import com.app208.app208.dto.MessageDto;
import com.app208.app208.model.Message;
import com.app208.app208.repository.MessageRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageApiController {

    private final MessageRepository messageRepository;

    public MessageApiController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Message> all() {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> one(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings({ "nullness", "null" })
    @PostMapping
    public ResponseEntity<Message> create(@Valid @RequestBody MessageDto dto) {
        Message m = new Message();
        m.setName(dto.getName());
        m.setEmail(dto.getEmail());
        m.setSubject(dto.getSubject());
        m.setContent(dto.getContent());
        Message saved = messageRepository.save(m);
        URI location = URI.create("/api/messages/" + saved.getId());
        // Suppress nullness warning as URI.create never returns null
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> update(@PathVariable Long id, @Valid @RequestBody MessageDto dto) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return messageRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setSubject(dto.getSubject());
                    existing.setContent(dto.getContent());
                    Message saved = messageRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!messageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
