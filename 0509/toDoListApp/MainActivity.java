package com.example.todolistapp; // 본인의 패키지 이름으로 변경

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTodo;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTodo = findViewById(R.id.editTextTodo);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        RecyclerView recyclerViewTodo = findViewById(R.id.recyclerViewTodo);

        List<TodoItem> todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList);
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTodo.setAdapter(todoAdapter);

        // 아이템 클릭 리스너 설정 (상세 화면 이동)
        todoAdapter.setOnItemClickListener(position -> {
            TodoItem todoItem = todoAdapter.getTodoItem(position);
            if (todoItem != null) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("todoText", todoItem.getTask());
                startActivity(intent);
            }
        });

        // 아이템 롱클릭 리스너 설정 (삭제 또는 수정)
        todoAdapter.setOnItemLongClickListener(this::showOptionsDialog);

        // 추가 버튼 클릭 리스너
        buttonAdd.setOnClickListener(v -> {
            String todoText = editTextTodo.getText().toString().trim();
            if (!todoText.isEmpty()) {
                todoAdapter.addTodo(todoText);
                editTextTodo.setText("");
            }
        });
    }

    private void showOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("옵션 선택")
                .setItems(new CharSequence[]{"삭제", "수정"}, (dialog, which) -> {
                    switch (which) {
                        case 0: // 삭제
                            deleteTodoItem(position);
                            break;
                        case 1: // 수정
                            showEditDialog(position);
                            break;
                    }
                })
                .show();
    }

    private void deleteTodoItem(int position) {
        todoAdapter.removeTodo(position);
        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void showEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("할 일 수정");

        final EditText input = new EditText(this);
        TodoItem currentItem = todoAdapter.getTodoItem(position);
        if (currentItem != null) {
            input.setText(currentItem.getTask());
        }
        builder.setView(input);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String newTask = input.getText().toString().trim();
            if (!newTask.isEmpty() && currentItem != null) {
                todoAdapter.updateTodo(position, newTask);
                Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}