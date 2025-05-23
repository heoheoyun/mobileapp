package com.example.todolistappva;

import androidx.annotation.Keep;

@Keep
@SuppressWarnings("unused")
public class TodoItem {
    private long id;
    private String todoText;
    private long registrationDate;
    private long alarmTime;

    public TodoItem(long id, String todoText, long registrationDate, long alarmTime) {
        this.id = id;
        this.todoText = todoText;
        this.registrationDate = registrationDate;
        this.alarmTime = alarmTime;
    }

    public TodoItem(String todoText, long registrationDate, long alarmTime) {
        this.todoText = todoText;
        this.registrationDate = registrationDate;
        this.alarmTime = alarmTime;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTodoText() { return todoText; }
    public void setTodoText(String todoText) { this.todoText = todoText; }
    public long getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(long registrationDate) { this.registrationDate = registrationDate; }
    public long getAlarmTime() { return alarmTime; }
    public void setAlarmTime(long alarmTime) { this.alarmTime = alarmTime; }
}