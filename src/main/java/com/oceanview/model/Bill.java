package com.oceanview.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Bill class - Represents a billing statement
 */
public class Bill {
    private String billNumber;
    private String reservationNumber;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private double roomRate;
    private double subtotal;
    private double tax;
    private double totalAmount;
    private LocalDateTime billDate;

    private static final double TAX_RATE = 0.10; // 10% tax

    // Constructors
    public Bill() {
    }

    public Bill(String billNumber, Reservation reservation, double roomRate) {
        this.billNumber = billNumber;
        this.reservationNumber = reservation.getReservationNumber();
        this.guestName = reservation.getGuestName();
        this.checkInDate = reservation.getCheckInDate();
        this.checkOutDate = reservation.getCheckOutDate();
        this.roomRate = roomRate;
        this.numberOfNights = reservation.calculateStayDuration();
        this.billDate = LocalDateTime.now();

        calculateAmounts();
    }

    // Business logic methods
    private void calculateAmounts() {
        this.subtotal = calculateSubtotal();
        this.tax = calculateTax();
        this.totalAmount = calculateTotal();
    }

    public double calculateSubtotal() {
        return numberOfNights * roomRate;
    }

    public double calculateTax() {
        return subtotal * TAX_RATE;
    }

    public double calculateTotal() {
        return subtotal + tax;
    }

    public String generateBillText() {
        StringBuilder bill = new StringBuilder();
        bill.append("========================================\n");
        bill.append("         OCEAN VIEW RESORT\n");
        bill.append("         Galle, Sri Lanka\n");
        bill.append("========================================\n\n");
        bill.append("Bill Number: ").append(billNumber).append("\n");
        bill.append("Date: ").append(billDate).append("\n\n");
        bill.append("Guest Name: ").append(guestName).append("\n");
        bill.append("Reservation: ").append(reservationNumber).append("\n\n");
        bill.append("Check-in:  ").append(checkInDate).append("\n");
        bill.append("Check-out: ").append(checkOutDate).append("\n");
        bill.append("Duration:  ").append(numberOfNights).append(" night(s)\n\n");
        bill.append("========================================\n");
        bill.append(String.format("Room Rate/Night: LKR %.2f\n", roomRate));
        bill.append(String.format("Subtotal:        LKR %.2f\n", subtotal));
        bill.append(String.format("Tax (10%%):       LKR %.2f\n", tax));
        bill.append("----------------------------------------\n");
        bill.append(String.format("Total Amount:    LKR %.2f\n", totalAmount));
        bill.append("========================================\n");
        bill.append("\nThank you for choosing Ocean View Resort!\n");

        return bill.toString();
    }

    // Getters and Setters
    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public double getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(double roomRate) {
        this.roomRate = roomRate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billNumber='" + billNumber + '\'' +
                ", guestName='" + guestName + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
