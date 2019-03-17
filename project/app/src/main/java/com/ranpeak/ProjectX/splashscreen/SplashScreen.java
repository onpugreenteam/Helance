package com.ranpeak.ProjectX.splashscreen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.internetConnection.InternetErrorActivity;
import com.ranpeak.ProjectX.activity.logIn.LogInActivity;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;

public class SplashScreen extends AppCompatActivity/* AwesomeSplash*/ {

    private final static int SPLASH_ACTIVITY = R.layout.activity_splash_screen;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(SPLASH_ACTIVITY);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        animate();
        getAllLogins();
    }

    @Override
    public void finish() {
        startActivity(new Intent(SplashScreen.this, LogInActivity.class));
        super.finish();
    }

    private void noInternetConnection() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreen.this, InternetErrorActivity.class));
                    end();
                }
            }
        };
        timer.start();
    }
    private void end(){
        super.finish();
    }

    private void findViewById() {
        textView = findViewById(R.id.splash_text);
        imageView = findViewById(R.id.splash_image);
    }

    private void animate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);
    }

    private void startLoadData(){
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        timer.start();
    }

    private void getAllLogins(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL.GET_LOGINS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Data downloading", Toast.LENGTH_SHORT).show();
                        startLoadData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Waiting...", Toast.LENGTH_SHORT).show();
                        noInternetConnection();
                    }
        });
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }

}
