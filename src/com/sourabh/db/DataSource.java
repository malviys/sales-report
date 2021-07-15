package com.sourabh.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
* DataSource provides methods to establish connection to underlying Sql database.
* Classes using DataSource class should create connection with createConnection() method and 
* close connection to database using closeConnection() method that properly close the connection to database.
* It is best practice to close connection as soon as transation is done.
*/
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

    /**
    * Open connection with underlying database
    */
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

    /**
    * Returns PreparedStatement, statement should be closed via Statement.close() method or call DataSource.closeConnection() to close the statement properly.
    */
    public PreparedStatement getStatement(String query) throws SQLException {
        if (statement == null)
            statement = connection.prepareStatement(query);

        return statement;
    }

    /**
    * Closes the database connection and statement.
    * Method should be called as soon as transaction is completed.
    */
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
