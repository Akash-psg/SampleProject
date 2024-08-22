package com.example.demo.Controllers;

import com.example.demo.Models.Milestone;
import com.example.demo.Services.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @Autowired
    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public ResponseEntity<List<Milestone>> getAllMilestones() {
        return new ResponseEntity<>(milestoneService.getAllMilestones(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Milestone> getMilestoneById(@PathVariable Long id) {
        return new ResponseEntity<>(milestoneService.getMilestoneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Milestone> createMilestone(@RequestBody Milestone milestone) {
        return new ResponseEntity<>(milestoneService.createMilestone(milestone), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(@PathVariable Long id, @RequestBody Milestone milestone) {
        milestone.setId(id);
        return new ResponseEntity<>(milestoneService.updateMilestone(milestone), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}