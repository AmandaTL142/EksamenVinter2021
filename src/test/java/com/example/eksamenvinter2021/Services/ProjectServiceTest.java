package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {
    //Amanda Tolstrup Laursen

    @Test
    //Denne test er bestået
    void getProjectObject() {
        //Arrange
        ProjectService ps = new ProjectService();


        //Act
        Project projectTest1 = ps.getProjectObject(455);

        Project expected1 = new Project();
        expected1.setProjectTitle("Build new Playground");
        expected1.setDescription("We're gonna build a new playground for a public kindergarten.");
        expected1.setProjectId(455);

        Project notExpected1 = new Project();
        notExpected1.setProjectTitle("");
        notExpected1.setDescription("We're gonna build a new playground for a public kindergarten.");
        notExpected1.setProjectId(455);

        Project notExpected2 = new Project();
        notExpected2.setProjectTitle("Build new Playground");
        notExpected2.setDescription("");
        notExpected2.setProjectId(455);

        Project notExpected3 = new Project();
        notExpected3.setProjectTitle("Build new Playground");
        notExpected3.setDescription("We're gonna build a new playground for a public kindergarten.");
        notExpected3.setProjectId(445);


        //Assert
        assertEquals(expected1, projectTest1);
        assertNotEquals(notExpected1, projectTest1);
        assertNotEquals(notExpected2, projectTest1);
        assertNotEquals(notExpected3, projectTest1);


    }

}