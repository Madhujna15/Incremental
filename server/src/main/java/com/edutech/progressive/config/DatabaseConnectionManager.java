package com.edutech.progressive.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    
    private static Properties properties= new Properties();

    public static void loadProperties(){
        try {
            InputStream inputStream = DatabaseConnectionManager.class.getClassLoader().getResourceAsStream("application.properties");
            if (inputStream==null) {
                throw new RuntimeException("application.properties file not found");
            }
           properties.load(inputStream);    
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file", e);
            
        }
    }
    public static Connection getConnection() throws SQLException{
        try {
            if (properties.isEmpty()) {
                loadProperties();
            }
            String url= properties.getProperty("spring.datasource.url");
            String username= properties.getProperty("spring.datasource.username");
            String password= properties.getProperty("spring.datasource.password");
            String driver= properties.getProperty("spring.datasource.driver-class-name");
            Class.forName(driver);
            return DriverManager.getConnection(url,username, password);

        } catch (ClassNotFoundException | SQLException e) { 
            throw new RuntimeException("Failed to create database connection", e);

        }
        
    }
}
