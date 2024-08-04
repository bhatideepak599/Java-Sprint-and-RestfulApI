package com.techlabs.app.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.service.EmailService;

@RestController

public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("email/send")
    public String sendEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        String subject = request.get("subject");
        String body = request.get("body");

        emailService.sendEmail(to, subject, body);

        return "Email sent successfully!";
    }
}
