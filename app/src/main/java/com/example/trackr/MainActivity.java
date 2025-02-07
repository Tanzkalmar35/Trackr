package com.example.trackr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackr.adapter.TaskAdapter;
import com.example.trackr.data.task.Task;
import com.example.trackr.data.task.TaskDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView activityListView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityListView = findViewById(R.id.activityListView);
        Button addActivityButton = findViewById(R.id.addActivityButton);

        // Initialize the task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);
        activityListView.setAdapter(taskAdapter);

        // Example task entry
        appendDbTasks();

        // Set up the button click listener to add new tasks
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Task newTask = new Task();
                newTask.taskName = "Some example task";
                newTask.date = "Today";
                newTask.startTime = "10:00";
                newTask.endTime = "11:00";

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // Insert task on a background thread
                        TaskDatabase.getInstance(getApplicationContext()).taskDao().insert(newTask);

                        // Update the UI on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                appendDbTasks();
                            }
                        });
                    }
                });
            }
        });
    }

    private void appendDbTasks() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Task> dbTasks = TaskDatabase.getInstance(getApplicationContext()).taskDao().getAllTasks();

                // Update the UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskList.clear();
                        taskList.addAll(dbTasks);
                        taskAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
