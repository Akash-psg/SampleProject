package com.example.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String receiver;
    private String subject;
    private String context;

    // Default constructor
    public Message() {}

    // Parameterized constructor
//    public Message(Long id, String sender, String receiver, String subject, String context) {
//        this.id = id;
//        this.sender = sender;
//        this.receiver = receiver;
//        this.subject = subject;
//        this.context = context;
//    }
    public Message(String sender, String receiver, String subject, String context) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.context = context;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
