package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * ViewReservationServlet - Display reservation details
 */
@WebServlet("/viewReservation")
public class ViewReservationServlet extends HttpServlet {

    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
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

        String reservationNumber = request.getParameter("number");
        String searchName = request.getParameter("searchName");

        if (reservationNumber != null && !reservationNumber.trim().isEmpty()) {
            // Search by reservation number
            Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);
            if (reservation != null) {
                request.setAttribute("reservation", reservation);
            } else {
                request.setAttribute("error", "Reservation not found: " + reservationNumber);
            }
        } else if (searchName != null && !searchName.trim().isEmpty()) {
            // Search by guest name
            List<Reservation> reservations = reservationDAO.searchByGuestName(searchName);
            request.setAttribute("reservations", reservations);
        } else {
            // Show all reservations
            List<Reservation> reservations = reservationDAO.getAllReservations();
            request.setAttribute("reservations", reservations);
        }

        request.getRequestDispatcher("/viewReservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
