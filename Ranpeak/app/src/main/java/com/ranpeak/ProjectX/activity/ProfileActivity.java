package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.user.data.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private final static int PROFILE_ACTIVITY = R.layout.activity_profile;

    private TextView login;

    private TextView name;
    private TextView age;
    private TextView country;
    private TextView gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(PROFILE_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }

        login = (TextView) findViewById(R.id.login);
        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);
        country = (TextView) findViewById(R.id.country);
        gender = (TextView) findViewById(R.id.gender);

        login.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
        name.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserName()));
        age.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserAge()));
        country.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserCountry()));
        gender.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserGender()));
        //System.out.println(SharedPrefManager.getInstance(this).getUserAge());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings_for_profile, menu);
        return true;
    }
}
