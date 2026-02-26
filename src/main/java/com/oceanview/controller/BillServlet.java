package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Bill;
import com.oceanview.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import com.oceanview.util.DatabaseManager;

/**
 * BillServlet - Handles bill generation and display
 */
@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;
    private DatabaseManager dbManager;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
        roomDAO = new RoomDAO();
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String reservationNumber = request.getParameter("reservationNumber");

        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            request.setAttribute("error", "Please provide a reservation number");
            request.getRequestDispatcher("/bill.jsp").forward(request, response);
            return;
        }

        // Get reservation details
        Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);

        if (reservation == null) {
            request.setAttribute("error", "Reservation not found: " + reservationNumber);
            request.getRequestDispatcher("/bill.jsp").forward(request, response);
            return;
        }

        // Get room rate
        double roomRate = roomDAO.getRoomRate(reservation.getRoomType());

        // Generate bill number
        String billNumber = generateBillNumber();

        // Create bill
        Bill bill = new Bill(billNumber, reservation, roomRate);

        // Save bill to database
        saveBill(bill);

        // Set attributes and forward
        request.setAttribute("bill", bill);
        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/bill.jsp").forward(request, response);
    }

    /**
     * Generate unique bill number
     */
    private String generateBillNumber() {
        String query = "SELECT bill_number FROM bills ORDER BY bill_date DESC LIMIT 1";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastNumber = rs.getString("bill_number");
                int number = Integer.parseInt(lastNumber.substring(5)) + 1;
                return String.format("BILL-%04d", number);
            } else {
                return "BILL-0001";
            }
        } catch (SQLException e) {
            System.err.println("Error generating bill number: " + e.getMessage());
            return "BILL-0001";
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save bill to database
     */
    private boolean saveBill(Bill bill) {
        String query = "INSERT INTO bills (bill_number, reservation_number, guest_name, " +
                      "check_in_date, check_out_date, number_of_nights, room_rate, " +
                      "subtotal, tax, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            // Check if bill already exists
            if (billExists(bill.getReservationNumber())) {
                return true; // Bill already generated
            }

            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, bill.getBillNumber());
            pstmt.setString(2, bill.getReservationNumber());
            pstmt.setString(3, bill.getGuestName());
            pstmt.setDate(4, Date.valueOf(bill.getCheckInDate()));
            pstmt.setDate(5, Date.valueOf(bill.getCheckOutDate()));
            pstmt.setInt(6, bill.getNumberOfNights());
            pstmt.setDouble(7, bill.getRoomRate());
            pstmt.setDouble(8, bill.getSubtotal());
            pstmt.setDouble(9, bill.getTax());
            pstmt.setDouble(10, bill.getTotalAmount());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saving bill: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if bill exists for reservation
     */
    private boolean billExists(String reservationNumber) {
        String query = "SELECT COUNT(*) FROM bills WHERE reservation_number = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = dbManager.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reservationNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking bill: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
