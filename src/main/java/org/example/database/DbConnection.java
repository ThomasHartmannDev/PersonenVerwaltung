package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    protected final String jdbcUrl = "jdbc:mysql://db4free.net:3306/projektthomas";
    protected final String username = "admin_thomas";
    protected final String password = "password";
    protected Connection connection;

    public void openConnection() { // Open a new connection with the DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Make sure the Dependency is installed!
            connection = DriverManager.getConnection(jdbcUrl, username, password); // Try to connect to the server
            System.out.println("Db Connected!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, "jdbc Driver Not found", ex); // Catch for class not found
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database"); // Can't reach the Db.
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){ // Close the connection
        System.out.println("trying to close the connection!");
        try {
            if(connection != null){
                if(!connection.getAutoCommit()){ // if we disable the autocommit, we activate it again
                    connection.setAutoCommit(true);
                }
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
