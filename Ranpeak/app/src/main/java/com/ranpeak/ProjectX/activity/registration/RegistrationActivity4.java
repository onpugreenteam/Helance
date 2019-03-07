package com.ranpeak.ProjectX.activity.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.logIn.LogInActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity4 extends AppCompatActivity {

    private final static int REGISTRATION_ACTIVITY4 = R.layout.activity_registration4;

    private TextInputLayout registration_username;
    private Button nextButton;
    private ProgressDialog progressDialog;
    private String email;
    private String name;
    private String country;
    private String password;
    private boolean login_exists = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY4);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
        password = getIntent().getStringExtra("password");

        findViewById();

        Objects.requireNonNull(registration_username.getEditText()).addTextChangedListener(textWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }


    private void findViewById(){
        registration_username = findViewById(R.id.registration_username);
        nextButton = findViewById(R.id.registration_button_4);
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
            checkLogin();
            if (login_exists) {
                registration_username.setError(getString(R.string.error_exist_login));
            } else {
                registration_username.setError(null);
            }
        }
    };


    private void attemptRegistration() {

        // Reset errors.
        registration_username.setError(null);

        // Store values at the time of the login attempt.
        String login = Objects.requireNonNull(registration_username.getEditText()).getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            registration_username.setError(getString(R.string.error_field_required));
            focusView = registration_username;
            cancel = true;
        } else if (!isLoginValid(login)) {
            registration_username.setError(getString(R.string.error_invalid_login));
            focusView = registration_username;
            cancel = true;
        } else if(!TextUtils.isEmpty(login) && isLoginValid(login)){
            checkLogin();
            if(login_exists){
                registration_username.setError(getString(R.string.error_exist_login));
                focusView = registration_username;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            registerUser();
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void checkLogin(){
        final String login = Objects.requireNonNull(registration_username.getEditText()).getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.CHECK_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("message").equals("no")){
                                Toast.makeText(getApplicationContext(),"This login already registered",Toast.LENGTH_LONG).show();
                                registration_username.setError(getString(R.string.error_exist_login));
                                login_exists = true;

                            }else if(jsonObject.getString("message").equals("ok")){
                                registration_username.setError(null);
                                login_exists = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),"Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("login",login);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }



    private boolean isLoginValid(String login) {
        return login.length() > 4;
    }


    private void registerUser(){

        final String login = Objects.requireNonNull(registration_username.getEditText()).getText().toString().trim();
        final String password = this.password;
        final String name = this.name;
        final String email = this.email;
        final String country = this.country;

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.POST_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        Toast.makeText(getApplicationContext(),"Registration successful", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),"Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("login",login);
                params.put("password",password);
                params.put("name", name);
                params.put("email",email);
                params.put("country", country);
                return params;
            }
        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}