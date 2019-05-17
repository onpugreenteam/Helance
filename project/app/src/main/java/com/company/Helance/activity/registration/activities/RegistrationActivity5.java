package com.company.Helance.activity.registration.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.company.Helance.activity.logIn.LogInActivity;
import com.company.Helance.activity.registration.viewModel.RegistrationViewModel;
import com.company.Helance.settingsApp.SharedPrefManager;

import java.util.Objects;

public class RegistrationActivity5 extends AppCompatActivity implements Activity {

    private TextInputLayout code;
    private Button next;
    private String login;
    private String email;
    private String name;
    private String country;
    private String avatar;
    private String phone;
    private RegistrationViewModel registrationViewModel;

    private boolean codeIsCorrect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration5);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        toolbar();
        findViewById();
        onListener();

        login = getIntent().getStringExtra("login");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
        avatar = getIntent().getStringExtra("avatar");
        phone = getIntent().getStringExtra("phone");
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
                Intent intent = new Intent(RegistrationActivity5.this, LogInActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity5.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void findViewById() {
        code = findViewById(R.id.activation_code);
        next = findViewById(R.id.registration_button_5);
    }

    @Override
    public void onListener() {
        next.setOnClickListener(v -> {
            /** use this to check activation code*/
            attemptActivation();
        });
        code.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener();
            }
        });
    }

    private void listener() {
        final String code = this.code.getEditText().getText().toString();
        if(code.length()==4) {
            if (!registrationViewModel.checkCode(email, code)) {
                codeIsCorrect = false;
            } else {
                codeIsCorrect = true;
            }
        }
    }

    private void attemptActivation() {

        // Reset errors.
        code.setError(null);

        // Store values at the time of the login attempt.
        String codeValue = Objects.requireNonNull(code.getEditText()).getText().toString();

        boolean cancel = false;

        // Check for a valid login.
        if (TextUtils.isEmpty(codeValue)) {
            code.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!TextUtils.isEmpty(code.getEditText().getText().toString())
                && code.getEditText().getText().toString().length() == 4) {
            listener();
            if(!codeIsCorrect) {
                cancel = true;
                code.getEditText().setError(getString(R.string.error_code));
            }
        }
        if (!cancel){
//            isCodeRight();
//            activateAccount();
            Intent lobby = new Intent(this, LobbyActivity.class);
            SharedPrefManager.getInstance(this).userLogin(
                    login,
                    name,
                    email,
                    country,
                    avatar,
                    phone
            );
            registrationViewModel.getSocialNetworks(login);
            lobby.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(lobby);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private void isCodeRight() {
        final String codeField = Objects.requireNonNull(code.getEditText()).getText().toString().trim();
        if(registrationViewModel.checkCode(email, codeField)) {
            Intent lobby = new Intent(this, LobbyActivity.class);
            SharedPrefManager.getInstance(this).userLogin(
                    login,
                    name,
                    email,
                    country,
                    avatar,
                    phone
            );
            registrationViewModel.getSocialNetworks(login);
            lobby.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(lobby);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Toast.makeText(this, "Wrong code", Toast.LENGTH_SHORT).show();
        }
    }
}
