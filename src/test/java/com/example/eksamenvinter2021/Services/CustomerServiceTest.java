package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Test
    void getCustomerObject() {
        //Arrange
        CustomerService ps = new CustomerService();


        //Act
        Customer customerTest1 = ps.getCustomerObject(5);

        Customer expected1 = new Customer();
        expected1.setCustomerId(5);
        expected1.setCustomerName("Amanda");


        //Assert
        assertEquals(expected1, customerTest1);
    }
}