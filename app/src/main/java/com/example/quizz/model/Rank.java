package com.example.quizz.model;

public class Rank {
    String subjectId;
    String subjectName;
    int score;
    public Rank(String subjectId, String subjectName, int score) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.score = score;
    }

    public Rank() {
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
