package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubprojectService {

    SubprojectRepo spr = new SubprojectRepo();

    public Subproject createNewSubproject(String title, String deadline, String status, int projectId) {
        Subproject sp = new Subproject(title, deadline, status, projectId);
        return sp;
    }

    //Jeg har ikke integreret, hvordan metoden får ændringerne fra brugeren.
    public void updateSubproject(Subproject sp, String title, String description, String subprojectDeadline, String status) {
        sp.setSubprojectTitle(title);
        sp.setSubprojectDescription(description);
        sp.setSubprojectDeadline(subprojectDeadline);
        sp.setSubprojectStatus(status);
        spr.updateSubprojectInDatabase(sp);
    }

    public Subproject getSubprojectObject(int subprojectId) {
        return spr.getSubprojectFromDatabase(subprojectId);
    }

    public ArrayList<Subproject> showSubprojectLinkedToProject(int thisProjectId) {
        return spr.getSubprojectsLinkedToProject(thisProjectId);
    }


    public void deleteSubprojectFromDatabase(int id) {
        spr.deleteSubprojectFromDatabase(id);
    }

    public void insertSubprojectIntoDatabase(Subproject sp) {
        spr.insertSubprojectIntoDatabase(sp);
    }

    public void updateSubprojectInDatabase(Subproject sp) {
        spr.updateSubprojectInDatabase(sp);
    }

    public ArrayList<Subproject> getSubprojectsLinkedToProject(int thisProjectId) {
        return spr.getSubprojectsLinkedToProject(thisProjectId);
    }

    public int getSubprojectIdByTitle(String title) {
        return spr.getSubprojectIdByTitle(title);
    }

    public ArrayList<Task> getTasksLinkedToSubproject(int thisSubprojectId) {
        return spr.getTasksLinkedToSubproject(thisSubprojectId);
    }

}
