package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.sql.Time;

public class TaskService {

    TaskRepo tr = new TaskRepo();

    Project p = new Project();
    Subproject sp = new Subproject();
    Task t = new Task();

    public Task createNewTask(String title, String description, Time estimatedTime, Time timeUsed, String status) {
        Task t = new Task(title, description, estimatedTime, timeUsed, status);
        tr.insertNewTaskToDB(t);

        return t;
    }

    public void readTask() {

    }

    public void updateTask() {
        tr.updateTask();
    }
}


