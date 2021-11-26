package com.example.eksamenvinter2021.Services;

import com.example.eksamenvinter2021.Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) {
        try {
            PreparedStatement stmt = JDBC.getConnection().prepareStatement("UPDATE `heroku_7aba49c42d6c0f0`." +
                    "`projects` SET `title` = 'Påske' WHERE (`project_id` = '25');");
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Test failed");
            System.out.println(e.getMessage());
        }
    }
}
