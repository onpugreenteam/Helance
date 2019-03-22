package com.ranpeak.ProjectX.activity.registration;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity1 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY1 = R.layout.activity_registration1;

    private Button nextButton;
    private TextInputLayout register_email;
    private boolean email_exists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();

    }

    @Override
    public void findViewById(){
        register_email = findViewById(R.id.registration_email);
        nextButton = findViewById(R.id.registration_button_1);
    }

    @Override
    public void onListener(){
        Objects.requireNonNull(register_email.getEditText()).addTextChangedListener(textWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
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

            boolean isEmailValid = isEmailValid(Objects.requireNonNull(register_email.getEditText()).getText().toString());
            if(!isEmailValid){
                register_email.setError(getString(R.string.error_invalid_email));
            }else {
                register_email.setError(null);
            }
            checkEmail();
            if (email_exists) {
                register_email.setError(getString(R.string.error_exist_email));
            } else {
                register_email.setError(null);
            }
        }
    };

    private void attemptRegistration() {

        // Reset errors.
        register_email.setError(null);

        // Store values at the time of the login attempt.
        String email = Objects.requireNonNull(register_email.getEditText()).getText().toString();

        boolean cancel = false;
        View focusView = null;

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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity2.class);
            intent.putExtra("email",register_email.getEditText().getText().toString().trim());
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    private boolean isEmailValid(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


    private void checkEmail() {
        final String email = Objects.requireNonNull(register_email.getEditText()).getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.CHECK_EMAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("message").equals("no")){
                                Toast.makeText(getApplicationContext(),"This email already registered",Toast.LENGTH_LONG).show();
                                register_email.setError(getString(R.string.error_exist_email));
                                email_exists = true;

                            }else if(jsonObject.getString("message").equals("ok")){
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
                        Toast.makeText(getApplicationContext(), "Please turn on Internet", Toast.LENGTH_LONG).show();
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
}
