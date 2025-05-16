package com.example.todolistappsqlite; // 본인의 패키지 이름으로 변경

import java.util.Date;

public class TodoItem {
    private long id; // 데이터베이스 ID 추가
    private String task;
    private Date registrationDate;
    private boolean isCompleted;

    public TodoItem(String task) {
        this.task = task;
        this.registrationDate = new Date();
        this.isCompleted = false;
        this.id = -1; // 초기값 설정
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // 나머지 getter 및 setter는 동일
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}