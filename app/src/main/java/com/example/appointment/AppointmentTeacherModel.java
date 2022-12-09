package com.example.appointment;

import java.util.Date;

public class AppointmentTeacherModel {

    private String Student_ID;
    private String Name;
    private String Date;
    private String Time;
    private String Detail;
    private String Topic;
    private String TeacherID;
    private String Teacher;
    private String Status;
    private String Day;
    private String Note_ID;
    private Long Timestamp;
    private Long DateMillis;
    private String Section;
    private String AdvisorID;
    private String AdvisorName;

    public AppointmentTeacherModel() {
    }

    public AppointmentTeacherModel(String student_ID, String name, String date, String time, String detail, String topic, String teacher, String status, String day, String note_ID, Long timestamp, Long dateMillis, String teacherID, String section, String advisorID, String advisorName) {
        this.Student_ID = student_ID;
        this.Name = name;
        this.Date = date;
        this.Time = time;
        this.Detail = detail;
        this.Topic = topic;
        this.Teacher = teacher;
        this.Status = status;
        this.Day = day;
        this.Note_ID = note_ID;
        this.Timestamp = timestamp;
        this.DateMillis = dateMillis;
        this.TeacherID = teacherID;
        this.Section = section;
        this.AdvisorID = advisorID;
        this.AdvisorName = advisorName;
    }

    public String getAdvisorName() {
        return AdvisorName;
    }

    public void setAdvisorName(String advisorName) {
        AdvisorName = advisorName;
    }

    public String getAdvisorID() {
        return AdvisorID;
    }

    public void setAdvisorID(String advisorID) {
        AdvisorID = advisorID;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public Long getDateMillis() {
        return DateMillis;
    }

    public void setDateMillis(Long dateMillis) {
        DateMillis = dateMillis;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.Timestamp = timestamp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.Student_ID = student_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        this.Detail = detail;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        this.Topic = topic;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        this.Teacher = teacher;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        this.Day = day;
    }

    public String getNote_ID() {
        return Note_ID;
    }

    public void setNote_ID(String note_ID) {
        this.Note_ID = note_ID;
    }
}
