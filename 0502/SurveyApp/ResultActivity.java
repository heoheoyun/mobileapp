package com.example.surveyapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SurveyData surveyData = SurveyData.getInstance();
        Map<String, String> results = surveyData.getAllResponses();

        // 모든 TextView를 지역 변수로 선언하고 findViewById로 초기화합니다.
        TextView ageResultTextView = findViewById(R.id.ageResultTextView);
        TextView occupationResultTextView = findViewById(R.id.occupationResultTextView);
        TextView genderResultTextView = findViewById(R.id.genderResultTextView);
        TextView residenceResultTextView = findViewById(R.id.residenceResultTextView);
        TextView housingTypeResultTextView = findViewById(R.id.housingTypeResultTextView);
        TextView educationResultTextView = findViewById(R.id.educationResultTextView);
        TextView incomeResultTextView = findViewById(R.id.incomeResultTextView);
        TextView maritalStatusResultTextView = findViewById(R.id.maritalStatusResultTextView);
        TextView numFamilyResultTextView = findViewById(R.id.numFamilyResultTextView);
        TextView religionResultTextView = findViewById(R.id.religionResultTextView);

        ageResultTextView.setText(String.format(getString(R.string.age), results.get(getString(R.string.age_question))));
        occupationResultTextView.setText(String.format(getString(R.string.occupation), results.get(getString(R.string.occupation_question))));
        genderResultTextView.setText(String.format(getString(R.string.gender), results.get(getString(R.string.gender_question))));
        residenceResultTextView.setText(String.format(getString(R.string.residence), results.get(getString(R.string.residence_question))));
        housingTypeResultTextView.setText(String.format(getString(R.string.housing_type), results.get(getString(R.string.housing_type_question))));
        educationResultTextView.setText(String.format(getString(R.string.education), results.get(getString(R.string.education_question))));
        incomeResultTextView.setText(String.format(getString(R.string.income), results.get(getString(R.string.income_question))));
        maritalStatusResultTextView.setText(String.format(getString(R.string.marital_status), results.get(getString(R.string.marital_status_question))));
        numFamilyResultTextView.setText(String.format(getString(R.string.num_family), results.get(getString(R.string.num_family_question))));
        religionResultTextView.setText(String.format(getString(R.string.religion), results.get(getString(R.string.religion_question))));
    }
}