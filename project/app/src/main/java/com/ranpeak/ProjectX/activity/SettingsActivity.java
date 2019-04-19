package com.ranpeak.ProjectX.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private TextView searchText;
    private TextView receiveNotification;
    private CheckBox checkBox1;
    private TextView receiveeEmailNotifications;
    private CheckBox checkBox2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initSettings();
        toolbar();
    }

    private void initSettings() {
        searchText = findViewById(R.id.settings_activity_search_text);
        receiveNotification = findViewById(R.id.settings_activity_receive_notification_text);
        checkBox1 = findViewById(R.id.settings_activity_checkBox_1);
        receiveeEmailNotifications = findViewById(R.id.settings_activity_receive_email_notifications_text);
        checkBox2 = findViewById(R.id.settings_activity_checkBox_2);
        button = findViewById(R.id.settings_activity_button);
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
}
