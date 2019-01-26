package com.example.aloos.finalproject2.HelperClass;

import java.io.Serializable;

public class Questions implements Serializable {
    private String question;
    private String T_A;
    private String F_A1;
    private String F_A2;
    private String F_A3;
    private String timer;

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }


    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    private String Flag;
    public String getF_A2() {
        return F_A2;
    }

    public void setF_A2(String f_A2) {
        F_A2 = f_A2;
    }

    public String getF_A1() {

        return F_A1;
    }

    public void setF_A1(String f_A1) {
        F_A1 = f_A1;
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }



    public String getT_A() {
        return T_A;
    }

    public void setT_A(String t_A) {
        T_A = t_A;
    }

    public String getF_A3() {
        return F_A3;
    }

    public void setF_A3(String f_A3) {
        F_A3 = f_A3;
    }

    @Override
    public String toString(){
        return getQuestion() + " " + getT_A() + " " + getF_A1() + " " + getF_A2() + " "+getF_A3();
    }
}
