package com.example.sridatta.timely.objects;

/**
 * Created by sridatta on 28-11-2017.
 */

public class Representative {


    private String firstName;
    private String lastName;

    private String phoneNumber;
    private String emailID;

    private String degree;
    private String department;
    private String batchYear;
    private String section;


    public Representative() {
    }

    public Representative(String firstName, String lastName, String phoneNumber, String emailID, String degree, String department, String batchYear, String section) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.degree = degree;
        this.department = department;
        this.batchYear = batchYear;
        this.section = section;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }



}
