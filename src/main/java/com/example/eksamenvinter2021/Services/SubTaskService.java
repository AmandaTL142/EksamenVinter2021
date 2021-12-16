package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.SubTask;
import com.example.eksamenvinter2021.Resporsitories.SubTaskRepo;

public class SubTaskService {
    SubTaskRepo sr = new SubTaskRepo();

    public SubTask createNewSubtask( String title, String description, String estimatedTime, String timeUsed, String status,String startDate, String endDate){
        SubTask sb = new SubTask(title,description,estimatedTime,timeUsed,status,startDate,endDate);

        return sb;
    }

    public void updateSubtask(SubTask st){
        sr.updateSubtask(st);

    }

    public SubTask getSubtaskObject(int subtaskID){
        return sr.getSubtaskFromDB(subtaskID);
    }

    public void deleteSubtaskFromDB(int id){
        sr.deleteSubtaskFromDB(id);
    }


}
