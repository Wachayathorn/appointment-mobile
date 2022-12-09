package com.example.appointment;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentServiceModel {

    private String Topic;
    private ArrayList<String> Tool;
    private ArrayList<String> ToolNumber;
    private String Time;
    private String Date;
    private String DateBack;
    private String Status;
    private String Day;
    private String Teacher;
    private String Name;
    private String Student_ID;
    private String Note_ID;
    private Long Timestamp;
    private String Purpose;
    private Long DateMillis;
    private String TeacherID;
    private String Section;
    private String AdvisorID;
    private String AdvisorName;

    public AppointmentServiceModel() {
    }

    public AppointmentServiceModel(String topic, ArrayList<String> tool, ArrayList<String> toolNumber, String time, String date, String dateBack, String status, String day, String teacher, String name, String student_ID, String note_id, Long timestamp, String purpose, Long dateMillis, String teacherID, String section, String advisorID, String advisorName) {
        this.Topic = topic;
        this.Tool = tool;
        this.ToolNumber = toolNumber;
        this.Time = time;
        this.Date = date;
        this.DateBack = dateBack;
        this.Status = status;
        this.Day = day;
        this.Teacher = teacher;
        this.Name = name;
        this.Student_ID = student_ID;
        this.Note_ID = note_id;
        this.Timestamp = timestamp;
        this.Purpose = purpose;
        this.DateMillis = dateMillis;
        this.TeacherID = teacherID;
        this.Section = section;
        this.AdvisorID = advisorID;
        this.AdvisorName = advisorName;
    }

    public void setAdvisorName(String advisorName) {
        AdvisorName = advisorName;
    }

    public String getAdvisorName() {
        return AdvisorName;
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

    public ArrayList<String> getTool() {
        return Tool;
    }

    public void setTool(ArrayList<String> tool) {
        this.Tool = tool;
    }

    public ArrayList<String> getToolNumber() {
        return ToolNumber;
    }

    public void setToolNumber(ArrayList<String> toolNumber) {
        this.ToolNumber = toolNumber;
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

    public String getDateBack() {
        return DateBack;
    }

    public void setDateBack(String dateBack) {
        this.DateBack = dateBack;
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

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        this.Purpose = purpose;
    }
}
