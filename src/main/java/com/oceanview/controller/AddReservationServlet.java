package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.service.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * AddReservationServlet - Handles adding new reservations
 */
@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
        roomDAO = new RoomDAO();
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

        // Get room types for dropdown
        List<Room> roomTypes = roomDAO.getAllRoomTypes();
        request.setAttribute("roomTypes", roomTypes);

        // Forward to add reservation page
        request.getRequestDispatcher("/addReservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get form parameters
            String guestName = request.getParameter("guestName");
            String address = request.getParameter("address");
            String contactNumber = request.getParameter("contactNumber");
            String email = request.getParameter("email");
            String roomType = request.getParameter("roomType");
            int numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
            LocalDate checkInDate = LocalDate.parse(request.getParameter("checkInDate"));
            LocalDate checkOutDate = LocalDate.parse(request.getParameter("checkOutDate"));
            String specialRequests = request.getParameter("specialRequests");

            // Generate reservation number
            String reservationNumber = reservationDAO.generateReservationNumber();

            // Create reservation object
            Reservation reservation = new Reservation();
            reservation.setReservationNumber(reservationNumber);
            reservation.setGuestName(guestName);
            reservation.setAddress(address);
            reservation.setContactNumber(contactNumber);
            reservation.setEmail(email);
            reservation.setRoomType(roomType);
            reservation.setNumberOfGuests(numberOfGuests);
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);
            reservation.setSpecialRequests(specialRequests);
            reservation.setStatus("Confirmed");
            reservation.setCreatedBy((String) session.getAttribute("userId"));

            // Validate reservation data
            String validationError = ValidationService.validateReservationData(reservation);
            if (validationError != null) {
                request.setAttribute("error", validationError);
                List<Room> roomTypes = roomDAO.getAllRoomTypes();
                request.setAttribute("roomTypes", roomTypes);
                request.getRequestDispatcher("/addReservation.jsp").forward(request, response);
                return;
            }

            // Save reservation
            boolean success = reservationDAO.addReservation(reservation);

            if (success) {
                session.setAttribute("success", "Reservation " + reservationNumber +
                                    " created successfully for " + guestName);
                response.sendRedirect(request.getContextPath() + "/viewReservation?number=" + reservationNumber);
            } else {
                request.setAttribute("error", "Failed to create reservation. Please try again.");
                List<Room> roomTypes = roomDAO.getAllRoomTypes();
                request.setAttribute("roomTypes", roomTypes);
                request.getRequestDispatcher("/addReservation.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            List<Room> roomTypes = roomDAO.getAllRoomTypes();
            request.setAttribute("roomTypes", roomTypes);
            request.getRequestDispatcher("/addReservation.jsp").forward(request, response);
        }
    }
}
