package com.example.eksamenvinter2021;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Task;
import com.example.eksamenvinter2021.Resporsitories.ProjectRepo;
import com.example.eksamenvinter2021.Resporsitories.TaskRepo;
import com.example.eksamenvinter2021.Utility.ConnectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
@SpringBootTest
public class TimeMethods {
    //Forfatter: Christian Hundahl
    //Tidsmetoder
    TaskRepo tr = new TaskRepo();

    @Test
    public static void main(String[] args) {
        ProjectRepo pr = new ProjectRepo();
        Project p = pr.getProjectFromDatabase(15);
    }

}