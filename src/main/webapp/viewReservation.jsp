<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Reservation" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Reservations - Ocean View Resort</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h1>View Reservations</h1>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <!-- Search Form -->
        <form action="${pageContext.request.contextPath}/viewReservation" method="get" class="form">
            <div class="form-row">
                <div class="form-group">
                    <label for="number">Reservation Number</label>
                    <input type="text" id="number" name="number" placeholder="RES-0001">
                </div>

                <div class="form-group">
                    <label for="searchName">Guest Name</label>
                    <input type="text" id="searchName" name="searchName" placeholder="Search by name">
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Search</button>
        </form>

        <!-- Single Reservation Details -->
        <%
        Reservation reservation = (Reservation) request.getAttribute("reservation");
        if (reservation != null) {
        %>
            <div class="reservation-details">
                <h2>Reservation Details</h2>

                <div class="detail-row">
                    <div class="detail-label">Reservation Number:</div>
                    <div class="detail-value"><%= reservation.getReservationNumber() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Guest Name:</div>
                    <div class="detail-value"><%= reservation.getGuestName() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Contact Number:</div>
                    <div class="detail-value"><%= reservation.getContactNumber() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Email:</div>
                    <div class="detail-value"><%= reservation.getEmail() != null ? reservation.getEmail() : "N/A" %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Address:</div>
                    <div class="detail-value"><%= reservation.getAddress() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Room Type:</div>
                    <div class="detail-value"><%= reservation.getRoomType() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Number of Guests:</div>
                    <div class="detail-value"><%= reservation.getNumberOfGuests() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Check-in Date:</div>
                    <div class="detail-value"><%= reservation.getCheckInDate() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Check-out Date:</div>
                    <div class="detail-value"><%= reservation.getCheckOutDate() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Duration:</div>
                    <div class="detail-value"><%= reservation.calculateStayDuration() %> night(s)</div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Status:</div>
                    <div class="detail-value"><%= reservation.getStatus() %></div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Special Requests:</div>
                    <div class="detail-value"><%= reservation.getSpecialRequests() != null ? reservation.getSpecialRequests() : "None" %></div>
                </div>

                 </div>
        <% if ("Confirmed".equals(reservation.getStatus())) { %>
                    <form action="${pageContext.request.contextPath}/cancelReservation" method="post" style="display: inline; margin-left: 10px;">
                        <input type="hidden" name="reservationNumber" value="<%= reservation.getReservationNumber() %>">
                        <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to cancel this reservation?')">Cancel Reservation</button>
                    </form>
                    <% } %>
       
                <div class="form-actions" style="margin-top: 1rem;">
                    <a href="${pageContext.request.contextPath}/bill?reservationNumber=<%= reservation.getReservationNumber() %>"
                       class="btn btn-success">Generate Bill</a>
                </div>
            </div>
        <%
        }
        %>

        <!-- List of Reservations -->
        <%
        List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
        if (reservations != null && !reservations.isEmpty()) {
        %>
            <h2>All Reservations (<%= reservations.size() %>)</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Reservation #</th>
                        <th>Guest Name</th>
                        <th>Contact</th>
                        <th>Room Type</th>
                        <th>Check-in</th>
                        <th>Check-out</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for (Reservation res : reservations) {
                    %>
                    <tr>
                        <td><%= res.getReservationNumber() %></td>
                        <td><%= res.getGuestName() %></td>
                        <td><%= res.getContactNumber() %></td>
                        <td><%= res.getRoomType() %></td>
                        <td><%= res.getCheckInDate() %></td>
                        <td><%= res.getCheckOutDate() %></td>
                        <td><%= res.getStatus() %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/viewReservation?number=<%= res.getReservationNumber() %>"
                               class="btn btn-primary" style="padding: 0.5rem 1rem;">View</a>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        <%
        } else if (request.getParameter("number") == null && request.getParameter("searchName") == null) {
        %>
            <p style="text-align: center; color: #666; margin-top: 2rem;">
                Use the search form above to find reservations or leave it empty to view all.
            </p>
        <%
        }
        %>
    </div>
</body>
</html>
