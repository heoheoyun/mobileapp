package com.example.surveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FirstQuestionActivity extends AppCompatActivity {

    private SurveyData surveyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);

        surveyData = SurveyData.getInstance();

        TextView question1TextView = findViewById(R.id.question1TextView);
        EditText answer1EditText = findViewById(R.id.answer1EditText);
        TextView question2TextView = findViewById(R.id.question2TextView);
        EditText answer2EditText = findViewById(R.id.answer2EditText);
        TextView question3TextView = findViewById(R.id.question3TextView);
        RadioGroup answer3RadioGroup = findViewById(R.id.answer3RadioGroup);
        TextView question4TextView = findViewById(R.id.question4TextView);
        EditText answer4EditText = findViewById(R.id.answer4EditText);
        TextView question5TextView = findViewById(R.id.question5TextView);
        Spinner answer5Spinner = findViewById(R.id.answer5Spinner);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            String age = answer1EditText.getText().toString();
            String occupation = answer2EditText.getText().toString();
            String gender = "";
            int selectedGenderId = answer3RadioGroup.getCheckedRadioButtonId();
            if (selectedGenderId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedGenderId);
                gender = selectedRadioButton.getText().toString();
            }
            String residence = answer4EditText.getText().toString();
            String housingType = answer5Spinner.getSelectedItem().toString();

            surveyData.addResponse(question1TextView.getText().toString(), age);
            surveyData.addResponse(question2TextView.getText().toString(), occupation);
            surveyData.addResponse(question3TextView.getText().toString(), gender);
            surveyData.addResponse(question4TextView.getText().toString(), residence);
            surveyData.addResponse(question5TextView.getText().toString(), housingType);

            Intent intent = new Intent(FirstQuestionActivity.this, SecondQuestionActivity.class);
            startActivity(intent);
        });
    }
}