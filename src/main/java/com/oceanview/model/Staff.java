package com.oceanview.model;

/**
 * Staff class - Represents hotel staff members
 */
public class Staff extends User {

    public Staff() {
        super();
    }

    public Staff(String userId, String username, String password, String fullName, String email) {
        super(userId, username, password, "STAFF", fullName, email);
    }

    @Override
    public boolean hasPermission(String permission) {
        // Staff permissions
        switch (permission) {
            case "ADD_RESERVATION":
            case "VIEW_RESERVATION":
            case "UPDATE_RESERVATION":
            case "GENERATE_BILL":
            case "VIEW_REPORTS":
                return true;
            default:
                return false;
        }
    }
}
