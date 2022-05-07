package com.auto;

public class InsuranceItem {
    private String name, firstName, lastName, carType;
    private long dateSubmitted; // unix

    public InsuranceItem(String firstName, String lastName, String carType, long dateSubmitted) {
        this.name = firstName + " " + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.carType = carType;
        this.dateSubmitted = dateSubmitted;
    }

    public InsuranceItem() { }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCarType() {
        return carType;
    }
    public void setCarType(String carType) {
        this.carType = carType;
    }

    public long getDateSubmitted() {
        return dateSubmitted;
    }
    public void setDateSubmitted(long dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
