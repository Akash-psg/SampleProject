package com.example.demo.Mockito;

import com.example.demo.DAO.MessageRepository;
import com.example.demo.Models.Message;
import com.example.demo.Services.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void createMessage() {
        // Given
        String sender = "sender";
        String receiver = "receiver";
        String subject = "subject";
        String content = "content";
        Message message = new Message(sender, receiver, subject, content);
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        // When
        Message result = messageService.createMessage(sender, receiver, subject, content);

        // Then
        assertEquals(message, result);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void getAllMessages() {
        // Given
        List<Message> messages = Arrays.asList(new Message(), new Message());
        when(messageRepository.findAll()).thenReturn(messages);

        // When
        List<Message> result = messageService.getAllMessages();

        // Then
        assertEquals(messages, result);
        verify(messageRepository).findAll();
    }

    @Test
    void getMessageById() {
        // Given
        Long messageId = 1L;
        Message message = new Message();
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // When
        Message result = messageService.getMessageById(messageId);

        // Then
        assertEquals(message, result);
        verify(messageRepository).findById(messageId);
    }

}
