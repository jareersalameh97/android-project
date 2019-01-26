package com.example.aloos.finalproject2.HelperClass;

public class ShowQuiz {
    private  String quizid;
    private  String quizname;
    private  String quizduration;

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getQuizname() {
        return quizname;
    }

    public void setQuizname(String quizname) {
        this.quizname = quizname;
    }

    public String getQuizduration() {
        return quizduration;
    }

    public void setQuizduration(String quizduration) {
        this.quizduration = quizduration;
    }

    @Override
    public String toString() {
        return quizduration + quizid + quizname;
    }
}
