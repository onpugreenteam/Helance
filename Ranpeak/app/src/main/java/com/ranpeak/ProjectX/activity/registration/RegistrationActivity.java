package com.ranpeak.ProjectX.activity.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.StartActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.user.data.RequestHandler;
import com.sun.mail.imap.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegistrationActivity extends AppCompatActivity implements TextWatcher{

    private final static int REGISTRATION_ACTIVITY = R.layout.activity_registration;


    private EditText register_login;
    private EditText register_password;
    private EditText register_name;
    private EditText register_email;
    private EditText register_age;
//    private EditText register_country;
    private EditText register_gender;

    private AutoCompleteTextView autoCompleteTextViewCountry;
    private Button register_button;

    private ImageView iconInRegister;

    private ProgressDialog progressDialog;

    private boolean login_exists = false;

    private boolean email_exists = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Находим и передаем локальным переменным обьекты activity_registration
        register_login = findViewById(R.id.register_login);
        register_password = findViewById(R.id.register_password);
        register_name = findViewById(R.id.register_name);
        register_email =findViewById(R.id.register_email);
        register_age = findViewById(R.id.register_age);
//        register_country = findViewById(R.id.register_country);
        register_gender = findViewById(R.id.register_gender);

        autoCompleteTextViewCountry = findViewById(R.id.register_country);
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Constants.Values.COUNTRIES);
        autoCompleteTextViewCountry.setAdapter(adapterCountry);

        register_button = findViewById(R.id.register_button);
        iconInRegister = findViewById(R.id.IconInRegister);

        register_login.addTextChangedListener(this);
        register_email.addTextChangedListener(this);
        register_password.addTextChangedListener(this);
        register_name.addTextChangedListener(this);
        register_age.addTextChangedListener(this);
        autoCompleteTextViewCountry.addTextChangedListener(this);
        register_gender.addTextChangedListener(this);

//        register_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    attemptRegistration();
//                    return true;
//                }
//                return false;
//            }
//        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });


        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(register_login.getText().hashCode() == s.hashCode()) {
            checkLogin();
            if (login_exists) {
                register_login.setError(getString(R.string.error_exist_login));
            } else {
                register_login.setError(null);
            }
        }else if(register_password.getText().hashCode() == s.hashCode()){
            if (!isPasswordValid(register_password.getText().toString())) {
                register_password.setError(getString(R.string.error_invalid_password));
            }
        }else if(register_email.getText().hashCode() == s.hashCode()){
            boolean isEmailValid = isEmailValid(register_email.getText().toString());
            if(!isEmailValid){
                register_email.setError(getString(R.string.error_invalid_email));
            }else if(isEmailValid){
                register_email.setError(null);
            }
            checkEmail();
            if (email_exists) {
                register_email.setError(getString(R.string.error_exist_email));
            } else {
                register_email.setError(null);
            }
        }else if(autoCompleteTextViewCountry.getText().hashCode() == s.hashCode()){
            if (!stringContainsItemFromList(
                    autoCompleteTextViewCountry.getText().toString(),
                    Constants.Values.COUNTRIES)) {
                autoCompleteTextViewCountry.setError(getString(R.string.error_exist_email));
            }
            } else {
                autoCompleteTextViewCountry.setError(null);
            }
    }




    private void attemptRegistration() {

        // Reset errors.
        register_login.setError(null);

        // Store values at the time of the login attempt.
        String login = register_login.getText().toString();
        String password = register_password.getText().toString();
        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String age = register_age.getText().toString();
        String country = autoCompleteTextViewCountry.getText().toString();
        String gender = register_gender.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid gender, if the user entered one.
        if (TextUtils.isEmpty(gender)) {
            register_gender.setError(getString(R.string.error_field_required));
            focusView = register_gender;
            cancel = true;
        }
//        else if (!isGenderValid(gender)) {
//            register_gender.setError(getString(R.string.error_invalid_gender));
//            focusView = register_gender;
//            cancel = true;
//        }

        // Check for a valid country, if the user entered one.
        if (TextUtils.isEmpty(country)) {
            autoCompleteTextViewCountry.setError(getString(R.string.error_field_required));
            focusView = autoCompleteTextViewCountry;
            cancel = true;
        }else if(!stringContainsItemFromList(country, Constants.Values.COUNTRIES)){
            autoCompleteTextViewCountry.setError(getString(R.string.choosedWrongCoutry));
            focusView = autoCompleteTextViewCountry;
            cancel = true;
        }
//        else if (!isCountryValid(country)) {
//            register_country.setError(getString(R.string.error_invalid_password));
//            focusView = register_country;
//            cancel = true;
//        }

        // Check for a valid age, if the user entered one.
        if (TextUtils.isEmpty(age)) {
            register_age.setError(getString(R.string.error_field_required));
            focusView = register_age;
            cancel = true;
        }
//        else if (!isAgeValid(age)) {
//            register_age.setError(getString(R.string.error_invalid_password));
//            focusView = register_age;
//            cancel = true;
//        }

        // Check for a valid email, if the user entered one.
        if (TextUtils.isEmpty(email)) {
            register_email.setError(getString(R.string.error_field_required));
            focusView = register_email;
            cancel = true;
        }else if (!isEmailValid(email)) {
            register_email.setError(getString(R.string.error_invalid_email));
            focusView = register_email;
            cancel = true;
        }else if(!TextUtils.isEmpty(email) && isEmailValid(email)){
            checkEmail();
            if(email_exists){
                register_email.setError(getString(R.string.error_exist_email));
                focusView = register_email;
                cancel = true;
            }
        }
//        else if(!email_exists){
//            register_email.setError(getString(R.string.error_invalid_email));
//            focusView = register_email;
//            cancel = true;
//        }

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            register_name.setError(getString(R.string.error_field_required));
            focusView = register_name;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            register_password.setError(getString(R.string.error_field_required));
            focusView = register_password;
            cancel = true;
        }else if (!isPasswordValid(password)) {
            register_password.setError(getString(R.string.error_invalid_password));
            focusView = register_password;
            cancel = true;
        }

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            register_login.setError(getString(R.string.error_field_required));
            focusView = register_login;
            cancel = true;
        } else if (!isLoginValid(login)) {
            register_login.setError(getString(R.string.error_invalid_login));
            focusView = register_login;
            cancel = true;
        } else if(!TextUtils.isEmpty(login) && isLoginValid(login)){
            checkLogin();
            if(login_exists){
                register_login.setError(getString(R.string.error_exist_login));
                focusView = register_login;
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


    private static boolean isEmpty(EditText editText) {

        String input = editText.getText().toString().trim();
        return input.length() == 0;

    }

    private boolean isLoginValid(String login) {
        //TODO: Replace this with your own logic

        return login.length() > 4;
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isGenderValid(String gender) {
        return gender.equals("male") || gender.equals("female");
    }

    private boolean isAgeValid(String age){
        return false;
    }

    private boolean isEmailValid(String email){
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//
//        return EmailValidator.getInstance().isValid(email);
        boolean isValid = false;
        try {
            //
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return isValid;
    }


    private void checkEmail() {

        final String email = register_email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.CHECK_EMAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("login").equals("no")){
                                Toast.makeText(getApplicationContext(),"This email already registered",Toast.LENGTH_LONG);
                                register_email.setError(getString(R.string.error_exist_email));
                                email_exists = true;

                            }else if(jsonObject.getString("login").equals("ok")){
                                register_email.setError(null);
                                email_exists = false;
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
                        Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };

        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }



    private void checkLogin(){

        final String login = register_login.getText().toString().trim();

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
                                register_login.setError(getString(R.string.error_exist_login));
                                login_exists = true;

                            }else if(jsonObject.getString("login").equals("ok")){
                                register_login.setError(null);
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



    private void registerUser(){

        final String login = register_login.getText().toString().trim();
        final String password = register_password.getText().toString().trim();
        final String name = register_name.getText().toString().trim();
        final String email = register_email.getText().toString().trim();
        final String age = register_age.getText().toString().trim();
        final String country = autoCompleteTextViewCountry.getText().toString().trim();
        final String gender = register_gender.getText().toString().trim();


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

    private static boolean stringContainsItemFromList(String inputStr, String[] items)
    {
        for(int i =0; i < items.length; i++)
        {
            if(inputStr.contains(items[i]))
            {
                return true;
            }
        }
        return false;
    }
}
