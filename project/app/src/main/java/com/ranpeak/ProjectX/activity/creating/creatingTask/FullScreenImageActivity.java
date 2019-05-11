package com.ranpeak.ProjectX.activity.creating.creatingTask;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ranpeak.ProjectX.R;

public class FullScreenImageActivity extends AppCompatActivity {

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
