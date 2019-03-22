package com.ranpeak.ProjectX.activity.registration;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

public class RegistrationActivity3 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY3 = R.layout.activity_registration3;

    private TextInputLayout password_1;
    private EditText password_2;
    private Button nextButton;
    private String email;
    private String name;
    private String country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY3);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        findViewById();
        onListener();

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");


        nextButton.setEnabled(false);
    }

    @Override
    public void findViewById() {
        password_1 = findViewById(R.id.registration_password_1);
        password_2 = findViewById(R.id.registration_password_2);
        nextButton = findViewById(R.id.registration_button_3);
    }

    @Override
    public void onListener() {
        password_1.getEditText().addTextChangedListener(textWatcher);
        password_2.addTextChangedListener(textWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity4.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("country", country);
                intent.putExtra("password", password_2.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nextButton.setEnabled(isPasswordValid());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private boolean isPasswordValid() {
        String password = password_1.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            password_1.setError(getString(R.string.error_field_required));
            return false;
        }
//        else if( !Constants.Values.getPasswordPattern().matcher(password).matches()){
//            password_1.setError(getString(R.string.error_weak_password));
//            return false;
//        }
        else if (!isSecondPasswordMatches()) {
            password_2.setError(getString(R.string.error_password_doesnt_match));
            return false;
        } else {
            password_1.setError(null);
            password_2.setError(null);
            return true;
        }
    }


    private boolean isSecondPasswordMatches() {
        if (!password_2.getText().toString().equals(password_1.getEditText().getText().toString())) {
            return false;
        } else {
            return true;
        }
    }
}
