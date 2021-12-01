package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.sql.Time;

public class TaskService {

 TaskRepo tr = new TaskRepo();

    public void createNewTask(String title, String description, String estimatedTime, String timeUsed, String status) {
        Task task = new Task(title, description, estimatedTime, timeUsed, status);
        tr.insertNewTaskToDB(task);

    }

    public Task fetchSingleTask(Task t){

        tr.fetchSingleTask();
        return t;
    }

    public void showTask() {

    }

    public void updateTask() {
        tr.updateTask();
    }
}


