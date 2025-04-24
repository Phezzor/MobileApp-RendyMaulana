package com.example.mobileapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItemActivity> {
    public TaskAdapter(Context context, ArrayList<TaskItemActivity> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItemActivity task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_task_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.taskText);
        TextView dateView = convertView.findViewById(R.id.taskDate);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);// ambil TextView tanggal

        text.setText(task.name);
        dateView.setText(task.dateTime);
        checkBox.setChecked(task.isDone);// tampilkan tanggal

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> task.isDone = isChecked);


        return convertView;
    }
}
