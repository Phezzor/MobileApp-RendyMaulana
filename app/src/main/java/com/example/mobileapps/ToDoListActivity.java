    package com.example.mobileapps;

    import android.app.DatePickerDialog;
    import android.app.TimePickerDialog;
    import android.os.Bundle;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import java.util.ArrayList;
    import java.util.Calendar;

    public class ToDoListActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_to_do_list);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // To-Do List Elements
            EditText editTextTask = findViewById(R.id.editTextTask);
            Button buttonAddTask = findViewById(R.id.buttonAddTask);
            ListView listViewTasks = findViewById(R.id.listViewTasks);

            ArrayList<TaskItemActivity> taskList = new ArrayList<>();
            TaskAdapter adapter = new TaskAdapter(this, taskList);
            listViewTasks.setAdapter(adapter);

            buttonAddTask.setOnClickListener(v -> {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    // Tampilkan dialog tanggal
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ToDoListActivity.this,
                            (view, year, month, dayOfMonth) -> {
                                // Setelah tanggal dipilih, tampilkan TimePicker
                                TimePickerDialog timePickerDialog = new TimePickerDialog(ToDoListActivity.this,
                                        (timeView, hourOfDay, minute) -> {
                                            // Format: dd/MM/yyyy - HH:mm
                                            String dateTime = String.format("%02d/%02d/%04d - %02d:%02d", dayOfMonth, month + 1, year, hourOfDay, minute);
                                            taskList.add(new TaskItemActivity(task, false, dateTime));
                                            adapter.notifyDataSetChanged();
                                            editTextTask.setText("");
                                        },
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        true
                                );
                                timePickerDialog.show();
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.show();
                } else {
                    Toast.makeText(ToDoListActivity.this, "Tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }