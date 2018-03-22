package com.example.sridatta.timely.objects;

/**
 * Created by Pratyush Srivastava on 08-02-2018.
 */

public class SwapRequest extends LectureSlot {
    private String userSenderId;
    private String requestDocumentId;
    private String timeOfRequest;

    public SwapRequest(String day, String hour, String courseCode, String courseName, String degree,
                       String department, String semester, String section, String block, String floor,
                       String roomNo, String assistingFaculty, String userSenderId,String requestDocumentId,String timeOfRequest,String dateOfRequest) {
        super(day, hour, courseCode, courseName, degree, department, semester, section, block, floor, roomNo, assistingFaculty,"#FF0000");
        this.userSenderId = userSenderId;
        this.requestDocumentId=requestDocumentId;
        this.timeOfRequest=timeOfRequest;
        this.dateOfRequest=dateOfRequest;
    }
    public String getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(String timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    private String dateOfRequest;



    public String getUserSenderId() {
        return userSenderId;
    }

    public void setUserSenderId(String userSenderId) {
        this.userSenderId = userSenderId;
    }

    public String getRequestDocumentId() {
        return requestDocumentId;
    }

    public void setRequestDocumentId(String requestDocumentId) {
        this.requestDocumentId = requestDocumentId;
    }

    public SwapRequest() {
        super();
        this.userSenderId="default user id";
        this.requestDocumentId=" default document id";
        this.dateOfRequest="default date";
        this.timeOfRequest="default time";
    }
}
