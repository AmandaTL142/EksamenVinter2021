package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
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

    public Subproject showSubproject (int subprojectId) {
        return spr.getSubprojectFromDatabase(subprojectId);
    }

    public ArrayList<Subproject> showSubprojectLinkedToProject(int thisProjectId) {
        return spr.showSubprojectLinkedToProject(thisProjectId);
    }

    public void deleteSubprojectFromDatabase(int id) {
        spr.deleteSubprojectFromDatabase(id);
    }
}
