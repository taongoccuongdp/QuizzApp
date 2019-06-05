package com.example.quizz.model;

public class Schedual {
    private String date;
    private String note;
    public Schedual(){

    }
    public Schedual(String date, String note, String status) {
        this.date = date;
        this.note = note;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
