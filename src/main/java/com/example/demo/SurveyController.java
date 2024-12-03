package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    // Create a new survey (POST)
    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey savedSurvey = surveyRepository.save(survey);
        return ResponseEntity.ok(savedSurvey);
    }

    // GET endpoint to retrieve all surveys
    @GetMapping("/all")
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return ResponseEntity.ok(surveys);
    }

    // Get a specific survey by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        Survey survey = surveyRepository.findById(id).orElse(null);
        if (survey == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(survey);
    }

    // Update a survey (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey updatedSurvey) {
        if (!surveyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedSurvey.setId(id);
        Survey savedSurvey = surveyRepository.save(updatedSurvey);
        return ResponseEntity.ok(savedSurvey);
    }

    // Delete a survey (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        if (!surveyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        surveyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
