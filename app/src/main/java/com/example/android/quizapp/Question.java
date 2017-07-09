package com.example.android.quizapp;


import java.io.Serializable;

public class Question implements Serializable {
    public String question;
    public String rawOptions;
   public String[] options;
    public String answer;

    public Question(String question, String rawOptions, String answer) {
        this.question = question;
        this.rawOptions = rawOptions;
        this.answer = answer;
        parseOptions();
    }

    public void parseOptions(){
        options = rawOptions.split("\\s*,\\s*");

    }

}
