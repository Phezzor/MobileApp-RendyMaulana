package com.example.mobileapps;

public class Task {
    private String description;
    private boolean isDone;
    private String deadline;

    public Task(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
