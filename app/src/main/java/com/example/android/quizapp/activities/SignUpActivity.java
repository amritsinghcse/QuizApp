package com.example.android.quizapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.quizapp.R;
import com.example.android.quizapp.database.DBHelper;
import com.example.android.quizapp.models.User;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

	DBHelper dbHelper;
	EditText nameField, emailField, passwordField;
	Button signUp;
	TextInputLayout nameLayout, emailLayout, passwordLayout;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		dbHelper = new DBHelper(this);
		nameField = (EditText) findViewById(R.id.name);
		emailField = (EditText) findViewById(R.id.email);
		passwordField = (EditText) findViewById(R.id.password);
		nameLayout = (TextInputLayout) findViewById(R.id.name_layout);
		emailLayout = (TextInputLayout) findViewById(R.id.email_layout);
		passwordLayout = (TextInputLayout) findViewById(R.id.password_layout);

		signUp = (Button) findViewById(R.id.sign_up_button);
		signUp.setOnClickListener(view -> {

			User user = new User(nameField.getText().toString(), emailField.getText().toString(),
				passwordField.getText().toString());
			dbHelper.addUser(user);
			Toast.makeText(SignUpActivity.this, "SignUp success!", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(SignUpActivity.this, MainActivity.class);
			i.putExtra("username", user.getName());
			i.putExtra("useremail", user.getEmail());
			startActivity(i);
			finish();
		});

		signUp.setEnabled(false);

		Observable<Boolean> nameObservable = RxTextView.textChanges(nameField)
			.map(s -> (s.length() != 0) && s.toString().matches("^[\\p{L} .'-]+$"))
			.debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
			.distinctUntilChanged();

		nameObservable.subscribe(valid -> {
			nameLayout.setError("Invalid name");
			nameLayout.setErrorEnabled(!valid);
		});

		Observable<Boolean> emailObservable = RxTextView.textChanges(emailField)
			.map(s -> (s.length() != 0) && s.toString()
				.matches("[A-Za-z0-9_.]+@[A-Za-z0-9]+.[A-Za-z]+"))
			.debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
			.distinctUntilChanged();

		emailObservable.subscribe(valid -> {
			emailLayout.setError("Invalid email");
			emailLayout.setErrorEnabled(!valid);
		});

		Observable<Boolean> passwordObservable = RxTextView.textChanges(passwordField)
			.map(s -> (s.length() != 0) && s.toString().matches("^[a-zA-Z0-9]+$"))
			.debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
			.distinctUntilChanged();

		passwordObservable.subscribe(valid -> {
			passwordLayout.setError("Alphanumeric only");
			passwordLayout.setErrorEnabled(!valid);
		});

		Observable.combineLatest(nameObservable, emailObservable, passwordObservable,
			(nameValid, emailValid, passwordValid) -> nameValid && emailValid && passwordValid)
			.distinctUntilChanged()
			.subscribe(valid -> {
				signUp.setEnabled(valid);
				signUp.setBackgroundColor(valid ? Color.BLACK : Color.GRAY);
			});
	}
}
