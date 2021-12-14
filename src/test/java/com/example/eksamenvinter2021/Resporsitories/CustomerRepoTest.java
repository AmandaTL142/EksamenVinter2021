package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Services.CustomerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepoTest {

    @Test
    void getCustomerIdFromDatabase() {
        //Arrange
        CustomerRepo pr = new CustomerRepo();


        //Act
        int customerIdTest1 = pr.getCustomerIdFromDatabase("Amanda");

        int expected1 = 5;
        int notExpected1 = 0;
        int notExpected2 = 15;


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
        String customerNameTest1 = pr.returnCustomerNameFromId(5);

        String expected1 = "Amanda";
        String notExpected1 = "";
        String notExpected2 = "Karl";


        //Assert
        assertEquals(expected1, customerNameTest1);
        assertNotEquals(notExpected1, customerNameTest1);
        assertNotEquals(notExpected2, customerNameTest1);

    }
}