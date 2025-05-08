package com.example.surveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

public class SecondQuestionActivity extends AppCompatActivity {

    private SurveyData surveyData;
    private EditText answer6EditText, answer7EditText, answer9EditText;
    private RadioGroup answer8RadioGroup;
    private CheckBox answer10CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);

        surveyData = SurveyData.getInstance();

        answer6EditText = findViewById(R.id.answer6EditText);
        answer7EditText = findViewById(R.id.answer7EditText);
        answer8RadioGroup = findViewById(R.id.answer8RadioGroup);
        answer9EditText = findViewById(R.id.answer9EditText);
        answer10CheckBox = findViewById(R.id.yesReligionCheckBox);
        Button previousButton = findViewById(R.id.previousButton);
        Button submitButton = findViewById(R.id.submitButton);

        previousButton.setOnClickListener(v -> {
            String education = answer6EditText.getText().toString();
            String income = answer7EditText.getText().toString();
            String maritalStatus = "";
            int selectedMaritalStatusId = answer8RadioGroup.getCheckedRadioButtonId();
            if (selectedMaritalStatusId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedMaritalStatusId);
                maritalStatus = selectedRadioButton.getText().toString();
            }
            String numFamily = answer9EditText.getText().toString();
            String religion = answer10CheckBox.isChecked() ? getString(R.string.yes) : getString(R.string.no);

            surveyData.addResponse(getString(R.string.education_question), education);
            surveyData.addResponse(getString(R.string.income_question), income);
            surveyData.addResponse(getString(R.string.marital_status_question), maritalStatus);
            surveyData.addResponse(getString(R.string.num_family_question), numFamily);
            surveyData.addResponse(getString(R.string.religion_question), religion);

            finish(); // 이전 액티비티로 돌아감 (FirstQuestionActivity)
        });

        submitButton.setOnClickListener(v -> {
            String education = answer6EditText.getText().toString();
            String income = answer7EditText.getText().toString();
            String maritalStatus = "";
            int selectedMaritalStatusId = answer8RadioGroup.getCheckedRadioButtonId();
            if (selectedMaritalStatusId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedMaritalStatusId);
                maritalStatus = selectedRadioButton.getText().toString();
            }
            String numFamily = answer9EditText.getText().toString();
            String religion = answer10CheckBox.isChecked() ? getString(R.string.yes) : getString(R.string.no);

            surveyData.addResponse(getString(R.string.education_question), education);
            surveyData.addResponse(getString(R.string.income_question), income);
            surveyData.addResponse(getString(R.string.marital_status_question), maritalStatus);
            surveyData.addResponse(getString(R.string.num_family_question), numFamily);
            surveyData.addResponse(getString(R.string.religion_question), religion);

            Intent intent = new Intent(SecondQuestionActivity.this, ResultActivity.class);
            startActivity(intent);
        });
    }
}