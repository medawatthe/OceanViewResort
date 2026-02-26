<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.model.Bill" %>
<%@ page import="com.oceanview.model.Reservation" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill - Ocean View Resort</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        @media print {
            .navbar, .form, .btn { display: none; }
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h1>Generate Bill</h1>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <!-- Bill Search Form -->
        <form action="${pageContext.request.contextPath}/bill" method="get" class="form">
            <div class="form-group">
                <label for="reservationNumber">Reservation Number *</label>
                <input type="text" id="reservationNumber" name="reservationNumber" required
                       placeholder="Enter reservation number (e.g., RES-0001)">
            </div>

            <button type="submit" class="btn btn-primary">Generate Bill</button>
        </form>

        <!-- Bill Display -->
        <%
        Bill bill = (Bill) request.getAttribute("bill");
        Reservation reservation = (Reservation) request.getAttribute("reservation");
        if (bill != null) {
        %>
            <div class="bill-container" style="margin-top: 2rem;">
                <div class="bill-header">
                    <h2 style="margin-bottom: 0.5rem;">OCEAN VIEW RESORT</h2>
                    <p>Galle, Sri Lanka</p>
                    <p>Tel: +94 91 234 5678</p>
                    <hr style="margin-top: 1rem;">
                </div>

                <div class="bill-details">
                    <div class="bill-row">
                        <span>Bill Number:</span>
                        <span><strong><%= bill.getBillNumber() %></strong></span>
                    </div>

                    <div class="bill-row">
                        <span>Date:</span>
                        <span><%= bill.getBillDate() %></span>
                    </div>

                    <div class="bill-row">
                        <span>Reservation Number:</span>
                        <span><%= bill.getReservationNumber() %></span>
                    </div>

                    <hr>

                    <div class="bill-row">
                        <span>Guest Name:</span>
                        <span><strong><%= bill.getGuestName() %></strong></span>
                    </div>

                    <div class="bill-row">
                        <span>Check-in Date:</span>
                        <span><%= bill.getCheckInDate() %></span>
                    </div>

                    <div class="bill-row">
                        <span>Check-out Date:</span>
                        <span><%= bill.getCheckOutDate() %></span>
                    </div>

                    <div class="bill-row">
                        <span>Number of Nights:</span>
                        <span><%= bill.getNumberOfNights() %></span>
                    </div>

                    <% if (reservation != null) { %>
                    <div class="bill-row">
                        <span>Room Type:</span>
                        <span><%= reservation.getRoomType() %></span>
                    </div>
                    <% } %>

                    <hr>

                    <div class="bill-row">
                        <span>Room Rate per Night:</span>
                        <span>LKR <%= String.format("%.2f", bill.getRoomRate()) %></span>
                    </div>

                    <div class="bill-row">
                        <span>Subtotal:</span>
                        <span>LKR <%= String.format("%.2f", bill.getSubtotal()) %></span>
                    </div>

                    <div class="bill-row">
                        <span>Tax (10%):</span>
                        <span>LKR <%= String.format("%.2f", bill.getTax()) %></span>
                    </div>

                    <hr style="border: 2px solid #333;">

                    <div class="bill-total">
                        <div class="bill-row">
                            <span>TOTAL AMOUNT:</span>
                            <span>LKR <%= String.format("%.2f", bill.getTotalAmount()) %></span>
                        </div>
                    </div>

                    <hr>

                    <p style="text-align: center; margin-top: 2rem; font-style: italic;">
                        Thank you for choosing Ocean View Resort!<br>
                        We hope to see you again soon.
                    </p>
                </div>
            </div>

            <div class="form-actions" style="margin-top: 2rem; justify-content: center;">
                <button onclick="window.print()" class="btn btn-primary">Print Bill</button>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Back to Dashboard</a>
            </div>
        <%
        }
        %>
    </div>
</body>
</html>
