package com.example.eksamenvinter2021.Resporsitories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepoTest {
    //Amanda Tolstrup Laursen

    @Test
    void testGetProjectId() {
        //Arrange
        ProjectRepo pr = new ProjectRepo();


        //Act
        int projectIdTest1 = pr.getProjectIdFromTitle("Build Papirøen");

        int expected1 = 415;
        int notExpected1 = 425;
        int notExpected2 = 405;


        //Assert
        assertEquals(expected1, projectIdTest1);
        assertNotEquals(notExpected1, projectIdTest1);
        assertNotEquals(notExpected2, projectIdTest1);
    }
}