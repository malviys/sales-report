package com.sourabh.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {
    private final String databaseName;
    private final String userName;
    private final String password;

    private Connection connection;
    private PreparedStatement statement;

    public DataSource(String databaseName, String userName, String password) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void openConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + databaseName,
                        userName,
                        password
                );
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public PreparedStatement getStatement(String query) throws SQLException {
        if (statement == null)
            statement = connection.prepareStatement(query);

        return statement;
    }


    public void closeConnection() {
        if (connection != null) {
            try {
                if (statement != null) {
                    statement.close();
                }

                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        statement = null;
        connection = null;
    }
}
