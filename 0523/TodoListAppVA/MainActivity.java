package com.example.todolistappva;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final ActivityResultLauncher<String> requestNotificationPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(MainActivity.this, "알림 권한이 거부되었습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission.launch(
                        Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        EditText editTextTodo = findViewById(R.id.editTextTodo);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        RecyclerView recyclerViewTodo = findViewById(R.id.recyclerViewTodo);

        TodoDatabaseHelper dbHelper = new TodoDatabaseHelper(this);
        List<TodoItem> todoList = dbHelper.getAllTodos();

        TodoAdapter todoAdapter = new TodoAdapter(this, todoList, dbHelper);
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTodo.setAdapter(todoAdapter);

        buttonAdd.setOnClickListener(v -> {
            String todoText = editTextTodo.getText().toString().trim();
            if (!todoText.isEmpty()) {
                Calendar now = Calendar.getInstance();
                new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    long alarmTime = calendar.getTimeInMillis();
                    if (alarmTime < now.getTimeInMillis()) {
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        alarmTime = calendar.getTimeInMillis();
                    }
                    long registrationTime = now.getTimeInMillis();
                    TodoItem todoItem = new TodoItem(todoText,
                            registrationTime, alarmTime);
                    long id = dbHelper.insertTodo(todoItem);
                    todoItem.setId(id);
                    scheduleAlarm(todoItem, todoAdapter);
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),
                        true).show();
                editTextTodo.setText("");
            }
        });
    }

    private void scheduleAlarm(TodoItem todo, TodoAdapter todoAdapter) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("todoText", todo.getTodoText());
        int requestCode = (int) todo.getId();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT |
                        PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = getSystemService(AlarmManager.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            todo.getAlarmTime(),
                            pendingIntent);
                } else {
                    Toast.makeText(this,
                            "정확한 알람 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        todo.getAlarmTime(),
                        pendingIntent);
            }
            todoAdapter.getTodos().add(todo);
            todoAdapter.notifyItemInserted(
                    todoAdapter.getTodos().size() - 1);
        } catch (SecurityException e) {
            Log.e(TAG, "Alarm scheduling failed", e);
            Toast.makeText(this,
                    "알람 설정 중 오류가 발생했습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}