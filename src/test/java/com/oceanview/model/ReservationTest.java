package com.oceanview.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * JUnit Test for Reservation Model - Task C (TDD)
 */
public class ReservationTest {

    private Reservation reservation;

    @Before
    public void setUp() {
        reservation = new Reservation();
        reservation.setReservationNumber("RES-0001");
        reservation.setGuestName("John Doe");
        reservation.setAddress("123 Main St, Colombo");
        reservation.setContactNumber("0771234567");
        reservation.setEmail("john@example.com");
        reservation.setRoomType("Deluxe");
        reservation.setNumberOfGuests(2);
        reservation.setCheckInDate(LocalDate.now().plusDays(1));
        reservation.setCheckOutDate(LocalDate.now().plusDays(4));
        reservation.setStatus("Confirmed");
    }

    @Test
    public void testReservationCreation() {
        assertNotNull("Reservation should not be null", reservation);
        assertEquals("RES-0001", reservation.getReservationNumber());
        assertEquals("John Doe", reservation.getGuestName());
        assertEquals("Deluxe", reservation.getRoomType());
    }

    @Test
    public void testCalculateStayDuration() {
        int duration = reservation.calculateStayDuration();
        assertEquals("Stay duration should be 3 nights", 3, duration);
    }

    @Test
    public void testValidDates() {
        boolean isValid = reservation.validateDates();
        assertTrue("Future dates should be valid", isValid);
    }

    @Test
    public void testInvalidPastCheckInDate() {
        reservation.setCheckInDate(LocalDate.now().minusDays(1));
        boolean isValid = reservation.validateDates();
        assertFalse("Past check-in date should be invalid", isValid);
    }

    @Test
    public void testInvalidCheckOutBeforeCheckIn() {
        reservation.setCheckInDate(LocalDate.now().plusDays(5));
        reservation.setCheckOutDate(LocalDate.now().plusDays(3));
        boolean isValid = reservation.validateDates();
        assertFalse("Check-out before check-in should be invalid", isValid);
    }

    @Test
    public void testSameDayCheckInCheckOut() {
        LocalDate sameDate = LocalDate.now().plusDays(1);
        reservation.setCheckInDate(sameDate);
        reservation.setCheckOutDate(sameDate);
        boolean isValid = reservation.validateDates();
        assertFalse("Same day check-in and check-out should be invalid", isValid);
    }

    @Test
    public void testStatusUpdate() {
        assertEquals("Initial status should be Confirmed", "Confirmed", reservation.getStatus());
        reservation.setStatus("Cancelled");
        assertEquals("Status should update to Cancelled", "Cancelled", reservation.getStatus());
    }

    @Test
    public void testContactNumberValidation() {
        String validNumber = "0771234567";
        reservation.setContactNumber(validNumber);
        assertEquals(validNumber, reservation.getContactNumber());
        assertTrue("Contact number should be 10 digits",
                   reservation.getContactNumber().length() == 10);
    }

    @Test
    public void testNumberOfGuests() {
        reservation.setNumberOfGuests(4);
        assertEquals("Number of guests should be 4", 4, reservation.getNumberOfGuests());
        assertTrue("Number of guests should be positive", reservation.getNumberOfGuests() > 0);
    }

    @Test
    public void testGuestNameNotEmpty() {
        assertNotNull("Guest name should not be null", reservation.getGuestName());
        assertFalse("Guest name should not be empty", reservation.getGuestName().isEmpty());
    }
}
