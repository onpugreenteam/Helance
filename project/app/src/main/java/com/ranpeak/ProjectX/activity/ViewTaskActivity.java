package com.ranpeak.ProjectX.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
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
    private Button connect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        findViewById();
        setData();
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
        connect = findViewById(R.id.activity_task_view_button);
    }


    private void setData(){
        Intent intent = getIntent();
        TaskDTO taskDTO = (TaskDTO) intent.getSerializableExtra("TaskObject");

        subject.setText(taskDTO.getSubject());
        header.setText(taskDTO.getHeadLine());
        description.setText(taskDTO.getText());
        name.setText(taskDTO.getEmployee());
    }

    @Override
    public void onListener() {

    }
}
