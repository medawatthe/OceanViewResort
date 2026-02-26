package com.oceanview.model;

/**
 * Room class - Represents room types
 */
public class Room {
    private int roomTypeId;
    private String typeName;
    private double ratePerNight;
    private int capacity;
    private String description;

    // Constructors
    public Room() {
    }

    public Room(int roomTypeId, String typeName, double ratePerNight, int capacity, String description) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.ratePerNight = ratePerNight;
        this.capacity = capacity;
        this.description = description;
    }

    // Getters and Setters
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(double ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Room{" +
                "typeName='" + typeName + '\'' +
                ", ratePerNight=" + ratePerNight +
                ", capacity=" + capacity +
                '}';
    }
}
