// MainActivity.java
package com.example.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editMemo;
    private String currentFileName = "memo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        editMemo = findViewById(R.id.editMemo);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);

        // 저장 버튼 클릭 이벤트
        btnSave.setOnClickListener(v -> showSaveDialog());

        // 불러오기 버튼 클릭 이벤트
        btnLoad.setOnClickListener(v -> showLoadDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            // 새 메모 작성
            editMemo.setText("");
            currentFileName = "memo.txt";
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 저장 다이얼로그 표시
    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setText(currentFileName);
        builder.setTitle("파일 이름 입력")
                .setView(input)
                .setPositiveButton("저장", (dialog, which) -> {
                    String fileName = input.getText().toString();
                    if (!fileName.endsWith(".txt")) {
                        fileName += ".txt";
                    }
                    saveToFile(fileName);
                })
                .setNegativeButton("취소", null)
                .show();
    }

    // 파일 저장 기능
    private void saveToFile(String fileName) {
        try {
            String memoContent = editMemo.getText().toString();
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(memoContent.getBytes());
            fos.close();
            currentFileName = fileName;
            Toast.makeText(this, fileName + " 파일로 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 불러오기 다이얼로그 표시
    private void showLoadDialog() {
        List<String> fileList = getFileList();

        if (fileList.isEmpty()) {
            Toast.makeText(this, "저장된 메모가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String[] files = fileList.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("파일 선택")
                .setItems(files, (dialog, which) -> loadFile(files[which]))
                .setNegativeButton("취소", null)
                .show();
    }

    // 저장된 파일 목록 가져오기
    private List<String> getFileList() {
        List<String> fileList = new ArrayList<>();
        File directory = getFilesDir();
        String[] files = directory.list();

        if (files != null) {
            for (String file : files) {
                if (file.endsWith(".txt")) {
                    fileList.add(file);
                }
            }
        }

        return fileList;
    }

    // 파일 로드 기능
    private void loadFile(String fileName) {
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            reader.close();
            fis.close();

            editMemo.setText(sb.toString());
            currentFileName = fileName;
            Toast.makeText(this, fileName + " 파일을 불러왔습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "파일 읽기 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}