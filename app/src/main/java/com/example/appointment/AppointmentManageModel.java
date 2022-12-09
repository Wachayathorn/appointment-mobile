package com.example.appointment;


import android.util.Log;

import java.util.Date;

public class AppointmentManageModel {

    private String Topic;
    private String Time;
    private String Date;
    private String Detail;
    private String Status;
    private String Day;
    private String Teacher;
    private String Name;
    private String Student_ID;
    private String Note_ID;
    private Long Timestamp;
    private Long DateMillis;
    private String TeacherID;
    private String Section;
    private String AdvisorID;
    private String AdvisorName;

    public AppointmentManageModel() {
    }

    public AppointmentManageModel(String topic, String time, String date, String detail, String status, String day, String teacher, String name, String student_ID, String note_id, Long timestamp, Long dateMillis, String teacherID, String section, String advisorID, String advisorName) {
        this.Topic = topic;
        this.Time = time;
        this.Date = date;
        this.Detail = detail;
        this.Status = status;
        this.Day = day;
        this.Teacher = teacher;
        this.Name = name;
        this.Student_ID = student_ID;
        this.Note_ID = note_id;
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

    public String getNote_ID() {
        return Note_ID;
    }

    public void setNote_ID(String note_ID) {
        this.Note_ID = note_ID;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        this.Topic = topic;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        this.Detail = detail;
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

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        this.Teacher = teacher;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getStudent_ID() {
        return Student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.Student_ID = student_ID;
    }
}
