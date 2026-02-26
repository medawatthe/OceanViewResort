package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservationDAO - Data Access Object for Reservation operations
 */
public class ReservationDAO {

    private DatabaseManager dbManager;

    public ReservationDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * Generate unique reservation number
     */
    public String generateReservationNumber() {
        String query = "SELECT reservation_number FROM reservations ORDER BY created_at DESC LIMIT 1";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastNumber = rs.getString("reservation_number");
                int number = Integer.parseInt(lastNumber.substring(4)) + 1;
                return String.format("RES-%04d", number);
            } else {
                return "RES-0001";
            }
        } catch (SQLException e) {
            System.err.println("Error generating reservation number: " + e.getMessage());
            e.printStackTrace();
            return "RES-0001";
        } finally {
            closeResources(rs, stmt);
        }
    }

    /**
     * Add new reservation
     */
    public boolean addReservation(Reservation reservation) {
        String query = "INSERT INTO reservations (reservation_number, guest_name, address, " +
                      "contact_number, email, room_type, number_of_guests, check_in_date, " +
                      "check_out_date, status, special_requests, created_by) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reservation.getReservationNumber());
            pstmt.setString(2, reservation.getGuestName());
            pstmt.setString(3, reservation.getAddress());
            pstmt.setString(4, reservation.getContactNumber());
            pstmt.setString(5, reservation.getEmail());
            pstmt.setString(6, reservation.getRoomType());
            pstmt.setInt(7, reservation.getNumberOfGuests());
            pstmt.setDate(8, Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(9, Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(10, reservation.getStatus());
            pstmt.setString(11, reservation.getSpecialRequests());
            pstmt.setString(12, reservation.getCreatedBy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt);
        }
    }

    /**
     * Get reservation by number
     */
    public Reservation getReservationByNumber(String reservationNumber) {
        String query = "SELECT * FROM reservations WHERE reservation_number = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reservationNumber);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractReservationFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return null;
    }

    /**
     * Get all reservations
     */
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations ORDER BY created_at DESC";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                reservations.add(extractReservationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all reservations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt);
        }

        return reservations;
    }

    /**
     * Search reservations by guest name
     */
    public List<Reservation> searchByGuestName(String guestName) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE LOWER(guest_name) LIKE LOWER(?) " +
                      "ORDER BY created_at DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + guestName + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(extractReservationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching reservations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return reservations;
    }

    /**
     * Update reservation
     */
    public boolean updateReservation(Reservation reservation) {
        String query = "UPDATE reservations SET guest_name = ?, address = ?, " +
                      "contact_number = ?, email = ?, room_type = ?, number_of_guests = ?, " +
                      "check_in_date = ?, check_out_date = ?, status = ?, special_requests = ? " +
                      "WHERE reservation_number = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reservation.getGuestName());
            pstmt.setString(2, reservation.getAddress());
            pstmt.setString(3, reservation.getContactNumber());
            pstmt.setString(4, reservation.getEmail());
            pstmt.setString(5, reservation.getRoomType());
            pstmt.setInt(6, reservation.getNumberOfGuests());
            pstmt.setDate(7, Date.valueOf(reservation.getCheckInDate()));
            pstmt.setDate(8, Date.valueOf(reservation.getCheckOutDate()));
            pstmt.setString(9, reservation.getStatus());
            pstmt.setString(10, reservation.getSpecialRequests());
            pstmt.setString(11, reservation.getReservationNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt);
        }
    }

    /**
     * Delete/Cancel reservation
     */
    public boolean cancelReservation(String reservationNumber) {
        String query = "UPDATE reservations SET status = 'Cancelled' WHERE reservation_number = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reservationNumber);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt);
        }
    }

    /**
     * Get reservations by date range
     */
    public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE check_in_date BETWEEN ? AND ? " +
                      "ORDER BY check_in_date";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(extractReservationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting reservations by date: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return reservations;
    }

    /**
     * Get reservation count by status
     */
    public int getReservationCountByStatus(String status) {
        String query = "SELECT COUNT(*) FROM reservations WHERE status = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting count: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt);
        }

        return 0;
    }

    /**
     * Extract Reservation object from ResultSet
     */
    private Reservation extractReservationFromResultSet(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(rs.getString("reservation_number"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNumber(rs.getString("contact_number"));
        reservation.setEmail(rs.getString("email"));
        reservation.setRoomType(rs.getString("room_type"));
        reservation.setNumberOfGuests(rs.getInt("number_of_guests"));
        reservation.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        reservation.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        reservation.setStatus(rs.getString("status"));
        reservation.setSpecialRequests(rs.getString("special_requests"));
        reservation.setCreatedBy(rs.getString("created_by"));

        Timestamp createdTimestamp = rs.getTimestamp("created_at");
        if (createdTimestamp != null) {
            reservation.setCreatedAt(createdTimestamp.toLocalDateTime());
        }

        Timestamp updatedTimestamp = rs.getTimestamp("updated_at");
        if (updatedTimestamp != null) {
            reservation.setUpdatedAt(updatedTimestamp.toLocalDateTime());
        }

        return reservation;
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
