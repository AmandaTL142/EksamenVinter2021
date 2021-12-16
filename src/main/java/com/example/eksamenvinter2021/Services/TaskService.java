package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

import java.util.ArrayList;
//Hele klassen lavet af Andrea
public class TaskService {

    TaskRepo tr = new TaskRepo();


    public Task createNewTask(String title, String description, String estimatedTime, String timeUsed,
                              String status, String startDate, String endDate) {


        Task task = new Task();
        task.setTaskTitle(title);
        task.setTaskDescription(description);
        task.setTaskEstimatedTime(estimatedTime);
        task.setTaskTimeUsed(timeUsed);
        task.setTaskStatus(status);
        task.setTaskStartDate(startDate);
        task.setTaskEndDate(endDate);

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

    public void deleteTask(int taskId){
        tr.deleteTask(taskId);
    }

}