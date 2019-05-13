package com.ranpeak.ProjectX.activity.registration.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.ranpeak.ProjectX.activity.registration.viewModel.RegistrationViewModel;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

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
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    }

    private void attemptActivation() {

        // Reset errors.
        code.setError(null);

        // Store values at the time of the login attempt.
        String codeValue = Objects.requireNonNull(code.getEditText()).getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid login.
        if (TextUtils.isEmpty(codeValue)) {
            code.setError(getString(R.string.error_field_required));
            focusView = code;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            isCodeRight();
//            activateAccount();
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
