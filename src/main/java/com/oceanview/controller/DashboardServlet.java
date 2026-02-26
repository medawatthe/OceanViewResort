package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * DashboardServlet - Main dashboard after login
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

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

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get statistics for dashboard
        int confirmedReservations = reservationDAO.getReservationCountByStatus("Confirmed");
        int cancelledReservations = reservationDAO.getReservationCountByStatus("Cancelled");
        List<Room> roomTypes = roomDAO.getAllRoomTypes();

        // Set attributes
        request.setAttribute("confirmedCount", confirmedReservations);
        request.setAttribute("cancelledCount", cancelledReservations);
        request.setAttribute("roomTypes", roomTypes);

        // Forward to dashboard JSP
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
