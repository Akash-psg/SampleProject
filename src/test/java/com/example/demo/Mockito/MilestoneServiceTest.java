package com.example.demo.Mockito;

import com.example.demo.DAO.MilestoneRepository;
import com.example.demo.Models.Milestone;
import com.example.demo.Services.MilestoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MilestoneServiceTest {

    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private MilestoneService milestoneService;

    @Test
    void getAllMilestones() {
        // Given
        List<Milestone> milestones = Arrays.asList(new Milestone(), new Milestone());
        when(milestoneRepository.findAll()).thenReturn(milestones);

        // When
        List<Milestone> result = milestoneService.getAllMilestones();

        // Then
        assertEquals(milestones, result);
        verify(milestoneRepository).findAll();
    }

    @Test
    void getMilestoneById() {
        // Given
        Long milestoneId = 1L;
        Milestone milestone = new Milestone();
        when(milestoneRepository.findById(milestoneId)).thenReturn(Optional.of(milestone));

        // When
        Milestone result = milestoneService.getMilestoneById(milestoneId);

        // Then
        assertEquals(milestone, result);
        verify(milestoneRepository).findById(milestoneId);
    }

    @Test
    void createMilestone() {
        // Given
        Milestone milestone = new Milestone();
        when(milestoneRepository.save(milestone)).thenReturn(milestone);

        // When
        Milestone result = milestoneService.createMilestone(milestone);

        // Then
        assertEquals(milestone, result);
        verify(milestoneRepository).save(milestone);
    }


}