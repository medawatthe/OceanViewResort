<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Room" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Reservation - Ocean View Resort</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h1>Add New Reservation</h1>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/addReservation" method="post" class="form">
            <div class="form-row">
                <div class="form-group">
                    <label for="guestName">Guest Name *</label>
                    <input type="text" id="guestName" name="guestName" required
                           placeholder="Enter guest full name" pattern="[A-Za-z\s]{2,100}">
                </div>

                <div class="form-group">
                    <label for="contactNumber">Contact Number *</label>
                    <input type="tel" id="contactNumber" name="contactNumber" required
                           placeholder="0771234567" pattern="\d{10}">
                    <small>10 digits only</small>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email"
                           placeholder="guest@example.com">
                </div>

                <div class="form-group">
                    <label for="numberOfGuests">Number of Guests *</label>
                    <input type="number" id="numberOfGuests" name="numberOfGuests" required
                           min="1" max="10" value="1">
                </div>
            </div>

            <div class="form-group">
                <label for="address">Address *</label>
                <textarea id="address" name="address" required rows="3"
                          placeholder="Enter full address"></textarea>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="roomType">Room Type *</label>
                    <select id="roomType" name="roomType" required>
                        <option value="">Select Room Type</option>
                        <%
                        List<Room> roomTypes = (List<Room>) request.getAttribute("roomTypes");
                        if (roomTypes != null) {
                            for (Room room : roomTypes) {
                        %>
                        <option value="<%= room.getTypeName() %>">
                            <%= room.getTypeName() %> - LKR <%= String.format("%.2f", room.getRatePerNight()) %>/night
                            (Capacity: <%= room.getCapacity() %>)
                        </option>
                        <%
                            }
                        }
                        %>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="checkInDate">Check-in Date *</label>
                    <input type="date" id="checkInDate" name="checkInDate" required
                           min="<%= LocalDate.now() %>">
                </div>

                <div class="form-group">
                    <label for="checkOutDate">Check-out Date *</label>
                    <input type="date" id="checkOutDate" name="checkOutDate" required
                           min="<%= LocalDate.now().plusDays(1) %>">
                </div>
            </div>

            <div class="form-group">
                <label for="specialRequests">Special Requests</label>
                <textarea id="specialRequests" name="specialRequests" rows="3"
                          placeholder="Any special requirements or requests"></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Create Reservation</button>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>

    <script>
        // Date validation
        document.getElementById('checkInDate').addEventListener('change', function() {
            var checkInDate = new Date(this.value);
            var checkOutDate = document.getElementById('checkOutDate');
            var minCheckOut = new Date(checkInDate);
            minCheckOut.setDate(minCheckOut.getDate() + 1);
            checkOutDate.min = minCheckOut.toISOString().split('T')[0];
        });
    </script>
</body>
</html>
