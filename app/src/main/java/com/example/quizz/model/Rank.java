package com.example.quizz.model;

public class Rank {
    String subjectId;
    String subjectName;
    int score;
    int numOfTest;
    public Rank(String subjectId, String subjectName, int score, int numOfTest) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.score = score;
        this.numOfTest = numOfTest;
    }

    public Rank() {
    }

    public int getNumOfTest() {
        return numOfTest;
    }

    public void setNumOfTest(int numOfTest) {
        this.numOfTest = numOfTest;
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
