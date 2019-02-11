package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.user.data.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private final static int PROFILE_ACTIVITY = R.layout.activity_profile;

    private TextView login;
    private TextView name;
    private TextView email;
    private TextView age;
    private TextView country;
    private TextView gender;

    private ImageView image;
    private static final int PICK_IMAGE = 160;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(PROFILE_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, StartActivity.class));
        }

        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        country = findViewById(R.id.country);
        gender = findViewById(R.id.gender);

        image = findViewById(R.id.user_image_view);

        // Записывание данных о пользователе в нужные поля профиля
        login.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
        name.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserName()));
        email.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserEmail()));
        age.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserAge()));
        country.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserCountry()));
        gender.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserGender()));



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings_for_profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_about:
                Toast.makeText(this,"You clicked about button",
                        Toast.LENGTH_LONG).show();
                break;
            case  R.id.menu_settings:
                Toast.makeText(this,"You clicked settings",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case  R.id.menu_logout:
                SharedPrefManager.getInstance(this).logout();
                Intent intent = new Intent(this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }


}
