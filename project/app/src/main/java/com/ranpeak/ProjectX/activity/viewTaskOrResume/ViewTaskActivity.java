package com.ranpeak.ProjectX.activity.viewTaskOrResume;

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
import com.ranpeak.ProjectX.activity.viewTaskOrResume.contact.ContactDialogFragment;
import com.ranpeak.ProjectX.dto.TaskDTO;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewTaskActivity extends AppCompatActivity implements Activity {


    private TextView subject;
    private TextView header;
    private TextView description;
    private LinearLayout linearLayout;
    private CircleImageView imageView;
    private TextView name;
    private TextView email;
    private TextView country;
    private Button contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        findViewById();
        onListener();
        setData();
        Slidr.attach(this);
    }

    @Override
    public void findViewById() {
        subject = findViewById(R.id.activity_task_view_subject);
        header = findViewById(R.id.activity_task_view_header);
        description = findViewById(R.id.activity_task_view_description);
        linearLayout = findViewById(R.id.task_view_activity_linearLayout);
        imageView = findViewById(R.id.profile_image_view);
        name = findViewById(R.id.activity_task_view_name);
        country = findViewById(R.id.activity_task_view_country);
        email= findViewById(R.id.activity_task_view_email);
        contact = findViewById(R.id.activity_task_view_button);
    }


    private void setData(){
        Intent intent = getIntent();
        TaskDTO taskDTO = (TaskDTO) intent.getSerializableExtra("TaskObject");

        subject.setText(taskDTO.getSubject());
        header.setText(taskDTO.getHeadLine());
        description.setText(taskDTO.getDescription());
        name.setText(taskDTO.getUserName());
        email.setText(taskDTO.getUserEmail());
        country.setText(taskDTO.getUserCountry());
    }

    @Override
    public void onListener() {
        contact.setOnClickListener(v -> {
            ContactDialogFragment contactDialogFragment = new ContactDialogFragment();
            contactDialogFragment.show(getSupportFragmentManager(), contactDialogFragment.getTag());
        });
    }
}
