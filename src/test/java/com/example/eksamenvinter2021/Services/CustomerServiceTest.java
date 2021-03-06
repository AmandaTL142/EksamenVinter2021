package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Models.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    //Amanda Tolstrup Laursen

    //Her testes det, om det rette Customer-objekt returneres, når dets id gives som input i metoden.
    @Test
    void getCustomerObject() {
        //Arrange
        CustomerService ps = new CustomerService();


        //Act
        Customer customerTest1 = ps.getCustomerObject(205);

        Customer expected1 = new Customer();
        expected1.setCustomerId(205);
        expected1.setCustomerName("Københavns Kommune");

        Customer notExpected1 = new Customer();
        notExpected1.setCustomerId(215);
        notExpected1.setCustomerName("Københavns Kommune");

        Customer notExpected2 = new Customer();
        notExpected2.setCustomerId(205);
        notExpected2.setCustomerName("");

        //Assert
        assertEquals(expected1, customerTest1);
        assertNotEquals(notExpected1, customerTest1);
        assertNotEquals(notExpected2, customerTest1);
    }
}