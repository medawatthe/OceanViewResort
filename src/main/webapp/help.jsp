<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help - Ocean View Resort</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container">
        <h1>Help & User Guide</h1>

        <div class="help-section">
            <h2>1. Getting Started</h2>
            <p>Welcome to the Ocean View Resort Reservation Management System. This system allows you to manage room reservations, view guest details, and generate bills efficiently.</p>

            <h3>Login Credentials:</h3>
            <ul>
                <li><strong>Administrator:</strong> Username: admin | Password: admin123</li>
                <li><strong>Staff:</strong> Username: staff1 | Password: staff123</li>
            </ul>
        </div>

        <div class="help-section">
            <h2>2. Adding a New Reservation</h2>
            <ol>
                <li>Click on "New Reservation" from the dashboard or navigation menu</li>
                <li>Fill in all required fields marked with (*):
                    <ul>
                        <li><strong>Guest Name:</strong> Full name of the guest (letters and spaces only)</li>
                        <li><strong>Contact Number:</strong> 10-digit phone number</li>
                        <li><strong>Email:</strong> Valid email address (optional)</li>
                        <li><strong>Address:</strong> Complete address</li>
                        <li><strong>Room Type:</strong> Select from available room types</li>
                        <li><strong>Number of Guests:</strong> Between 1 and 10</li>
                        <li><strong>Check-in/Check-out Dates:</strong> Valid future dates</li>
                    </ul>
                </li>
                <li>Add any special requests (optional)</li>
                <li>Click "Create Reservation"</li>
                <li>The system will generate a unique reservation number (format: RES-XXXX)</li>
            </ol>
        </div>

        <div class="help-section">
            <h2>3. Viewing Reservations</h2>
            <p>There are three ways to view reservations:</p>
            <ol>
                <li><strong>View All:</strong> Leave search fields empty and submit to view all reservations</li>
                <li><strong>By Reservation Number:</strong> Enter the reservation number (e.g., RES-0001)</li>
                <li><strong>By Guest Name:</strong> Enter guest name (partial names work)</li>
            </ol>
        </div>

        <div class="help-section">
            <h2>4. Generating Bills</h2>
            <ol>
                <li>Click on "Generate Bill" from the navigation menu</li>
                <li>Enter the reservation number</li>
                <li>Click "Generate Bill"</li>
                <li>The system will:
                    <ul>
                        <li>Calculate the number of nights</li>
                        <li>Apply the room rate</li>
                        <li>Calculate tax (10%)</li>
                        <li>Display the total amount</li>
                    </ul>
                </li>
                <li>You can print the bill by clicking the "Print Bill" button</li>
            </ol>
        </div>

        <div class="help-section">
            <h2>5. Room Types and Rates</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Room Type</th>
                        <th>Rate per Night</th>
                        <th>Capacity</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Standard</td>
                        <td>LKR 5,000.00</td>
                        <td>2 guests</td>
                        <td>Comfortable room with basic amenities</td>
                    </tr>
                    <tr>
                        <td>Deluxe</td>
                        <td>LKR 8,000.00</td>
                        <td>3 guests</td>
                        <td>Spacious room with ocean view and premium amenities</td>
                    </tr>
                    <tr>
                        <td>Suite</td>
                        <td>LKR 12,000.00</td>
                        <td>4 guests</td>
                        <td>Luxury suite with living area and panoramic ocean view</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="help-section">
            <h2>6. Important Notes</h2>
            <ul>
                <li>All reservations require a valid check-in and check-out date</li>
                <li>Check-in dates cannot be in the past</li>
                <li>Check-out date must be after check-in date</li>
                <li>Contact numbers must be exactly 10 digits</li>
                <li>Session timeout is 30 minutes of inactivity</li>
                <li>Bills include 10% tax on the room charges</li>
            </ul>
        </div>

        <div class="help-section">
            <h2>7. Troubleshooting</h2>
            <h3>Common Issues:</h3>
            <ul>
                <li><strong>Cannot login:</strong> Verify username and password are correct</li>
                <li><strong>Session expired:</strong> Login again to continue</li>
                <li><strong>Validation errors:</strong> Ensure all required fields are filled correctly</li>
                <li><strong>Reservation not found:</strong> Double-check the reservation number</li>
            </ul>
        </div>

        <div class="help-section">
            <h2>8. Contact Support</h2>
            <p>For technical support or assistance, please contact:</p>
            <ul>
                <li><strong>Email:</strong> support@oceanviewresort.com</li>
                <li><strong>Phone:</strong> +94 91 234 5678</li>
                <li><strong>Address:</strong> Ocean View Resort, Galle, Sri Lanka</li>
            </ul>
        </div>

        <div class="form-actions" style="margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
