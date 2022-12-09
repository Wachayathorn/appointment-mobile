package com.example.appointment;

import java.util.ArrayList;

public class RetrieveNewsUser {

    private String Enddate;
    private Long EnddateMillis;
    private String News;
    private String Note_ID;
    private String Startdate;
    private Long StartdateMillis;
    private String Status;
    private ArrayList<String> Student_ID;
    private String TeacherID;
    private String TeacherName;
    private String TeacherRole;
    private Long Timestamp;
    private String Title;

    public RetrieveNewsUser() {

    }

    public RetrieveNewsUser(String enddate, Long enddateMillis, String news, String note_ID, String startdate, Long startdateMillis, String status, ArrayList<String> student_ID, String teacherID, String teacherName, String teacherRole, Long timestamp, String title) {
        this.Enddate = enddate;
        this.EnddateMillis = enddateMillis;
        this.News = news;
        this.Note_ID = note_ID;
        this.Startdate = startdate;
        this.StartdateMillis = startdateMillis;
        this.Status = status;
        this.Student_ID = student_ID;
        this.TeacherID = teacherID;
        this.TeacherName = teacherName;
        this.TeacherRole = teacherRole;
        this.Timestamp = timestamp;
        this.Title = title;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public Long getEnddateMillis() {
        return EnddateMillis;
    }

    public void setEnddateMillis(Long enddateMillis) {
        EnddateMillis = enddateMillis;
    }

    public String getNews() {
        return News;
    }

    public void setNews(String news) {
        News = news;
    }

    public String getNote_ID() {
        return Note_ID;
    }

    public void setNote_ID(String note_ID) {
        Note_ID = note_ID;
    }

    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public Long getStartdateMillis() {
        return StartdateMillis;
    }

    public void setStartdateMillis(Long startdateMillis) {
        StartdateMillis = startdateMillis;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public ArrayList<String> getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(ArrayList<String> student_ID) {
        Student_ID = student_ID;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherRole(String teacherRole) {
        TeacherRole = teacherRole;
    }

    public String getTeacherRole() {
        return TeacherRole;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
