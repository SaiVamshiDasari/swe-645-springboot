package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService service;

    @GetMapping("/all")
    public List<Survey> getAllSurveys() {
        return service.getAllSurveys();
    }
    


    // POST a new survey
    @PostMapping
public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
    Survey createdSurvey = service.saveSurvey(survey);
    return ResponseEntity.created(URI.create("/api/surveys/" + createdSurvey.getId())).body(createdSurvey);
}


    // GET a survey by ID
    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        Survey survey = service.getSurveyById(id);
        return survey != null ? ResponseEntity.ok(survey) : ResponseEntity.notFound().build();
    }

    // PUT to update an existing survey
    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey updatedSurvey) {
        Survey survey = service.updateSurvey(id, updatedSurvey);
        return survey != null ? ResponseEntity.ok(survey) : ResponseEntity.notFound().build();
    }

    // DELETE a survey by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        boolean isDeleted = service.deleteSurvey(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
