package com.oceanview.util;

import java.sql.*;

/**
 * DatabaseManager - Singleton pattern for database connections
 * PostgreSQL implementation
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    // PostgreSQL Database credentials
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ocean_view_resort";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234"; // PostgreSQL password

    // Private constructor
    private DatabaseManager() {
        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get singleton instance - Singleton Pattern
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Get database connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established successfully.");
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Execute SELECT query
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Execute INSERT, UPDATE, DELETE
     */
    public int executeUpdate(String query) throws SQLException {
        Statement statement = getConnection().createStatement();
        return statement.executeUpdate(query);
    }

    /**
     * Close ResultSet
     */
    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
    }

    /**
     * Close Statement
     */
    public void closeStatement(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }
    }
}
