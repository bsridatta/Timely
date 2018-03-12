package com.example.sridatta.timely.objects;

/**
 * Created by Pratyush Srivastava on 08-02-2018.
 */

public class SwapRequest extends LectureSlot {
    private String userSenderId;
    private String requestDocumentId;

    public SwapRequest(String day, String hour, String courseCode, String courseName, String degree,
                       String department, String semester, String section, String block, String floor,
                       String roomNo, String assistingFaculty, String userSenderId,String requestDocumentId) {
        super(day, hour, courseCode, courseName, degree, department, semester, section, block, floor, roomNo, assistingFaculty);
        this.userSenderId = userSenderId;
        this.requestDocumentId=requestDocumentId;
    }

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
    }
}
