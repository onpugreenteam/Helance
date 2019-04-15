package com.ranpeak.ProjectX.activity.registration;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import java.util.Objects;


public class RegistrationActivity3 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY2 = R.layout.activity_registration3;

    private TextInputLayout password_1;
    private EditText password_2;
    private String email;
    private String name;
    private String country;
    private String login;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar();
        findViewById();
        onListener();


        login = getIntent().getStringExtra("login");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void findViewById() {
        password_1 = findViewById(R.id.registration_password_1);
        password_2 = findViewById(R.id.registration_password_2);
        nextButton = findViewById(R.id.registration_button_2);
    }

    @Override
    public void onListener() {
        password_2.addTextChangedListener(textWatcher);
        nextButton.setOnClickListener(v -> attemptPasswordClick());
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            attemptPassword();
        }
    };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void attemptPassword() {
        String password = Objects.requireNonNull(password_1.getEditText()).getText().toString().trim();
        if (password.isEmpty()) {
            password_1.setError(getString(R.string.error_field_required));
        }
//        else if( !Constants.Values.getPasswordPattern().matcher(password).matches()){
//            password_1.setError(getString(R.string.error_weak_password));
//            return false;
//        }
        else if (!isSecondPasswordMatches()) {
            password_2.setError(getString(R.string.error_password_doesnt_match));
        } else {
            password_1.setError(null);
            password_2.setError(null);
        }
    }

    private void attemptPasswordClick() {
        String password = Objects.requireNonNull(password_1.getEditText()).getText().toString().trim();
        boolean cancel;
        if (password.isEmpty()) {
            password_1.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isPasswordValid(password)) {
            password_1.setError(getString(R.string.error_invalid_password_short));
            cancel = true;
        }
//        else if( !Constants.Values.getPasswordPattern().matcher(password).matches()){
//            password_1.setError(getString(R.string.error_weak_password));
//            return false;
//        }
        else if (!isSecondPasswordMatches()) {
            password_2.setError(getString(R.string.error_password_doesnt_match));
            cancel = true;
        } else {
            password_1.setError(null);
            password_2.setError(null);
            cancel = false;
        }
        if (!cancel) {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity4.class);
            intent.putExtra("email", email);
            intent.putExtra("login", login);
            intent.putExtra("name", name);
            intent.putExtra("country", country);
            intent.putExtra("password", password_1.getEditText().getText().toString().trim());
//                intent.putExtra("country", "Nigeria");
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    private boolean isSecondPasswordMatches() {
        return password_2.getText().toString().equals(Objects.requireNonNull(password_1.getEditText()).getText().toString());
    }
}
