package com.example.eksamenvinter2021.Models;

//CAS

public class Customer {
    private String customerName;
    private int customerId;

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public Customer(int customerId, String customerName) {
        this.customerName = customerName;
        this.customerId = customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Name = '" + customerName + '\'' +
                ", ID = " + customerId;

    }

    //Amanda Tolstrup Laursen og CAS
    //Denne metode er overridet, så vi kan sammenligne customers i unit-testen af getCustomerObject
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Customer)) {
            return false;
        } else {
            Customer otherCustomer = (Customer) other;
            boolean theSameId = otherCustomer.getCustomerId() == this.getCustomerId();
            if (otherCustomer.getCustomerName() == null && this.getCustomerName() != null) {
                return false;
            }

            if (this.getCustomerName() == null && otherCustomer.getCustomerName() != null) {
                return false;
            }

            boolean theSameName = otherCustomer.getCustomerName().equals(this.getCustomerName());

            if (theSameId == true && theSameName == true){
                return true;
            } else {
                return false;
            }
        }
    }
}

