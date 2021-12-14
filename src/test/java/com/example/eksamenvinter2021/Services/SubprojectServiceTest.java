package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Models.Subproject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubprojectServiceTest {

    @Test
    void getSubprojectObject() {
        //Arrange
        SubprojectService sps = new SubprojectService();


        //Act
        Subproject subprojectTest1 = sps.getSubprojectObject(15);

        Subproject expected1 = new Subproject();
        expected1.setSubprojectId(15);
        expected1.setSubprojectTitle("1. søndag i advent");
        expected1.setSubprojectDescription("Lys og konfekt.");
        expected1.setProjectId(15);


        //Assert
        assertEquals(expected1, subprojectTest1);
    }
}
