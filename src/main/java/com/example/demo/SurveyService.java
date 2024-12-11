package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        if (!surveyRepository.existsById(id)) {
            return null;
        }
        updatedSurvey.setId(id);
        return surveyRepository.save(updatedSurvey);
    }

    public boolean deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

