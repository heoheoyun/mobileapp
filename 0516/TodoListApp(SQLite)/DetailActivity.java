package com.example.todolistappsqlite; // 본인의 패키지 이름으로 변경

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textViewDetailTodo = findViewById(R.id.textViewDetailTodo);
        // 이 변수 선언 확인
        TextView textViewDetailRegistrationDate = findViewById(R.id.textViewDetailRegistrationDate); // findViewById 호출 시 ID 확인

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("todoText")) {
            String todoText = intent.getStringExtra("todoText");
            textViewDetailTodo.setText(todoText);
            textViewDetailRegistrationDate.setText("등록 날짜 정보 없음"); // 이 부분은 필요에 따라 수정
        }
    }
}