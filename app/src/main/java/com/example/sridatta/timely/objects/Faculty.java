package com.example.sridatta.timely.objects;

/**
 * Created by sridatta on 28-11-2017.
 */

public class Faculty {

    private String firstName;
    private String lastName;
    private String department;
    private String phoneNumber;
    private String emailID;
    private String designation;
    private String additionalResponsibility;
    private String photoURL;

    private lectureSlot[][]  lectureSlot;



    public Faculty() {

        this.firstName = "first";
        this.lastName = "last";
        this.department = "department";
        this.phoneNumber = "phoneNumber";
        this.emailID = "emailID";
        this.designation = "designation";
        this.additionalResponsibility = "Responsibility";
        this.photoURL = "photoURL";
        this.lectureSlot = new lectureSlot[7][7];

    }

    public Faculty(String firstName, String lastName, String department, String phoneNumber, String emailID, String designation, String additionalResponsibility, String photoURL, lectureSlot[][]  lectureSlot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.emailID = emailID;
        this.designation = designation;
        this.additionalResponsibility = additionalResponsibility;
        this.photoURL = photoURL;
        this.lectureSlot = lectureSlot;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAdditionalResponsibility() {
        return additionalResponsibility;
    }

    public void setAdditionalResponsibility(String additionalResponsibility) {
        this.additionalResponsibility = additionalResponsibility;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public com.example.sridatta.timely.objects.lectureSlot[][] getLectureSlot() {
        return lectureSlot;
    }

    public void setLectureSlot(com.example.sridatta.timely.objects.lectureSlot[][] lectureSlot) {
        this.lectureSlot = lectureSlot;
    }
}
