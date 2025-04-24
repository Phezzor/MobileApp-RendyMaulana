package com.example.mobileapps;

import androidx.appcompat.app.AppCompatActivity;

public class TaskItemActivity extends AppCompatActivity {

    public String name;
    public boolean isDone;
    public String dateTime;

    public TaskItemActivity(String name, boolean isDone, String dateTime) {
        this.name = name;
        this.isDone = isDone;
        this.dateTime = dateTime;

    }
}