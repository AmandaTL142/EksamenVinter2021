package com.example.eksamenvinter2021.Models;

public class Customer {

    private String customerName;
    private int customerId;


    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public Customer(String customerName){
        this.customerName = customerName;
    }

    public Customer(){}

}
