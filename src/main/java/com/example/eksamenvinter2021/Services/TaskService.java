package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;

public class TaskService {

    TaskRepo tr = new TaskRepo();

    Project p = new Project();
    Task t = new Task();

    public void createNewTask( String title,String description, String estimated_time, String status){
        Task t = new Task();
        tr.insertNewTaskToDB(t, p.getProjectId());
    }

    public void readTask(){

    }

    public void updateTask(){
        tr.updateTask();
    }

    public void deleteTask(){
        tr.deleteTask(t.getTaskID());
    }
}
