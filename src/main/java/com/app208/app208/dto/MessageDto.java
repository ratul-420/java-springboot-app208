package com.app208.app208.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name is too long")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Subject is required")
    @Size(max = 200, message = "Subject is too long")
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(max = 2000, message = "Message is too long")
    private String content;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
