package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Services.ProjectService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepoTest {

    @Test
    void testGetProjectId() {
        //Arrange
        ProjectRepo pr = new ProjectRepo();


        //Act
        int projectIdTest1 = pr.getProjectId("Jul");

        int expected1 = 15;
        int notExpected1 = 5;
        int notExpected2 = 25;


        //Assert
        assertEquals(expected1, projectIdTest1);
        assertNotEquals(notExpected1, projectIdTest1);
        assertNotEquals(notExpected2, projectIdTest1);
    }
}