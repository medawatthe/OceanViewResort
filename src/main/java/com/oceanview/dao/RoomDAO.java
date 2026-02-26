package com.oceanview.dao;

import com.oceanview.model.Room;
import com.oceanview.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RoomDAO - Data Access Object for Room operations
 */
public class RoomDAO {

    private DatabaseManager dbManager;

    public RoomDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * Get all room types
     */
    public List<Room> getAllRoomTypes() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room_types ORDER BY rate_per_night";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Room room = new Room();
                room.setRoomTypeId(rs.getInt("room_type_id"));
                room.setTypeName(rs.getString("type_name"));
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                room.setCapacity(rs.getInt("capacity"));
                room.setDescription(rs.getString("description"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error getting room types: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt);
        }

        return rooms;
    }

    /**
     * Get room type by name
     */
    public Room getRoomTypeByName(String typeName) {
        String query = "SELECT * FROM room_types WHERE type_name = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, typeName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Room room = new Room();
                room.setRoomTypeId(rs.getInt("room_type_id"));
                room.setTypeName(rs.getString("type_name"));
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                room.setCapacity(rs.getInt("capacity"));
                room.setDescription(rs.getString("description"));
                return room;
            }
        } catch (SQLException e) {
            System.err.println("Error getting room type: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    /**
     * Get room rate by type name
     */
    public double getRoomRate(String typeName) {
        String query = "SELECT rate_per_night FROM room_types WHERE type_name = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, typeName);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("rate_per_night");
            }
        } catch (SQLException e) {
            System.err.println("Error getting room rate: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return 0.0;
    }

    /**
     * Close resources
     */
    private void closeResources(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
