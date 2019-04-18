package com.ranpeak.ProjectX.activity.lobby.forGuestUsers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

public class LobbyForGuestActivity extends AppCompatActivity implements Activity {

    private final static int LOBBY_FOR_GUEST_ACTIVITY = R.layout.activity_lobby_for_guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOBBY_FOR_GUEST_ACTIVITY);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void onListener() {

    }
}
