package com.ranpeak.ProjectX.activity.viewTaskOrResume;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.viewTaskOrResume.contact.ContactDialogFragment;
import com.ranpeak.ProjectX.dto.ResumeDTO;

import java.util.Objects;

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
    private Button contact;
    private String userEmail;
    private String userName;
    private String userPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_view);
        toolbar();
        findViewById();
        onListener();
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
        email = findViewById(R.id.activity_resume_view_email);
        contact = findViewById(R.id.activity_resume_view_button);
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        Intent intent = getIntent();
        ResumeDTO resumeDTO = (ResumeDTO) intent.getSerializableExtra("ResumeObject");

        userPhone = resumeDTO.getTelephone();
        userEmail = resumeDTO.getUserEmail();
        userName = resumeDTO.getUserName();
        subject.setText(resumeDTO.getSubject());
        description.setText(resumeDTO.getOpportunities());
        name.setText(resumeDTO.getUserName());
        email.setText(resumeDTO.getUserEmail());
        country.setText(resumeDTO.getUserCountry());
        date.setText(resumeDTO.getDateStart());
    }

    @Override
    public void onListener() {
        contact.setOnClickListener(v -> {
            ContactDialogFragment contactDialogFragment = new ContactDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.phone), userPhone);
            bundle.putString(getString(R.string.email), userEmail);
            bundle.putString(getString(R.string.name), userName);
            contactDialogFragment.setArguments(bundle);
            contactDialogFragment.show(getSupportFragmentManager(), contactDialogFragment.getTag());
        });
    }
}
