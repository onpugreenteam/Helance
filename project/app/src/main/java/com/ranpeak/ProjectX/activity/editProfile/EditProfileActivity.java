package com.ranpeak.ProjectX.activity.editProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity implements Activity {

    private EditText name;
    private EditText country;
    private EditText email;
    private EditText telephone;
    private EditText telegram;
    private EditText instgram;
    private Button save;
    private Button password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findViewById();
        onListener();
        toolbar();
        setText();
    }

    @Override
    public void findViewById() {
        name = findViewById(R.id.edit_profile_name_edit_text);
        country = findViewById(R.id.edit_profile_country_edit_text);
        email= findViewById(R.id.edit_profile_email_edit_text);
        telephone= findViewById(R.id.edit_profile_telephone_edit_text);
        telegram = findViewById(R.id.edit_profile_social_network_telegramm_edit_text);
        instgram = findViewById(R.id.edit_profile_social_network_instagramm_edit_text);
        save = findViewById(R.id.edit_profile_save_button);
        password = findViewById(R.id.edit_profile_password_button);
    }

    @Override
    public void onListener() {
        password.setOnClickListener( v-> {

        });
        save.setOnClickListener( v-> {

        });
    }


    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setText(){
        name.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
        name.setSelection(name.getText().length());
        country.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
        email.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
//        telephone.setOpportunities(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserTelephone()));
//        telegram.setOpportunities(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserTelegram()));
//        instgram.setOpportunities(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserInstagram()));
    }
}
