package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.ResumeDTO;

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
        setData();
        Slidr.attach(this);
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

    private void setData(){
        Intent intent = getIntent();
        ResumeDTO resumeDTO = (ResumeDTO) intent.getSerializableExtra("ResumeObject");

        subject.setText(resumeDTO.getSubject());
        description.setText(resumeDTO.getOpportunities());
        name.setText(resumeDTO.getUserName());
        email.setText(resumeDTO.getUserEmail());
        country.setText(resumeDTO.getUserCountry());
        date.setText(resumeDTO.getDateStart());
    }

    @Override
    public void onListener() {

    }
}
