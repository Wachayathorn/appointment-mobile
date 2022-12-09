package com.example.appointment;

import java.util.ArrayList;

public class RetrieveStatusUser {

    private String Topic;
    private String Time;
    private String Date;
    private String Detail;
    private String Status;
    private String Day;
    private String Teacher;
    private String TeacherID;
    private String Name;
    private String Student_ID;
    private ArrayList<String> Tool;
    private ArrayList<String> ToolNumber;
    private String DateBorrow;
    private String DateBack;
    private String Note_ID;
    private String Purpose;
    private String DeclineReason;
    private String Barcode;
    private String Section;
    private String AdvisorID;
    private String AdvisorName;

    public RetrieveStatusUser() {
    }

    public RetrieveStatusUser(String topic, String time, String date, String detail, String status, String day, String teacher, String name, String student_ID, ArrayList<String> tool, ArrayList<String> toolNumber, String dateBorrow, String dateBack, String note_id, String purpose, String declineReason, String barcode, String teacherID, String section, String advisorID, String advisorName) {
        this.Topic = topic;
        this.Time = time;
        this.Date = date;
        this.Detail = detail;
        this.Status = status;
        this.Day = day;
        this.Teacher = teacher;
        this.Name = name;
        this.Student_ID = student_ID;
        this.Tool = tool;
        this.ToolNumber = toolNumber;
        this.DateBorrow = dateBorrow;
        this.DateBack = dateBack;
        this.Note_ID = note_id;
        this.Purpose = purpose;
        this.DeclineReason = declineReason;
        this.Barcode = barcode;
        this.TeacherID = teacherID;
        this.Section = section;
        this.AdvisorID = advisorID;
        this.AdvisorName = advisorName;
    }

    public String getAdvisorID() {
        return AdvisorID;
    }

    public void setAdvisorID(String advisorID) {
        AdvisorID = advisorID;
    }

    public String getAdvisorName() {
        return AdvisorName;
    }

    public void setAdvisorName(String advisorName) {
        AdvisorName = advisorName;
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

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
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

    public String getDateBorrow() {
        return DateBorrow;
    }

    public void setDateBorrow(String dateBorrow) {
        this.DateBorrow = dateBorrow;
    }

    public String getDateBack() {
        return DateBack;
    }

    public void setDateBack(String dateBack) {
        this.DateBack = dateBack;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        this.Purpose = purpose;
    }

    public String getDeclineReason() {
        return DeclineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.DeclineReason = declineReason;
    }
}
