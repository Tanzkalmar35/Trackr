package com.example.trackr.data.task;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Query("SELECT * FROM tasks ORDER BY startTime DESC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY startTime DESC")
    List<Task> getAllTasksOfDay(final String date);
}
