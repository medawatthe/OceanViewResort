<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar">
    <div class="nav-container">
        <div class="nav-logo">
            <a href="${pageContext.request.contextPath}/dashboard">Ocean View Resort</a>
        </div>

        <ul class="nav-menu">
            <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/addReservation">New Reservation</a></li>
            <li><a href="${pageContext.request.contextPath}/viewReservation">View Reservations</a></li>
            <li><a href="${pageContext.request.contextPath}/bill">Generate Bill</a></li>
            <li><a href="${pageContext.request.contextPath}/help.jsp">Help</a></li>
        </ul>

        <div class="nav-user">
            <span>Welcome, <%= session.getAttribute("fullName") %></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-logout">Logout</a>
        </div>
    </div>
</nav>
