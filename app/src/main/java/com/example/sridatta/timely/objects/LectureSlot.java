package com.example.sridatta.timely.objects;

/**
 * Created by sridatta on 28-11-2017.
 */

public class LectureSlot {

    private String day ;
    private String hour;
    private String courseCode;
    private String courseName;
    private String degree;
    private String department;
    private String semester;
    private String section;
    private String block;
    private String floor;
    private String roomNo;
    private String assistingFaculty;

    public LectureSlot() {

        this.courseCode = "15CSE000";
        this.courseName = "Course Name";
        this.degree = "B.Tech";
        this.department = "CSE";
        this.semester = "0";
        this.section = "O";
        this.block = "AB3";
        this.floor = "0";
        this.roomNo = "A000";
        this.assistingFaculty = "Assisting Faculty";
        this.day="Mon";
        this.hour="1";

    }


    public LectureSlot(String day,String hour) {

        this.day=day;
        this.hour=hour;

        this.courseCode = "15CSE000";
        this.courseName = "Course Name";
        this.degree = "B.Tech";
        this.department = "CSE";
        this.semester = "0";
        this.section = "O";
        this.block = "AB3";
        this.floor = "0";
        this.roomNo = "A000";
        this.assistingFaculty = "Assisting Faculty";


    }

    public LectureSlot(String day, String hour,String courseCode, String courseName, String degree, String department, String semester, String section, String block, String floor, String roomNo, String assistingFaculty) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.degree = degree;
        this.department = department;
        this.semester = semester;
        this.section = section;
        this.block = block;
        this.floor = floor;
        this.roomNo = roomNo;
        this.assistingFaculty = assistingFaculty;
        this.day=day;
        this.hour=hour;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getAssistingFaculty() {
        return assistingFaculty;
    }

    public void setAssistingFaculty(String assistingFaculty) {
        this.assistingFaculty = assistingFaculty;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }


}
