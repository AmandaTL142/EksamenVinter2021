package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.sql.Time;
import java.util.ArrayList;

public class TaskService {

    TaskRepo tr = new TaskRepo();


    public Task createNewTask(String title, String description, String estimatedTime, String timeUsed,
                              String status, String startDate, String endDate) {


        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setEstimatedTime(estimatedTime);
        task.setTimeUsed(timeUsed);
        task.setStatus(status);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        return task;
    }

    public void insertNewTaskToDB(Task task){
        tr.insertNewTaskToDB(task);
    }


    public void updateTask(Task t) {

        tr.updateTask(t);
    }

    public Task getTaskObject(int taskId) {
        return tr.getTaskFromDB(taskId);
    }


    public ArrayList<Task> getAllTasksInArray(){
        return tr.getTasksInArray();
    }

    public void deleteTask(int taskID){
        tr.deleteTask(taskID);
    }

}