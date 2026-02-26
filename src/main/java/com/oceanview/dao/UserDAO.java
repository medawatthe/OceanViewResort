package com.oceanview.dao;

import com.oceanview.model.Staff;
import com.oceanview.model.User;
import com.oceanview.util.DatabaseManager;

import java.sql.*;

/**
 * UserDAO - Data Access Object for User operations
 * Implements DAO Pattern
 */
public class UserDAO {

    private DatabaseManager dbManager;

    public UserDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * Authenticate user login
     */
    public User authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                String role = rs.getString("role");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");

                // Create appropriate user object based on role
                if ("STAFF".equals(role) || "ADMIN".equals(role)) {
                    Staff user = new Staff();
                    user.setUserId(userId);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(role);
                    user.setFullName(fullName);
                    user.setEmail(email);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    /**
     * Get user by ID
     */
    public User getUserById(String userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Staff user = new Staff();
                user.setUserId(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    /**
     * Create new user
     */
    public boolean createUser(User user) {
        String query = "INSERT INTO users (user_id, username, password, role, full_name, email) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getFullName());
            pstmt.setString(6, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt);
        }
    }

    /**
     * Update user
     */
    public boolean updateUser(User user) {
        String query = "UPDATE users SET full_name = ?, email = ? WHERE user_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUserId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt);
        }
    }

    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return false;
    }

    /**
     * Close resources
     */
    private void closeResources(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
