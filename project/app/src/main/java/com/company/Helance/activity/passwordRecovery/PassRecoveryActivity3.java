package com.company.Helance.activity.passwordRecovery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.company.Helance.R;
import com.company.Helance.activity.settings.LanguageHelper;
import com.company.Helance.base.BaseActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.logIn.LogInActivity;
import com.company.Helance.activity.registration.viewModel.RegistrationViewModel;

import java.util.Objects;

public class PassRecoveryActivity3 extends BaseActivity implements Activity {

    private TextView textView;
    private AppCompatEditText password1;
    private EditText password2 ;
    private Button nextButton;
    private String email;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = getIntent().getStringExtra("email");
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        toolbar();
        findViewById();
        onListener();
    }

    @Override
    public void findViewById() {
        textView = findViewById(R.id.password_recovery3_activity_text_view);
        password1 = findViewById(R.id.new_password_activity_AppCompatEditText);
        password2 = findViewById(R.id.new_password_edit_text);
        nextButton = findViewById(R.id.password_recovery3_button);
    }


    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListener() {
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPassword();
            }
        });
        nextButton.setOnClickListener(v-> attemptPassword());
    }

    private void checkPassword() {
        String password = Objects.requireNonNull(password1.getText()).toString().trim();
        if (password.isEmpty()) {
            password1.setError(getString(R.string.error_field_required));
        }
//        else if( !Constants.Values.getPasswordPattern().matcher(password).matches()){
//            password_1.setError(getString(R.string.error_weak_password));
//            return false;
//        }
        else if (!isSecondPasswordMatches()) {
            password2.setError(getString(R.string.error_password_doesnt_match));
        } else {
            password1.setError(null);
            password2.setError(null);
        }
    }

    private void attemptPassword() {
        String password = Objects.requireNonNull(password1.getText()).toString().trim();
        boolean cancel = false;
        if (password.isEmpty()) {
            password1.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isPasswordValid(password)) {
            password1.setError(getString(R.string.error_invalid_password_short));
            cancel = true;
        }
//        else if( !Constants.Values.getPasswordPattern().matcher(password).matches()){
//            password_1.setError(getString(R.string.error_weak_password));
//            return false;
//        }
        else if (!isSecondPasswordMatches()) {
            password2.setError(getString(R.string.error_password_doesnt_match));
            cancel = true;
        } else {
            password1.setError(null);
            password2.setError(null);
        }
        if(!cancel) {
            registrationViewModel.changePassword(email, password2.getText().toString());
            Intent intent = new Intent(PassRecoveryActivity3.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private boolean isSecondPasswordMatches() {
        return password2.getText().toString().equals(Objects.requireNonNull(password1.getText()).toString());
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }


}
