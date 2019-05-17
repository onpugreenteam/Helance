package com.company.Helance.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.company.Helance.R;
import com.company.Helance.activity.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyProfileViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.activity.lobby.forGuestUsers.LobbyForGuestActivity;
import com.company.Helance.settings.SharedPrefManager;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements Activity {

    private TextView searchText;
    private TextView receiveNotification;
    private CheckBox checkBox1;
    private TextView receiveeEmailNotifications;
    private CheckBox checkBox2;
    private Button button;
    private Button logOut;
    private MyTaskViewModel taskViewModel;
    private MyResumeViewModel resumeViewModel;
    private MyProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById();
        onListener();
        toolbar();
    }

    @Override
    public void findViewById() {
        searchText = findViewById(R.id.settings_activity_search_text);
        receiveNotification = findViewById(R.id.settings_activity_receive_notification_text);
        checkBox1 = findViewById(R.id.settings_activity_checkBox_1);
        receiveeEmailNotifications = findViewById(R.id.settings_activity_receive_email_notifications_text);
        checkBox2 = findViewById(R.id.settings_activity_checkBox_2);
        button = findViewById(R.id.settings_activity_button);
        logOut = findViewById(R.id.settings_activity_button_log_out);
    }

    @Override
    public void onListener() {
        logOut.setOnClickListener(v->{
            SharedPrefManager.getInstance(this).logout();
            removeAllDataFromLocalDB();
            Intent intent = new Intent(this, LobbyForGuestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        button.setOnClickListener((v) -> {
            finish();
        });
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void removeAllDataFromLocalDB() {
        profileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        profileViewModel.deleteAllSocialNetworks();
        taskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        taskViewModel.deleteAllUsersTasks();
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
        resumeViewModel.deleteAllUsersResumes();
    }
}
