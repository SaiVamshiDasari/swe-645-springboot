package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/survey")
@CrossOrigin(origins = "*")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    // Create or update survey (POST)
    @PostMapping
    public Survey createOrUpdateSurvey(@RequestBody Survey survey) {
        return surveyRepository.save(survey);
    }

    // Update survey (PUT)
    @PutMapping("/{id}")
    public Survey updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        // Ensure the survey ID exists before updating
        if (!surveyRepository.existsById(id)) {
            throw new RuntimeException("Survey with ID " + id + " not found.");
        }
        survey.setId(id); // Set the ID to ensure the correct record is updated
        return surveyRepository.save(survey);
    }

    // Get all surveys
    @GetMapping("/all")
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Get a survey by ID
    @GetMapping("/{id}")
    public Survey getSurveyById(@PathVariable Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    // Delete a survey
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        surveyRepository.deleteById(id);
    }
}
