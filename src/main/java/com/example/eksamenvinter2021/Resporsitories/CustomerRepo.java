package com.example.eksamenvinter2021.Resporsitories;

import com.example.eksamenvinter2021.Models.Customer;
import com.example.eksamenvinter2021.Models.Project;
import com.example.eksamenvinter2021.Services.CustomerService;
import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepo {

    public void insertCustomerIntoDatabase(Customer customer) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("INSERT INTO heroku_7aba49c42d6c0f0.customers (`name`) " + "VALUES (?);");
            stmt.setString(1, customer.getCustomerName());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Customer could not be inserted into database");
            System.out.println(e.getMessage());
        }
    }
    
    public Customer getCustomerFromDatabase(int id) {
        Customer cus = new Customer();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM " + "heroku_7aba49c42d6c0f0.customers WHERE customer_id=?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            String name = rs.getString("name");

            cus = new Customer(name);
            cus.setCustomerId(id);
            cus.setCustomerName(name);

        } catch(SQLException e){
            System.out.println("Couldn't get the customer with id: " + id + " from the database");
            System.out.println(e.getMessage());
        }
        return cus;
    }

    public void deleteCustomerFromDatabase(int id) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("DELETE FROM `heroku_7aba49c42d6c0f0`.`customers` WHERE (`customer_id` = '" + id + "');");
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Couldn't delete the customer with id: " + id + " from database");
            System.out.println(e.getMessage());
        }

    }

    public void updateCustomerInDatabase(Customer customer) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement
                    ("UPDATE `heroku_7aba49c42d6c0f0`.`customers` SET `name` = ? WHERE (`customer_id` = ?);");

            stmt.setString(1, customer.getCustomerName());
            stmt.setInt(2, customer.getCustomerId());
            System.out.println("update method CR: " +customer);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Couldn't update Customer: '" + customer.getCustomerName() + "' ID: " + customer.getCustomerId() +" in database");
            System.out.println(e.getMessage());
        }

    }

    public int getCustomerIdFromDatabase(String name) {
        int customer_id = 0;
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
                    "SELECT customer_id FROM heroku_7aba49c42d6c0f0.customers WHERE name=?;");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("customer_id");


        } catch(SQLException e){
            System.out.println("Couldn't get the customer-id for customer with name " + name + " from the database");
            System.out.println(e.getMessage());
        }
        return customer_id;
    }

    public String returnCustomerNameFromId(int customerId){

        String name = null;

        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement(
                    "SELECT * FROM " + "heroku_7aba49c42d6c0f0.customers WHERE customer_id=?;");
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            name = rs.getString("name");

        } catch(SQLException e){
            System.out.println("Couldn't get the customer with id: " + customerId + " from the database");
            System.out.println(e.getMessage());
        }
        return name;
    }

    public ArrayList <Customer> allCustomers(){
        ArrayList<Customer> customerList = new ArrayList<>();
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("SELECT * FROM " +
                    "heroku_7aba49c42d6c0f0.customers;");
            ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int customerId = rs.getInt("customer_id");
                    String customerName = rs.getString("name");

                    Customer tempCustomer = new Customer(customerId, customerName);
                    tempCustomer.setCustomerId(customerId);
                    tempCustomer.setCustomerName(customerName);

                    customerList.add(tempCustomer);
                }

            } catch (SQLException e) {
                System.out.println("Couldn't get customers from database");
                System.out.println(e.getMessage());
            }
            return customerList;
        }
    }

