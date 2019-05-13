package com.ranpeak.ProjectX.activity.registration.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.github.reinaldoarrosi.maskededittext.MaskedEditText;
import com.hbb20.CountryCodePicker;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.ranpeak.ProjectX.activity.registration.viewModel.RegistrationViewModel;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity4 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY4 = R.layout.activity_registration4;

    private TextInputLayout registration_username;
    private Button nextButton;
    //    private EditText registration_phoneNumber;
    private CountryCodePicker registration_phoneNumber_code;
    private MaskedEditText registration_phoneNumber;
    private EditText registration_telegram;
    private EditText registration_instagram;
    private EditText registration_facebook;

    private String email;
    private String name;
    private String country;
    private String password;
    private String login;

    private RegistrationViewModel registrationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY4);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        login = getIntent().getStringExtra("login");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
        password = getIntent().getStringExtra("password");

        toolbar();
        findViewById();
        onListener();
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

//                Intent intent = new Intent(CreatingTaskActivity.this, LobbyActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void findViewById() {

        registration_phoneNumber = findViewById(R.id.registration_phone_number);
        registration_phoneNumber_code = findViewById(R.id.registration_phone_number_code);
        registration_telegram = findViewById(R.id.registration_telegram);
        registration_instagram = findViewById(R.id.registration_instagram);
        registration_facebook = findViewById(R.id.registration_facebook);
        nextButton = findViewById(R.id.registration_button_4);
    }

    @Override
    public void onListener() {
        nextButton.setOnClickListener(view -> {
            attemptRegistration();
        });
    }

    private void attemptRegistration() {

        // Reset errors.
        registration_telegram.setError(null);
        registration_instagram.setError(null);
        registration_facebook.setError(null);
        registration_phoneNumber.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(registration_phoneNumber.getText().toString())) {
            registration_phoneNumber.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = registration_phoneNumber;
        } else if (registration_phoneNumber.getText().toString().length() < 10) {
            registration_phoneNumber.setError(getString(R.string.error_wrong_number));
            cancel = true;
            focusView = registration_phoneNumber;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
//            next();
            registerUser(
                    login, email, name,
                    password, country,
                    registration_phoneNumber_code.getSelectedCountryCodeAsInt() + registration_phoneNumber.getText().toString());

        }
    }

    private void registerNetwork(String login, String networkName, String networkLogin) {
        registrationViewModel.addNetwork(login, networkName, networkLogin);
    }

    private void registerUser(String login, String email, String name, String password, String country, String phone) {
        if(registrationViewModel.register(login,
                email, name,password, country, phone)){
            if (registration_telegram.getText().toString().length() != 0
                    || registration_instagram.getText().toString().length() == 0
                    || registration_facebook.getText().toString().length() == 0) {
                if (registration_telegram.getText().toString().length() != 0) {
                    registerNetwork(login, getString(R.string.telegram), registration_telegram.getText().toString());
                }
                if (registration_instagram.getText().toString().length() != 0) {
                    registerNetwork(login, getString(R.string.instagram), registration_instagram.getText().toString());
                }
                if (registration_facebook.getText().toString().length() != 0) {
                    registerNetwork(login, getString(R.string.facebook_app_id), registration_facebook.getText().toString());
                }

            }
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity5.class);
            intent.putExtra("login", login);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("country", country);
            intent.putExtra("avatar", "nullk");
            intent.putExtra("phone", phone);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        };
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}