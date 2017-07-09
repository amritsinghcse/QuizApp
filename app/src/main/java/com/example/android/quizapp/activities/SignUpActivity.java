package com.example.android.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.quizapp.R;
import com.example.android.quizapp.database.DBHelper;
import com.example.android.quizapp.models.User;

public class SignUpActivity extends AppCompatActivity {


    DBHelper dbHelper;
    EditText nameField, emailField, passwordField;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DBHelper(this);
        nameField = (EditText) findViewById(R.id.name);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameField.getText().toString().isEmpty()){
                    nameField.setError("Name cannot be empty");
                    return;
                }
                if (emailField.getText().toString().isEmpty()) {
                    emailField.setError("Email cannot be empty");
                    return;
                }
                if (passwordField.getText().toString().isEmpty()) {
                    passwordField.setError("Password cannot be empty");
                    return;
                }

                User user = new User(nameField.getText().toString(), emailField.getText().toString(), passwordField.getText().toString());
                dbHelper.addUser(user);
                Toast.makeText(SignUpActivity.this, "SignUp success!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                i.putExtra("username", user.getName());
                i.putExtra("useremail", user.getEmail());
                startActivity(i);
                finish();
            }
        });

    }
}
