package com.example.eksamenvinter2021.Resporsitories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepoTest {
    //Amanda Tolstrup Laursen

    //Her testes det, om det rette id returneres i databasen, når titlen "Københavns Kommune" gives som input i metoden.
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

    //Her testes det, om det rette navn returneres i databasen, når id=205 gives som input i metoden.
    @Test
    void returnCustomerNameFromId() {
        //Arrange
        CustomerRepo pr = new CustomerRepo();

        //Act
        String customerNameTest1 = pr.returnCustomerNameFromId(205);

        String expected1 = "Københavns Kommune";
        String notExpected1 = "";
        String notExpected2 = "KMD";    //Denne kunde findes også i databasen


        //Assert
        assertEquals(expected1, customerNameTest1);
        assertNotEquals(notExpected1, customerNameTest1);
        assertNotEquals(notExpected2, customerNameTest1);

    }
}