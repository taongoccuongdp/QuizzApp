package com.example.quizz.model;

public class Questions {
    int id;
    int monhocId;
    String question;
    String answerA;
    String answerB;
    String answerC;
    String answerD;
    String rightAnswer;

    public Questions(int monhocId, String question, String answerA, String answerB, String answerC, String answerD, String rightAnswer) {
        this.monhocId = monhocId;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.rightAnswer = rightAnswer;
    }

    public Questions(int id, int monhocId, String question, String answerA, String answerB, String answerC, String answerD, String rightAnswer) {
        this.id = id;
        this.monhocId = monhocId;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.rightAnswer = rightAnswer;
    }

    public Questions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonhocId() {
        return monhocId;
    }

    public void setMonhocId(int monhocId) {
        this.monhocId = monhocId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
