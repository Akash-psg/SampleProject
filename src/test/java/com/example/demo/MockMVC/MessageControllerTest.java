package com.example.demo.MockMVC;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createMessage() throws Exception {
        String messageJson = "{\"content\": \"Hello, World!\", \"taskId\": 1, \"userId\": 1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(messageJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllMessages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMessageById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}