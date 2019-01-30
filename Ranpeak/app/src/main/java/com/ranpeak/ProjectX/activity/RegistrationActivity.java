package com.ranpeak.ProjectX.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.constant.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int REGISTRATION_ACTIVITY = R.layout.activity_registration;


    private EditText register_login;
    private EditText register_password;
    private EditText register_name;
    private EditText register_age;
    private EditText register_country;
    private EditText register_gender;

    private Button register_button;

    private ImageView iconInRegister;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Находим и передаем локальным переменным обьекты activity_registration
        register_login = findViewById(R.id.register_login);
        register_password = findViewById(R.id.register_password);
        register_name = findViewById(R.id.register_name);
        register_age = findViewById(R.id.register_age);
        register_country = findViewById(R.id.register_country);
        register_gender = findViewById(R.id.register_gender);

        register_button = findViewById(R.id.register_button);
        iconInRegister = findViewById(R.id.IconInRegister);

        progressDialog = new ProgressDialog(this);
        register_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==register_button){
            registerUser();
            Intent intent = new Intent(getApplicationContext(),StartActivity.class);
            startActivity(intent);
        }

    }


    private void registerUser(){

        final String login = register_login.getText().toString().trim();
        final String password = register_password.getText().toString().trim();
        final String name = register_name.getText().toString().trim();
        final String age = register_age.getText().toString().trim();
        final String country = register_country.getText().toString().trim();
        final String gender = register_gender.getText().toString().trim();


        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.POST_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("Error"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("login",login);
                params.put("password",password);
                params.put("name", name);
                params.put("age", age);
                params.put("country", country);
                params.put("gender", gender);
                return params;
            }
            };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


//     private class  RESTTask extends AsyncTask<String, Void, ResponseEntity<Users>> {
//
//        protected ResponseEntity<Users> doInBackground(String... uri) {
//
//            final String url = uri[0];
//            RestTemplate restTemplate = new RestTemplate();
//
//            try{
//                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//
//                HttpHeaders headers = new HttpHeaders();
//
//                headers.set("APIKey", "5567GGH67225HYVGG");
//
//                String auth = "Jack" + ":" + "Jones";
//
//                String encodeAuth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
//                String authHeader = "Basic" + new String(encodeAuth);
//                headers.set("Authorization", authHeader);
//
//                HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//                ResponseEntity<Users> response = restTemplate.exchange(url, HttpMethod.GET, entity, Users.class);
//
//                return response;
//
//        }catch (Exception ex){
//
//                String message = ex.getMessage();
//                return null;
//            }
//        }
//
//        protected void OnPostExecute(ResponseEntity<Users> result){
//            HttpStatus statusCode = result.getStatusCode();
//            Users userReturned = result.getBody();
//        }
//
//    }
//
//    public  void sendMessage(View view){
//        final String uri = "http://localhost:8080/getallusers/1";
//        new RESTTask().execute(uri);
//    }

}
