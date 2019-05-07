package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTaskViewActivity extends AppCompatActivity implements Activity {

    private TaskDTO myTaskItem;

    private TaskDTO myEditedTaskItem;

    private TextView subject;
    private TextView header;
    private TextView description;
    private TextView userName;
    private TextView userEmail;
    private TextView userCountry;
    private TextView deadline;
    private TextView price;
    private CircleImageView avatar;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_view);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        toolbar();
        findViewById();
        setData();
        onListener();
        Slidr.attach(this);
    }

    @Override
    public void findViewById() {
        avatar = findViewById(R.id.activity_view_my_task_avatar);
        subject = findViewById(R.id.activity_view_my_task_subject);
        header = findViewById(R.id.activity_view_my_task_header);
        description = findViewById(R.id.activity_view_my_task_description);
        deadline = findViewById(R.id.activity_view_my_task_deadline);
        price = findViewById(R.id.activity_view_my_task_price);
        userName = findViewById(R.id.activity_view_my_task_user_name);
        userEmail = findViewById(R.id.activity_view_my_task_user_email);
        userCountry = findViewById(R.id.activity_view_my_task_user_country);
    }

    @Override
    public void onListener() {

    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_task_menu, menu);
        return true;
    }

    private void setData() {

        myTaskItem = (TaskDTO) getIntent().getSerializableExtra("MyTask");
        Glide.with(getApplicationContext())
                .load(myTaskItem.getUserAvatar())
                .into(avatar);
        subject.setText(myTaskItem.getSubject());
        header.setText(myTaskItem.getHeadLine());
        description.setText(myTaskItem.getDescription());
        deadline.setText(myTaskItem.getDeadline());
        price.setText(String.valueOf(myTaskItem.getPrice()));
        userCountry.setText(myTaskItem.getUserCountry());
        userName.setText(myTaskItem.getUserName());
        userEmail.setText(myTaskItem.getUserEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // back button on toolbar pressed
            case android.R.id.home:
                finish();
                return true;
            case R.id.my_task_menu_edit:
                Intent intent = new Intent(getApplicationContext(), MyTaskEditActivity.class);
                intent.putExtra("MyTask", myTaskItem);
                startActivityForResult(intent, Constants.Codes.EDIT_CODE);
                break;
            case R.id.my_task_menu_delete:
                taskViewModel.delete(myTaskItem);
                // sending result code = 1 after user click delete item to previous activity
//                Intent resultIntent = new Intent();
//                setResult(Constants.Codes.DELETE_CODE, resultIntent);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.Codes.EDIT_CODE
                && resultCode == Constants.Codes.EDIT_CODE) {
            if (data != null) {
                myEditedTaskItem = (TaskDTO) data.getSerializableExtra("EditedTask");
                setEditedData(myEditedTaskItem);
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("EditedTask", myEditedTaskItem);
//                setResult(Constants.Codes.EDIT_CODE, resultIntent);

            }
//            else {
//                Toast.makeText(getApplicationContext(), "no_data", Toast.LENGTH_SHORT).show();
//            }
        }
//        else {
//
//            Toast.makeText(getApplicationContext(), "desc_no", Toast.LENGTH_SHORT).show();
//        }
    }

    private void setEditedData(TaskDTO editedData) {
        myTaskItem = editedData;

        subject.setText(editedData.getSubject());
        header.setText(editedData.getHeadLine());
        description.setText(editedData.getDescription());
        deadline.setText(editedData.getDeadline());
        price.setText(String.valueOf(editedData.getPrice()));
    }
}
