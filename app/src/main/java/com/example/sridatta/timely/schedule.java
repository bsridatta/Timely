package com.example.sridatta.timely;

/**
 * Created by sridatta on 17-09-2017.
 */

public class schedule {

    private String courseId;
    private String courseName;
    private String className;
    private String classRoom;
    private String helpingFaculty;

    //default constructor
    public schedule(){

    }

    //parametrized constructor
    public schedule(String courseId, String courseName, String className, String classRoom, String helpingFaculty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.className = className;
        this.classRoom = classRoom;
        this.helpingFaculty = helpingFaculty;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getHelpingFaculty() {
        return helpingFaculty;
    }

    public void setHelpingFaculty(String helpingFaculty) {
        this.helpingFaculty = helpingFaculty;
    }


}
