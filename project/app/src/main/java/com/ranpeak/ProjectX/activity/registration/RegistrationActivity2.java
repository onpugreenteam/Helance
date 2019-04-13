package com.ranpeak.ProjectX.activity.registration;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.LobbyActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity2 extends AppCompatActivity implements Activity {

    private TextInputLayout code;
    private Button next;
    private String email;
    private String name;
    private String country;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

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
        code = findViewById(R.id.activation_code);
        next = findViewById(R.id.registration_button_5);
    }

    @Override
    public void onListener() {
        next.setOnClickListener(v -> {
            /** use this to check activation code*/
//            attemptRegistration();

            /** delete this*/
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity3.class);
            intent.putExtra("email", email);
            intent.putExtra("login", login);
            intent.putExtra("name", name);
            intent.putExtra("country", country);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void attemptRegistration() {

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
        /** Check activation code*/
        final String codeField = Objects.requireNonNull(code.getEditText()).getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ACTIVATE_USER,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("message").equals("ok")) {
                            Intent intent = new Intent(getApplicationContext(), RegistrationActivity3.class);
                            intent.putExtra("email", email);
                            intent.putExtra("login", login);
                            intent.putExtra("name", name);
                            intent.putExtra("country", country);
//                            SharedPrefManager.getInstance(getApplicationContext())
//                                    .userLogin(
//                                            login,
//                                            name,
//                                            email,
//                                            country,
//                                            "nullk"
//                                    );
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            Toast.makeText(getApplicationContext(), "Activation successful", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("code", codeField);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void registerUser() {

        final String login = this.login;
        final String name = this.name;
        final String email = this.email;
        final String country = this.country;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.POST_USER,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("message").equals("Registered")) {
                            Intent intent = new Intent(getApplicationContext(), RegistrationActivity3.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("email", email);
                params.put("name", name);
                params.put("country", country);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
