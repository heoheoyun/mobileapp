package com.example.surveyapp;

import java.util.HashMap;
import java.util.Map;

public class SurveyData {
    private static SurveyData instance;
    private final Map<String, String> responses; // final 키워드 추가

    private SurveyData() {
        responses = new HashMap<>();
    }

    public static synchronized SurveyData getInstance() {
        if (instance == null) {
            instance = new SurveyData();
        }
        return instance;
    }

    public void addResponse(String question, String answer) {
        responses.put(question, answer);
    }

    public Map<String, String> getAllResponses() {
        return responses;
    }
}