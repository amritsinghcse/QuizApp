package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuizDetails extends AppCompatActivity {

    List<Question> list;
    public static final String TAG = "Ye raha";
    TextView topic, question, option1, option2, option3, option4;
    int score = 0, questionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);
        list = (List<Question>) getIntent().getSerializableExtra("list");
        topic = (TextView) findViewById(R.id.topic);
        question = (TextView) findViewById(R.id.question);
        option1 = (TextView) findViewById(R.id.option_1);
        option2 = (TextView) findViewById(R.id.option_2);
        option3 = (TextView) findViewById(R.id.option_3);
        option4 = (TextView) findViewById(R.id.option_4);
        topic.setText(getIntent().getStringExtra("topic"));
       setUI();


    }

    public void optionClicked(View view) {
        TextView view1 = (TextView) view;
        if (view1.getText().toString().equals(list.get(questionCount).answer)) {
            score++;
            Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
        }
        questionCount++;
        if(questionCount < 5)
            setUI();
    }

        public void setUI(){
        question.setText(list.get(questionCount).question);
        option1.setText(list.get(questionCount).options[0]);
        option2.setText(list.get(questionCount).options[1]);
        option3.setText(list.get(questionCount).options[2]);
        option4.setText(list.get(questionCount).options[3]);



    }
}
