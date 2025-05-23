package com.example.todolistappva;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 로컬 변수로 선언
        TextView textViewDetailTodo = findViewById(R.id.textViewDetailTodo);
        TextView textViewDetailRegistrationDate = findViewById(R.id.textViewDetailRegistrationDate);
        TextView textViewDetailAlarmTime = findViewById(R.id.textViewDetailAlarmTime);

        String todoText = getIntent().getStringExtra("todoText");
        long registrationDate = getIntent().getLongExtra("registrationDate", 0);
        long alarmTime = getIntent().getLongExtra("alarmTime", 0);

        textViewDetailTodo.setText(todoText);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        textViewDetailRegistrationDate.setText(sdf.format(new Date(registrationDate)));

        if (alarmTime > 0) {
            String formattedAlarm = sdf.format(new Date(alarmTime));
            textViewDetailAlarmTime.setText(
                    getString(R.string.alarm_time_format, formattedAlarm)
            );
        }
    }
}