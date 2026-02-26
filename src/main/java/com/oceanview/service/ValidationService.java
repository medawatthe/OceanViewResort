package com.oceanview.service;

import com.oceanview.model.Reservation;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * ValidationService - Input validation for the system
 */
public class ValidationService {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\d{10}$"); // Sri Lankan format: 10 digits

    private static final Pattern NAME_PATTERN =
        Pattern.compile("^[a-zA-Z\\s]{2,100}$");

    /**
     * Validate email format
     */
    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate phone number (Sri Lankan format)
     */
    public static boolean validatePhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleaned = phone.replaceAll("[\\s-]", "");
        return PHONE_PATTERN.matcher(cleaned).matches();
    }

    /**
     * Validate name
     */
    public static boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate date is not in the past
     */
    public static boolean validateDateNotPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(LocalDate.now());
    }

    /**
     * Validate date range (checkout after checkin)
     */
    public static boolean validateDateRange(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false;
        }
        return checkOut.isAfter(checkIn);
    }

    /**
     * Validate number of guests
     */
    public static boolean validateNumberOfGuests(int guests) {
        return guests > 0 && guests <= 10;
    }

    /**
     * Validate reservation data
     */
    public static String validateReservationData(Reservation reservation) {
        StringBuilder errors = new StringBuilder();

        // Validate guest name
        if (!validateName(reservation.getGuestName())) {
            errors.append("Invalid guest name. ");
        }

        // Validate contact number
        if (!validatePhoneNumber(reservation.getContactNumber())) {
            errors.append("Invalid contact number (must be 10 digits). ");
        }

        // Validate email if provided
        if (reservation.getEmail() != null && !reservation.getEmail().isEmpty()) {
            if (!validateEmail(reservation.getEmail())) {
                errors.append("Invalid email format. ");
            }
        }

        // Validate check-in date
        if (!validateDateNotPast(reservation.getCheckInDate())) {
            errors.append("Check-in date cannot be in the past. ");
        }

        // Validate date range
        if (!validateDateRange(reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            errors.append("Check-out date must be after check-in date. ");
        }

        // Validate number of guests
        if (!validateNumberOfGuests(reservation.getNumberOfGuests())) {
            errors.append("Number of guests must be between 1 and 10. ");
        }

        // Validate address
        if (reservation.getAddress() == null || reservation.getAddress().trim().length() < 5) {
            errors.append("Address must be at least 5 characters. ");
        }

        return errors.length() == 0 ? null : errors.toString();
    }

    /**
     * Validate string not empty
     */
    public static boolean validateNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate positive number
     */
    public static boolean validatePositiveNumber(double number) {
        return number > 0;
    }
}
