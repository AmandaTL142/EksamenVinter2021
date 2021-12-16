package com.example.eksamenvinter2021.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    //Christian Hundahl

        //Creates db connection and returns as Singleton
        private static String url;
        private static String username;
        private static String password;
        private static Connection conn;

        public ConnectionManager() {
        }

        public static Connection getConnection() {
            if (conn != null) {
                return conn;
            }
            url = System.getenv("url");
            username = System.getenv("username");
            password = System.getenv("password");

            try  {
                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Connection established");

            } catch (SQLException e) {
                System.out.println("No connection");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return conn;
        }

}
