package com.example.quizz.model;

public class Questions {
    private int id;
    private int subjectId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightOpt;

    public Questions() {
    }

    public Questions(int id, int subjectId, String question, String option1, String option2, String option3, String option4, String rightOpt) {
        this.id = id;
        this.subjectId = subjectId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.rightOpt = rightOpt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getRightOpt() {
        return rightOpt;
    }

    public void setRightOpt(String rightOpt) {
        this.rightOpt = rightOpt;
    }
}
