package com.jamb.Jamb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB instance;
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String DATABASEDRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";


    private ConnectDB() throws SQLException {
        try {
            Class.forName(DATABASEDRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    //method to get database connection
    public static Connection getConnection() {
        return connection;
    }

    //static method to create instance of Singleton class
    public static ConnectDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectDB();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectDB();
        }
        return instance;
    }

    //static method to close connection
    public static void closeConnection() throws SQLException {
        try {
            System.out.println("----Connection closed with MYSQL database----");
            connection.close();
        }catch (Exception e)
        {
            System.out.println("Close connection failed  : " + e.getMessage());
        }

}}
