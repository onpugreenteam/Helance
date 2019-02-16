package com.ranpeak.ProjectX.activity.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.StartActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.user.data.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity4 extends AppCompatActivity {

    private final static int REGISTRATION_ACTIVITY4 = R.layout.activity_registration4;
    private TextInputLayout registration_username;
    private Button nextButton;
    private boolean loginExists = false;
    private ProgressDialog progressDialog;
    private String email;
    private String name;
    private String country;
    private String gender;
    private String age;
    private String password;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nextButton.setEnabled( checkLogin()
            && isLoginValid()
            );
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY4);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        country = getIntent().getStringExtra("country");
        gender = getIntent().getStringExtra("gender");
        age = getIntent().getStringExtra("age");
        password = getIntent().getStringExtra("password");

        registration_username = findViewById(R.id.registration_username);
        registration_username.getEditText().addTextChangedListener(textWatcher);
        nextButton = findViewById(R.id.registration_button_4);
        nextButton.setEnabled(false);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    public void clickRegistration_4(View view){
        registerUser();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private boolean checkLogin(){
            checkLoginOnServer();
            if (!loginExists) {
//                registration_username.setError(getString(R.string.error_exist_login));
                return true;
            } else {
//                registration_username.setError(null);
                return false;
            }
    }

    private void checkLoginOnServer(){
        final String login = registration_username.getEditText().getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.CHECK_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("login").equals("no")){
                                Toast.makeText(getApplicationContext(),"This login already registered",Toast.LENGTH_LONG);
                                registration_username.setError(getString(R.string.error_exist_login));
                                loginExists = true;

                            }else if(jsonObject.getString("login").equals("ok")){
                                registration_username.setError(null);
                                loginExists = false;
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
    private boolean isLoginValid(){
        if(registration_username.getEditText().getText().length() <= 4){
            registration_username.setError(getText(R.string.error_invalid_login));
            return false;
        }
        registration_username.setError(null);
        return true;
    }

    private void registerUser(){

        final String login = registration_username.getEditText().getText().toString().trim();
        final String password = this.password;
        final String name = this.name;
        final String email = this.email;
        final String age = this.age;
        final String country = this.country;
        final String gender = this.gender;

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.POST_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), StartActivity.class);

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
                params.put("age", age);
                params.put("country", country);
                params.put("gender", gender);
                return params;
            }
        };

        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);

    }

}