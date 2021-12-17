package com.example.eksamenvinter2021.Resporsitories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepoTest {
    //Amanda Tolstrup Laursen

    @Test
    void getCustomerIdFromDatabase() {
        //Arrange
        CustomerRepo pr = new CustomerRepo();


        //Act
        int customerIdTest1 = pr.getCustomerIdFromDatabase("Københavns Kommune");

        int expected1 = 205;
        int notExpected1 = 195;
        int notExpected2 = 210;


        //Assert
        assertEquals(expected1, customerIdTest1);
        assertNotEquals(notExpected1, customerIdTest1);
        assertNotEquals(notExpected2, customerIdTest1);
    }

    @Test
    void returnCustomerNameFromId() {
        //Arrange
        CustomerRepo pr = new CustomerRepo();

        //Act
        String customerNameTest1 = pr.returnCustomerNameFromId(205);

        String expected1 = "Københavns Kommune";
        String notExpected1 = "";
        String notExpected2 = "KMD";


        //Assert
        assertEquals(expected1, customerNameTest1);
        assertNotEquals(notExpected1, customerNameTest1);
        assertNotEquals(notExpected2, customerNameTest1);

    }
}