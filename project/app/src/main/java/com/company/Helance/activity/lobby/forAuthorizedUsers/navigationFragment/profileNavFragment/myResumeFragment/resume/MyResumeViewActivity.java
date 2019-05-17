package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myResumeFragment.resume;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.dto.MyResumeDTO;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.retrofit.ApiService;
import com.company.Helance.networking.retrofit.RetrofitClient;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MyResumeViewActivity extends AppCompatActivity implements Activity {

    private static MyResumeDTO myResumeItem;
    private static MyResumeDTO resumeDTO;
    private MyResumeDTO myEditedResumeItem;
    private MyResumeViewModel resumeViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

    private TextView subject;
    private TextView opportunities;
    private TextView userName;
    private TextView userEmail;
    private TextView userCountry;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume_view);

        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);

        toolbar();
        findViewById();
        initData();
        onListener();
        Slidr.attach(this);
    }

    @Override
    public void findViewById() {
        subject = findViewById(R.id.activity_my_resume_view_subject);
        opportunities = findViewById(R.id.activity_my_resume_view_opportunities);
        userName = findViewById(R.id.activity_my_resume_view_user_name);
        userEmail = findViewById(R.id.activity_my_resume_view_user_email);
        userCountry = findViewById(R.id.activity_my_resume_view_user_country);
        avatar = findViewById(R.id.activity_my_resume_view_avatar);
    }

    @Override
    public void onListener() {

    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // back button on toolbar pressed
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_my_task_edit:
                Intent intent = new Intent(getApplicationContext(), MyResumeEditActivity.class);
                intent.putExtra("MyResume", resumeDTO);
//                startActivity(intent);
                startActivityForResult(intent, Constants.Codes.EDIT_CODE);
                break;
            case R.id.menu_my_task_delete:
                if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
                    resumeViewModel.delete(resumeDTO);
                } else {
                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initData() {

        myResumeItem = (MyResumeDTO) getIntent().getSerializableExtra("MyResume");
        resumeDTO = myResumeItem;
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);

        disposable.add(resumeViewModel.getResumeById(myResumeItem.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val -> {
                    resumeDTO = val;
                }));
        disposable.dispose();

        setData(resumeDTO);
    }

    private void setData(MyResumeDTO resumeDTO) {
//        Glide.with(getApplicationContext())
//                .load(resumeDTO.getUserAvatar())
//                .into(avatar);
        subject.setText(resumeDTO.getSubject());
        opportunities.setText(resumeDTO.getOpportunities());
        userCountry.setText(resumeDTO.getUserCountry());
        userName.setText(resumeDTO.getUserName());
        userEmail.setText(resumeDTO.getUserEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.Codes.EDIT_CODE
                && resultCode == Constants.Codes.EDIT_CODE) {
            if (data != null) {
                myEditedResumeItem = (MyResumeDTO) data.getSerializableExtra("EditedResume");
                setEditedData(myEditedResumeItem);

            }
        }
    }

    private void setEditedData(MyResumeDTO editedData) {
        myResumeItem = editedData;

        subject.setText(editedData.getSubject());
        opportunities.setText(editedData.getOpportunities());
    }
}
