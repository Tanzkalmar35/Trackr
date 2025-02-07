package com.example.trackr.data.task;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String taskName;
    public String date;
    public String startTime;
    public String endTime;
}
