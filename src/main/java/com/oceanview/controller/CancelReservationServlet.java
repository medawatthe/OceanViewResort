package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cancelReservation")
public class CancelReservationServlet extends HttpServlet {
    private ReservationDAO reservationDAO;

    @Override
    public void init() {
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter("reservationNumber");

        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            // Get the reservation
            Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);

            if (reservation != null) {
                // Update status to Cancelled
                reservation.setStatus("Cancelled");
                boolean updated = reservationDAO.updateReservation(reservation);

                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/dashboard?message=Reservation cancelled successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/dashboard?error=Failed to cancel reservation");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard?error=Reservation not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/dashboard?error=Error cancelling reservation");
        }
    }
}
