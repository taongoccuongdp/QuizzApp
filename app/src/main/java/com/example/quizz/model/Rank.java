package com.example.quizz.model;

public class Rank {
    int id;
    int userId;
    int monhocId;
    int score;

    public Rank() {
    }

    public Rank(int id, int userId, int monhocId, int score) {
        this.id = id;
        this.userId = userId;
        this.monhocId = monhocId;
        this.score = score;
    }

    public Rank(int userId, int monhocId, int score) {
        this.userId = userId;
        this.monhocId = monhocId;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMonhocId() {
        return monhocId;
    }

    public void setMonhocId(int monhocId) {
        this.monhocId = monhocId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
