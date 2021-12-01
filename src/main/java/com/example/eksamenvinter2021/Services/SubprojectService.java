package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Subproject;
import com.example.eksamenvinter2021.Resporsitories.SubprojectRepo;

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
}
