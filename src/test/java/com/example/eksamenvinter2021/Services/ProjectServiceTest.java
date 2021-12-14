package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    @Test
    void createNewProjectObject() {
        /*
        //Arrange
        ProjectService ps = new ProjectService();

        //Act
        Project projectTest1 = ps.createNewProjectObject("title", "projectDeadline", "status", 0, 1);

        Project expected1 = new Project("title", "projectDeadline", "status", 0, 1);
        //Assert
        assertEquals(expected1, projectTest1);

         */
    }

    @Test
    //Denne test er bestået
    void getProjectObject() {
        //Arrange
        ProjectService ps = new ProjectService();


        //Act
        Project projectTest1 = ps.getProjectObject(15);

        Project expected1 = new Project();
        expected1.setProjectTitle("Jul");
        expected1.setDescription("Et meget spændende projekt!");
        expected1.setProjectId(15);


        //Assert
        assertEquals(expected1, projectTest1);
    }

}