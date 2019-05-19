package com.company.Helance.activity.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.company.Helance.R;
import com.company.Helance.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyProfileViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.activity.lobby.forGuestUsers.LobbyForGuestActivity;
import com.company.Helance.settingsApp.SharedPrefManager;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements Activity {

    private Spinner language;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private Button changePassword;
    private Button logOut;

    private String baseLang;
    private Language lang = new Language();


    // override the base context of application to update default locale for this activity
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById();
        toolbar();
        onListener();
        spinner();
    }

    @Override
    public void findViewById() {
        language = findViewById(R.id.settings_activity_language);
        checkBox1 = findViewById(R.id.settings_activity_checkBox_1);
        checkBox2 = findViewById(R.id.settings_activity_checkBox_2);
        changePassword = findViewById(R.id.settings_activity_button_password);
        logOut = findViewById(R.id.settings_activity_button_log_out);
    }

    @Override
    public void onListener() {
        logOut.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).logout();
            removeAllDataFromLocalDB();
            Intent intent = new Intent(this, LobbyForGuestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        changePassword.setOnClickListener((v) -> Toast.makeText(this, getString(R.string.is_developing), Toast.LENGTH_SHORT).show());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    private void toolbar() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_name);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_save) {
            if( !lang.getLanguageCode().equals(baseLang) ) {
                switchLanguage(SettingsActivity.this, lang.getLanguageCode());
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);

    }

    public void removeAllDataFromLocalDB() {

        MyProfileViewModel profileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        profileViewModel.deleteAllSocialNetworks();

        MyTaskViewModel taskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        taskViewModel.deleteAllUsersTasks();

        MyResumeViewModel resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
        resumeViewModel.deleteAllUsersResumes();

    }

    private void spinner() {

        ArrayAdapter mAdapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ln));
        language.setAdapter(mAdapter);
        for (int i =0; i < language.getCount(); i++) {
            if (LanguageHelper.getLanguage(SettingsActivity.this).equals("ru")) {
                language.setSelection(1);
            } else if (LanguageHelper.getLanguage(SettingsActivity.this).equals("en")) {
                language.setSelection(0);
            }
        }

        if (LanguageHelper.getLanguage(SettingsActivity.this).equals("ru")) {
            baseLang = "ru";
        } else if (LanguageHelper.getLanguage(SettingsActivity.this).equals("en")) {
            baseLang = "en";
        }

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        lang.setLanguageCode("en");
                        lang.setLanguageName("en");
                        lang.setLanguageNameInDefaultLocale("en");

                        Log.d("lang_selected", lang.getLanguageCode());
                        break;
                    case 1:

                        lang.setLanguageCode("ru");
                        lang.setLanguageName("ru");
                        lang.setLanguageNameInDefaultLocale("ru");

                        Log.d("lang_selected", lang.getLanguageCode());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void switchLanguage(android.app.Activity activity, String languageCode) {

        LanguageHelper.setLanguage(activity, languageCode);
        Log.d("lang_code", LanguageHelper.getLanguage(SettingsActivity.this));
        relaunch();

    }

    public void relaunch (){

        Intent i = new Intent(SettingsActivity.this, LobbyActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }
}
