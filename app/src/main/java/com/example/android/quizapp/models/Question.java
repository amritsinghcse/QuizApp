package com.example.android.quizapp.models;


import java.io.Serializable;

public class Question implements Serializable {
    private String question;
    private String rawOptions;
    private String[] options;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }



    public Question(String question, String rawOptions, String answer) {
        this.question = question;
        this.rawOptions = rawOptions;
        this.answer = answer;
        parseOptions();
    }

    private void parseOptions(){
        options = rawOptions.split("\\s*,\\s*");

    }

}
