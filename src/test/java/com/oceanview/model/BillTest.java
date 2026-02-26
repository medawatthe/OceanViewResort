package com.oceanview.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * JUnit Test for Bill Model - Task C (TDD)
 */
public class BillTest {

    private Bill bill;

    @Before
    public void setUp() {
        bill = new Bill();
        bill.setBillNumber("BILL-0001");
        bill.setReservationNumber("RES-0001");
        bill.setGuestName("John Doe");
        bill.setCheckInDate(LocalDate.of(2026, 3, 1));
        bill.setCheckOutDate(LocalDate.of(2026, 3, 4));
        bill.setNumberOfNights(3);
        bill.setRoomRate(8000.00);
    }

    @Test
    public void testBillCreation() {
        assertNotNull("Bill should not be null", bill);
        assertEquals("BILL-0001", bill.getBillNumber());
        assertEquals("RES-0001", bill.getReservationNumber());
        assertEquals("John Doe", bill.getGuestName());
    }

    @Test
    public void testCalculateSubtotal() {
        bill.calculateTotal();
        double expectedSubtotal = 3 * 8000.00; // 24000.00
        assertEquals("Subtotal should be number of nights * room rate",
                     expectedSubtotal, bill.getSubtotal(), 0.01);
    }

    @Test
    public void testCalculateTax() {
        bill.calculateTotal();
        double expectedTax = 24000.00 * 0.12; // 2880.00 (12% tax)
        assertEquals("Tax should be 12% of subtotal",
                     expectedTax, bill.getTax(), 0.01);
    }

    @Test
    public void testCalculateTotalAmount() {
        bill.calculateTotal();
        double expectedTotal = 24000.00 + (24000.00 * 0.12); // 26880.00
        assertEquals("Total should be subtotal + tax",
                     expectedTotal, bill.getTotalAmount(), 0.01);
    }

    @Test
    public void testSingleNightBill() {
        bill.setNumberOfNights(1);
        bill.setRoomRate(5000.00);
        bill.calculateTotal();

        assertEquals(5000.00, bill.getSubtotal(), 0.01);
        assertEquals(600.00, bill.getTax(), 0.01); // 12% of 5000
        assertEquals(5600.00, bill.getTotalAmount(), 0.01);
    }

    @Test
    public void testLongStayBill() {
        bill.setNumberOfNights(7);
        bill.setRoomRate(12000.00);
        bill.calculateTotal();

        assertEquals(84000.00, bill.getSubtotal(), 0.01);
        assertEquals(10080.00, bill.getTax(), 0.01);
        assertEquals(94080.00, bill.getTotalAmount(), 0.01);
    }

    @Test
    public void testRoomRatePositive() {
        assertTrue("Room rate should be positive", bill.getRoomRate() > 0);
    }

    @Test
    public void testNumberOfNightsPositive() {
        assertTrue("Number of nights should be positive", bill.getNumberOfNights() > 0);
    }

    @Test
    public void testZeroNightsInvalidBill() {
        bill.setNumberOfNights(0);
        bill.calculateTotal();
        assertEquals("Subtotal should be 0 for zero nights", 0.00, bill.getSubtotal(), 0.01);
    }
}
