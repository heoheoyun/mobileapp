package com.example.todolistapp; // 본인의 패키지 이름으로 변경

import java.util.Date;

public class TodoItem {
    private String task;
    private final Date registrationDate;
    private boolean isCompleted;

    public TodoItem(String task) {
        this.task = task;
        this.registrationDate = new Date(); // 생성 시 현재 날짜로 초기화
        this.isCompleted = false; // 기본적으로 미수행 상태
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}