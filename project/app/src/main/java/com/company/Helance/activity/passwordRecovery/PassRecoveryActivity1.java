package com.company.Helance.activity.passwordRecovery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.registration.viewModel.RegistrationViewModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassRecoveryActivity1 extends AppCompatActivity implements Activity {

    private TextView emailTextView;
    private EditText emailEditText;
    private Button nextButton;
    private boolean email_on_server = true;


    private RegistrationViewModel registrationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        toolbar();
        findViewById();
        onListener();
    }

    @Override
    public void findViewById() {
        emailTextView = findViewById(R.id.password_recovery1_activity_text_view);
        emailEditText = findViewById(R.id.password_recovery1_activity_edit_text);
        nextButton = findViewById(R.id.password_recovery1_button);
    }

    @Override
    public void onListener() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmailOnServer();
            }
        });
        nextButton.setOnClickListener(v -> checkEmail());
    }

    private void checkEmail() {
        /** проверка почты на сервере (существует или нет)*/

        emailEditText.setError(null);
        boolean cancel = false;

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            cancel = true;
        } else if (!TextUtils.isEmpty(emailEditText.getText().toString())
                && isEmailValid(emailEditText.getText().toString())) {
            checkEmailOnServer();
            if (!email_on_server) {
                cancel = true;

                emailEditText.setError(getString(R.string.error_email_not_exist));
            }
        }
        if (!cancel) {
            sendCodeOnEmail(emailEditText.getText().toString());
            Intent intent = new Intent(PassRecoveryActivity1.this, PassRecoveryActivity2.class);
            intent.putExtra("email", emailEditText.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }

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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void checkEmailOnServer() {
        if(isEmailValid(emailEditText.getText().toString())) {
            final String email = Objects.requireNonNull(emailEditText).getText().toString().trim();
            if (!registrationViewModel.checkEmailOnServer(email)) {
                email_on_server = false;

            } else {
                emailEditText.setError(null);
                email_on_server = true;

            }
        }
    }

    private void sendCodeOnEmail(String email) {
        registrationViewModel.sendCodeOnEmail(email);
    }
}
