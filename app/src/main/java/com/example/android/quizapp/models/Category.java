package com.example.android.quizapp.models;


public class Category {

    private int id;
    private String categoryName;

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}

