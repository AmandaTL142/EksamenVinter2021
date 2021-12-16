package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Subproject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubprojectServiceTest {
    //Amanda Tolstrup Laursen

    @Test
    void getSubprojectObject() {
        //Arrange
        SubprojectService sps = new SubprojectService();


        //Act
        Subproject subprojectTest1 = sps.getSubprojectObject(185);

        Subproject expected1 = new Subproject();
        expected1.setSubprojectId(185);
        expected1.setSubprojectTitle("Dig a hole");
        expected1.setSubprojectDescription("We're gonna dig");
        expected1.setProjectId(465);

        Subproject notExpected1 = new Subproject();
        notExpected1.setSubprojectId(175);
        notExpected1.setSubprojectTitle("Dig a hole");
        notExpected1.setSubprojectDescription("We're gonna dig");
        notExpected1.setProjectId(465);

        Subproject notExpected2 = new Subproject();
        notExpected2.setSubprojectId(185);
        notExpected2.setSubprojectTitle("");
        notExpected2.setSubprojectDescription("We're gonna dig");
        notExpected2.setProjectId(465);

        Subproject notExpected3 = new Subproject();
        notExpected3.setSubprojectId(185);
        notExpected3.setSubprojectTitle("Dig a hole");
        notExpected3.setSubprojectDescription("");
        notExpected3.setProjectId(465);

        Subproject notExpected4 = new Subproject();
        notExpected4.setSubprojectId(185);
        notExpected4.setSubprojectTitle("Dig a hole");
        notExpected4.setSubprojectDescription("We're gonna dig");
        notExpected4.setProjectId(455);


        //Assert
        assertEquals(expected1, subprojectTest1);
        assertNotEquals(notExpected1, subprojectTest1);
        assertNotEquals(notExpected2, subprojectTest1);
        assertNotEquals(notExpected3, subprojectTest1);
        assertNotEquals(notExpected4, subprojectTest1);
    }
}
