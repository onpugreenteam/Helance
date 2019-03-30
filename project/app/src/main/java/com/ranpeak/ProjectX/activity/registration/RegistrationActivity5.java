package com.ranpeak.ProjectX.activity.registration;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.LobbyActivity;
import com.ranpeak.ProjectX.activity.logIn.LogInActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity5 extends AppCompatActivity implements Activity {

    private TextInputLayout code;
    private Button next;
    private String email;
    private String name;
    private String country;
    private String login;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration5);

        findViewById();
        onListener();
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
        password = getIntent().getStringExtra("password");
        login = getIntent().getStringExtra("registration_username");
    }

    @Override
    public void findViewById() {
        code = findViewById(R.id.activation_code);
        next = findViewById(R.id.registration_button_5);
    }

    @Override
    public void onListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
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
            activateAccount();
        }
    }

    private void registerUser() {

        final String login = this.login;
        final String password = this.password;
        final String name = this.name;
        final String email = this.email;
        final String country = this.country;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.POST_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("Registered")) {
                                activateAccount();
//                                Intent intent = new Intent(getApplicationContext(), RegistrationActivity5.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);
                params.put("name", name);
                params.put("email", email);
                params.put("country", country);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void activateAccount() {

        final String codeField = code.getEditText().getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ACTIVATE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("ok")) {
                                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                login,
                                                name,
                                                email,
                                                country,
                                                "nullk"
                                        );
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                Toast.makeText(getApplicationContext(), "Activation successful", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }) {

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

    public TextInputLayout getCode() {
        return code;
    }

    public void setCode(TextInputLayout code) {
        this.code = code;
    }

    public Button getNext() {
        return next;
    }

    public void setNext(Button next) {
        this.next = next;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
