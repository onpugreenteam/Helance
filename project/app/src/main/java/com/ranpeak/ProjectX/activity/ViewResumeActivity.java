package com.ranpeak.ProjectX.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewResumeActivity extends AppCompatActivity implements Activity {

    private TextView subject;
    private TextView date;
    private TextView description;
    private LinearLayout linearLayout;
    private CircleImageView imageView;
    private TextView name;
    private TextView email;
    private TextView country;
    private Button connect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resume);
        findViewById();
    }

    @Override
    public void findViewById() {
        subject = findViewById(R.id.activity_resume_view_subject);
        date = findViewById(R.id.activity_resume_view_date);
        description = findViewById(R.id.activity_resume_view_description);
        linearLayout = findViewById(R.id.resume_view_activity_linearLayout);
        imageView = findViewById(R.id.resume_profile_image_view);
        name = findViewById(R.id.activity_resume_view_name);
        country = findViewById(R.id.activity_resume_view_country);
        email= findViewById(R.id.activity_resume_view_email);
        connect = findViewById(R.id.activity_resume_view_button);
    }

    @Override
    public void onListener() {

    }
}
