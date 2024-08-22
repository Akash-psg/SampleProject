package com.example.demo.Services;

import com.example.demo.DAO.MessageRepository;
import com.example.demo.Models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(String sender, String receiver, String subject, String context) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSubject(subject);
        message.setContext(context);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }


}

