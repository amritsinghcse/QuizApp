package com.example.android.quizapp.database;

import android.provider.BaseColumns;


public class DBContract {

    private DBContract(){}

    public static class DBEntry implements BaseColumns{
        public static final String TABLE_NAME = "topics";
        public static final String COLUMN_TOPIC_NAME = "Topic";
        public static final String TABLE_NAME_2 = "questions";
        public static final String COLUMN_ID = "q_id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTIONS = "options";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PASSWORD = "password";
        public static final String TABLE_USER = "users";
    }
}
