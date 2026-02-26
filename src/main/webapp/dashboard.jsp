<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Ocean View Resort</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h1>Dashboard</h1>

        <% if (session.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                <%= session.getAttribute("success") %>
                <% session.removeAttribute("success"); %>
            </div>
        <% } %>

        <div class="stats-container">
            <div class="stat-card">
                <h3>Confirmed Reservations</h3>
                <p class="stat-number"><%= request.getAttribute("confirmedCount") %></p>
            </div>

            <div class="stat-card">
                <h3>Cancelled Reservations</h3>
                <p class="stat-number"><%= request.getAttribute("cancelledCount") %></p>
            </div>
        </div>

        <div class="quick-actions">
            <h2>Quick Actions</h2>
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/addReservation" class="btn btn-primary">
                    New Reservation
                </a>
                <a href="${pageContext.request.contextPath}/viewReservation" class="btn btn-secondary">
                    View Reservations
                </a>
                <a href="${pageContext.request.contextPath}/bill" class="btn btn-success">
                    Generate Bill
                </a>
            </div>
        </div>

        <div class="room-types">
            <h2>Available Room Types</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Room Type</th>
                        <th>Rate per Night (LKR)</th>
                        <th>Capacity</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<Room> roomTypes = (List<Room>) request.getAttribute("roomTypes");
                    if (roomTypes != null) {
                        for (Room room : roomTypes) {
                    %>
                    <tr>
                        <td><%= room.getTypeName() %></td>
                        <td><%= String.format("%.2f", room.getRatePerNight()) %></td>
                        <td><%= room.getCapacity() %> guests</td>
                        <td><%= room.getDescription() %></td>
                    </tr>
                    <%
                        }
                    }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
