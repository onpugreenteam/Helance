package com.company.Helance.activity.editProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.company.Helance.R;
import com.company.Helance.activity.interfaces.Activity;

public class EditProfilePasswordActivity extends AppCompatActivity implements Activity {

    private TextInputLayout oldPassword;
    private TextInputLayout newPassword;
    private EditText confirmPassword;
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_password);
        findViewById();
    }

    @Override
    public void findViewById() {
        oldPassword = findViewById(R.id.edit_profile_password_old_TextInputLayout);
        newPassword = findViewById(R.id.edit_profile_password_new_TextInputLayout);
        confirmPassword = findViewById(R.id.edit_profile_сonfirm_password_edit_text);
        save = findViewById(R.id.edit_profile_save_button);
    }

    @Override
    public void onListener() {

    }

}
