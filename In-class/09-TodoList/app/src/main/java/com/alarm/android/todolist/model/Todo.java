package com.alarm.android.todolist.model;

import com.orm.SugarRecord;

public class Todo extends SugarRecord {
    private String title;
    private boolean isDone;

    public Todo() {
    }

    public Todo(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
}
