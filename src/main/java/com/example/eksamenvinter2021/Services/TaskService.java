package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.sql.Time;
import java.util.ArrayList;

public class TaskService {

    TaskRepo tr = new TaskRepo();

    public Task createNewTask(String title, String description, String estimatedTime, String timeUsed, String status) {
        Task task = new Task(title, description, estimatedTime, timeUsed, status);
        //tr.insertNewTaskToDB(task);
        return task;
    }

    public Task updateTask(String title, String description, String estimatedTime, String timeUsed, String status) {
        Task t = new Task(title, description, estimatedTime, timeUsed, status);
        tr.updateTask(t);

        return t;

    }


    public ArrayList<Task> getAllTasks(int taskID){
        return tr.getAllTasksInnProject(taskID);
    }

    public Task getTaskObject(int taskId) {
        return tr.getTaskFromDB(taskId);
    }

    public void deleteTask(int taskID){
        tr.deleteTask(taskID);
    }

    public ArrayList<Task> showTaskLinkedToProject(int thisProject){
        return tr.getTaskLinkedToProject(thisProject);
    }



}


