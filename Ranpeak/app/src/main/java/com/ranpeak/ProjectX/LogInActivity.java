package com.ranpeak.ProjectX;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void ClickLogInLobby(View view){
        Intent intent = new Intent(getApplicationContext(), Lobby.class);
        startActivity(intent);
    }

    public void ClickRegistration(View view){
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

}
