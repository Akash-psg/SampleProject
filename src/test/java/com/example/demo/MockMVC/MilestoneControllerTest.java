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
public class MilestoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllMilestones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/milestones"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMilestoneById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/milestones/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createMilestone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Milestone\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


}
