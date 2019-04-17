package com.ranpeak.ProjectX.activity.passwordRecovery;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import java.util.Objects;

public class PassRecoveryActivity3 extends AppCompatActivity implements Activity {

    private TextView textView;
    private TextInputLayout newPassword;
    private EditText newPasswordEditText;
    private Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar();
        findViewById();
    }

    @Override
    public void findViewById() {
        textView = findViewById(R.id.password_recovery3_activity_text_view);
        newPassword = findViewById(R.id.new_password__activity_TextInputLayout);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        nextButton = findViewById(R.id.password_recovery3_button);
    }


    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
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

    @Override
    public void onListener() {

    }
}
