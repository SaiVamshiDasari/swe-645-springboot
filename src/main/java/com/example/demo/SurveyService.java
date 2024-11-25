package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    // Save a new survey (POST)
    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
    

    // Get a survey by ID (GET)
    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    // Update an existing survey (PUT)
    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        return surveyRepository.findById(id).map(existingSurvey -> {
            existingSurvey.setFirstName(updatedSurvey.getFirstName());
            existingSurvey.setLastName(updatedSurvey.getLastName());
            existingSurvey.setStreetAddress(updatedSurvey.getStreetAddress());
            existingSurvey.setCity(updatedSurvey.getCity());
            existingSurvey.setState(updatedSurvey.getState());
            existingSurvey.setZip(updatedSurvey.getZip());
            existingSurvey.setTelephoneNumber(updatedSurvey.getTelephoneNumber());
            existingSurvey.setEmail(updatedSurvey.getEmail());
            existingSurvey.setDateOfSurvey(updatedSurvey.getDateOfSurvey());
            existingSurvey.setLikedMost(updatedSurvey.getLikedMost());
            existingSurvey.setHowInterested(updatedSurvey.getHowInterested());
            existingSurvey.setRecommendationLikelihood(updatedSurvey.getRecommendationLikelihood());
            return surveyRepository.save(existingSurvey);
        }).orElse(null);
    }

    // Delete a survey (DELETE)
    public boolean deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
