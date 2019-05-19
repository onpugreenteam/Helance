package com.company.Helance.activity.creating.creatingTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.company.Helance.R;
import com.company.Helance.activity.settings.LanguageHelper;
import com.company.Helance.base.BaseActivity;

public class FullScreenImageActivity extends BaseActivity {

    ImageView fullScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        findViewById();

        Intent callingActivityIntent = getIntent();
        if (callingActivityIntent != null) {
            Uri imageUri = callingActivityIntent.getData();
            if (imageUri != null && fullScreenImageView !=null){
                Glide.with(this)
                        .load(imageUri)
                        .into(fullScreenImageView);
            }
        }
    }

    private void findViewById() {
        fullScreenImageView = findViewById(R.id.fullScreenImageView);
    }
}
