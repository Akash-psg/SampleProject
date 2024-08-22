package com.example.demo.Services;

import com.example.demo.Models.Milestone;
import com.example.demo.DAO.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {
    @Autowired

    private final MilestoneRepository milestoneRepository;

    @Autowired
    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }

    public Milestone getMilestoneById(Long id) {
        return milestoneRepository.findById(id).orElse(null);
    }


    public Milestone createMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public Milestone updateMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }
}