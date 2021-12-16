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
        Project projectTest1 = ps.getProjectObject(15);

        Project expected1 = new Project();
        expected1.setProjectTitle("Jul");
        expected1.setDescription("Et meget spændende projekt!");
        expected1.setProjectId(15);

        Project notExpected1 = new Project();
        notExpected1.setProjectTitle("");
        notExpected1.setDescription("Et meget spændende projekt!");
        notExpected1.setProjectId(15);

        Project notExpected2 = new Project();
        notExpected2.setProjectTitle("Jul");
        notExpected2.setDescription("");
        notExpected2.setProjectId(15);

        Project notExpected3 = new Project();
        notExpected3.setProjectTitle("Jul");
        notExpected3.setDescription("Et meget spændende projekt!");
        notExpected3.setProjectId(5);


        //Assert
        assertEquals(expected1, projectTest1);
        assertNotEquals(notExpected1, projectTest1);
        assertNotEquals(notExpected2, projectTest1);
        assertNotEquals(notExpected3, projectTest1);


    }

}