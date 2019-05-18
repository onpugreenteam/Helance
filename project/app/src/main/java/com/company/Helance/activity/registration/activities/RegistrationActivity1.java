package com.company.Helance.activity.registration.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.registration.viewModel.RegistrationViewModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity1 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY1 = R.layout.activity_registration1;

    private EditText register_name;
    private EditText register_login;
    private CountryCodePicker register_country;
    private Button nextButton;
    private EditText register_email;
    private boolean email_exists = false;
    private boolean login_exists = false;


    private RegistrationViewModel registrationViewModel;
//    private final CountryListFragment countryListFragment = new CountryListFragment();
//    private final FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar();
        findViewById();

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        onListener();

    }


    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            // если пустых полей нет, то открывается диалог с потверждение закрытия окна
            if (!allFieldsEmpty()) {
                openDialog();
            }
            // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
            else finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // если нажата кнопка назад на устройстве
    @Override
    public void onBackPressed() {
        // если пустых полей нет, то открывается диалог с потверждение закрытия окна
        if (!allFieldsEmpty()) {
            openDialog();
        }
        // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
        else {
            super.onBackPressed();
        }
    }

    // открытие подтверждающего диалога перед закрытием окна
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(getString(R.string.confirm_exit))
                .setMessage(getString(R.string.cancel_registration))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    finish();

                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void findViewById() {
        register_login = findViewById(R.id.registration_login);
        register_email = findViewById(R.id.registration_email);
        register_name = findViewById(R.id.register_name);
        register_country = findViewById(R.id.register_country);
        nextButton = findViewById(R.id.registration_button_1);
    }

    @Override
    public void onListener() {
        nextButton.setOnClickListener(v -> attemptRegistration());
        register_login.addTextChangedListener(textWatcher);
        register_email.addTextChangedListener(textWatcher);
        register_name.addTextChangedListener(textWatcher);
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
            if (isLoginValidLong(register_login.getText().toString()) && isLoginValidShort(register_login.getText().toString())) {
                checkLogin();
            }
            if(isEmailValid(register_email.getText().toString())) {
                checkEmail();
            }
        }
    };

    private void checkLogin() {
        final String login = Objects.requireNonNull(register_login).getText().toString().trim();
        if (!registrationViewModel.checkUserLogin(login)) {
            register_login.setError(getString(R.string.error_exist_login));
            login_exists = true;
        } else {
            register_login.setError(null);
            login_exists = false;
        }
    }

    private void checkEmail() {
        final String email = Objects.requireNonNull(register_email).getText().toString().trim();
        if(!registrationViewModel.checkUserEmail(email)) {
            register_email.setError(getString(R.string.error_exist_email));
            email_exists = true;
        } else {
            register_email.setError(null);
            email_exists = false;
        }
    }

    private void attemptRegistration() {
        // Reset errors.
        register_email.setError(null);
        register_name.setError(null);
        register_email.setError(null);

        // Store values at the time of the login attempt.
        String email = Objects.requireNonNull(register_email).getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        String login = Objects.requireNonNull(register_login).getText().toString();

//        // Check for a valid country
//        if (!stringContainsItemFromList(register_country.getSelectedCountryName())) {
//            cancel = true;
//        }

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(register_name.getText().toString())) {
            register_name.setError(getString(R.string.error_field_required));
            focusView = register_name;
            cancel = true;
        } else if (!isNameValid(register_name.getText().toString())) {
            register_name.setError(getString(R.string.error_invalid_name));
            focusView = register_name;
            cancel = true;
        }

        // Check for a valid email, if the user entered one.
        if (TextUtils.isEmpty(email)) {
            register_email.setError(getString(R.string.error_field_required));
            focusView = register_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            register_email.setError(getString(R.string.error_invalid_email));
            focusView = register_email;
            cancel = true;
        } else if (!TextUtils.isEmpty(email) && isEmailValid(email)) {
            checkEmail();
            if (email_exists) {
                register_email.setError(getString(R.string.error_exist_email));
                focusView = register_email;
                cancel = true;
            }
        }

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            register_login.setError(getString(R.string.error_field_required));
            focusView = register_login;
            cancel = true;
        } else if (!isLoginValidShort(login)) {
            register_login.setError(getString(R.string.error_invalid_login_short));
            focusView = register_login;
            cancel = true;
        } else if (!isLoginValidLong(login)) {
            register_login.setError(getString(R.string.error_invalid_login_long));
            focusView = register_login;
            cancel = true;
        } else if (!TextUtils.isEmpty(login) && isLoginValidLong(login) && isLoginValidShort(login)) {
            checkLogin();
            if (login_exists) {
                register_login.setError(getString(R.string.error_exist_login));
                focusView = register_login;
                cancel = true;
            }
        }

        if (!cancel) {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity3.class);
            intent.putExtra("email", register_email.getText().toString().trim());
            intent.putExtra("login", register_login.getText().toString().trim());
            intent.putExtra("name", register_name.getText().toString().trim());
            intent.putExtra("country", register_country.getSelectedCountryName());
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        }
    }

    public void setCountry(String country) {
//        this.register_country.setText(country);
//        this.register_country.setTextColor(ContextCompat.getColor(this, R.color.darkText));
    }

    private boolean isLoginValidShort(String login) {
        return login.length() > 4;
    }

    private boolean isLoginValidLong(String login) {
        return login.length() < 10;
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isNameValid(String name) {
        return name.length() > 2;
    }

    private boolean allFieldsEmpty() {
        return TextUtils.isEmpty(register_name.getText().toString())
                && TextUtils.isEmpty(register_email.getText().toString())
                && TextUtils.isEmpty(register_login.getText().toString())
                && TextUtils.isEmpty(register_country.getSelectedCountryName());
    }
}
