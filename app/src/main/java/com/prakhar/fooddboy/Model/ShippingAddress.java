package com.prakhar.fooddboy.Model;

public class ShippingAddress {
    private String fullName;
    private String address;
    private String mobileNumber;
    private String pincode;
    private String landmark;

    public ShippingAddress() {
        // Default constructor required for Firebase
    }

    public ShippingAddress(String fullName, String address, String mobileNumber, String pincode, String landmark) {
        this.fullName = fullName;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.pincode = pincode;
        this.landmark = landmark;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    // Add getters and setters as needed
}
