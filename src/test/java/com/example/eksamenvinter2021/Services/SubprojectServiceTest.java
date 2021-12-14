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

        Subproject notExpected1 = new Subproject();
        notExpected1.setSubprojectId(5);
        notExpected1.setSubprojectTitle("1. søndag i advent");
        notExpected1.setSubprojectDescription("Lys og konfekt.");
        notExpected1.setProjectId(15);

        Subproject notExpected2 = new Subproject();
        notExpected2.setSubprojectId(15);
        notExpected2.setSubprojectTitle("");
        notExpected2.setSubprojectDescription("Lys og konfekt.");
        notExpected2.setProjectId(15);

        Subproject notExpected3 = new Subproject();
        notExpected3.setSubprojectId(15);
        notExpected3.setSubprojectTitle("1. søndag i advent");
        notExpected3.setSubprojectDescription("");
        notExpected3.setProjectId(15);

        Subproject notExpected4 = new Subproject();
        notExpected4.setSubprojectId(15);
        notExpected4.setSubprojectTitle("1. søndag i advent");
        notExpected4.setSubprojectDescription("Lys og konfekt.");
        notExpected4.setProjectId(5);


        //Assert
        assertEquals(expected1, subprojectTest1);
        assertNotEquals(notExpected1, subprojectTest1);
        assertNotEquals(notExpected2, subprojectTest1);
        assertNotEquals(notExpected3, subprojectTest1);
        assertNotEquals(notExpected4, subprojectTest1);
    }
}
