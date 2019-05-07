package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myTaskFragment.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.viewModel.TaskViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MyTaskViewActivity extends AppCompatActivity implements Activity {

    private static TaskDTO myTaskItem;
    private static TaskDTO taskDTO;
    private TaskDTO myEditedTaskItem;
    private TaskViewModel taskViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();


    private TextView subject;
    private TextView header;
    private TextView description;
    private TextView userName;
    private TextView userEmail;
    private TextView userCountry;
    private TextView deadline;
    private TextView price;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_view);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        toolbar();
        findViewById();
        initData();
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
        getMenuInflater().inflate(R.menu.menu_my_task, menu);
        return true;
    }
    private void initData() {

        myTaskItem = (TaskDTO) getIntent().getSerializableExtra("MyTask");
        taskDTO = myTaskItem;
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        disposable.add(taskViewModel.getTaskById(myTaskItem.getId())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(val -> {
            taskDTO = val;
        }));
        disposable.dispose();

        setData(taskDTO);
    }

    private void setData(TaskDTO taskDTO) {
        Glide.with(getApplicationContext())
                .load(taskDTO.getUserAvatar())
                .into(avatar);
        subject.setText(taskDTO.getSubject());
        header.setText(taskDTO.getHeadLine());
        description.setText(taskDTO.getDescription());
        deadline.setText(taskDTO.getDeadline());
        price.setText(String.valueOf(taskDTO.getPrice()));
        userCountry.setText(taskDTO.getUserCountry());
        userName.setText(taskDTO.getUserName());
        userEmail.setText(taskDTO.getUserEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // back button on toolbar pressed
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_my_task_edit:
                Intent intent = new Intent(getApplicationContext(), MyTaskEditActivity.class);
                intent.putExtra("MyTask", taskDTO);
//                startActivity(intent);
                startActivityForResult(intent, Constants.Codes.EDIT_CODE);
                break;
            case R.id.menu_my_task_delete:
                Completable.fromRunnable(() -> {
                    taskViewModel.delete(taskDTO);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

            }
        }
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
