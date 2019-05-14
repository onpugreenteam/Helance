package com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.myResumeFragment.resume;

import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creating.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.dto.MyResumeDTO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.networking.IsOnline;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.settings.SharedPrefManager;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;

import java.util.Objects;

public class MyResumeEditActivity extends AppCompatActivity implements Activity {

    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();

    private MyResumeDTO myResumeItem;
    private MyResumeDTO editedResume;
    private MyResumeViewModel resumeViewModel;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

    private Button save;
    private TextView subject;
    private TextView opportunities;
    private TextView opportunitiesLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume_edit);
        toolbar();
        findViewById();
        initData();
        onListener();

        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
    }

    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void findViewById() {
        save = findViewById(R.id.activity_my_resume_edit_save);
        subject = findViewById(R.id.activity_my_resume_edit_subject);
        opportunities = findViewById(R.id.activity_my_resume_edit_opportunities);
        opportunitiesLength = findViewById(R.id.activity_my_resume_edit_opportunities_length);
    }

    @Override
    public void onListener() {
        save.setOnClickListener(v -> attemptEditingResume());
        opportunities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                opportunitiesLength.setText(opportunities.getText().toString().length() + "/");
            }
        });

        subject.setOnClickListener(v -> lessonListFragment.show(fm, "Lesson lists"));
    }

    private void initData() {
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
        myResumeItem = (MyResumeDTO) getIntent().getSerializableExtra("MyResume");
        opportunitiesLength.setText(opportunities.getText().toString().length() + "/");
        subject.setText(myResumeItem.getSubject());
        opportunities.setText(myResumeItem.getOpportunities());
    }

    public void setLessonPicker(String lessonPicker) {
        subject.setText(lessonPicker);
    }

    private void attemptEditingResume() {

        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {

            // reset errors
            opportunities.setError(null);

            boolean cancel = false;
            boolean changed = true;
            View focusView = null;

            if (opportunities.getText().toString().equals("")) {
                cancel = true;
                focusView = opportunities;
            } else if (equalFields(

                    (MyResumeDTO) getIntent().getSerializableExtra("MyResume"))) {
                changed = false;
            }
            if (!changed) {
                Toast.makeText(getApplicationContext(), "nothing changed", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (changed && cancel) {
                Toast.makeText(getApplicationContext(), "feel all required fields", Toast.LENGTH_SHORT).show();
                if (focusView != null) {
                    focusView.requestFocus();
                }
            } else {

                //            if (fieldsAreCorrect()) {
                editedResume = new MyResumeDTO();
                editedResume.setSubject(subject.getText().toString());
                editedResume.setOpportunities(opportunities.getText().toString());
                editedResume.setDateStart(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getDateStart());
                editedResume.setStatus(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getStatus());
                editedResume.setUserLogin(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
                editedResume.setUserEmail(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
                editedResume.setUserName(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
                editedResume.setUserCountry(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
                editedResume.setUserAvatar(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()));
                editedResume.setViews(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getViews());
                /** Использовать этот метод для обновления задания в бд (только в бд)*/
                editResume(editedResume);

                updateOnServer(editedResume);
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean fieldsAreCorrect() {
        return stringContainsItemFromList(subject.getText().toString())
                && !opportunities.getText().toString().equals("");
    }

    // check if objects fields are identical
    private boolean equalFields(MyResumeDTO item2) {
        MyResumeDTO item1 = new MyResumeDTO();
        item1.setSubject(subject.getText().toString());
        item1.setOpportunities(opportunities.getText().toString());
        item1.setDateStart(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getDateStart());
        item1.setStatus(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getStatus());
        item1.setUserLogin(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
        item1.setUserEmail(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
        item1.setUserName(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
        item1.setUserCountry(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserCountry()));
        item1.setUserAvatar(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserAvatar()));
        item1.setViews(((MyResumeDTO) getIntent().getSerializableExtra("MyResume")).getViews());

        return item1.getSubject().equals(item2.getSubject())
                && item1.getOpportunities().equals(item2.getOpportunities())
                && item1.getDateStart().equals(item2.getDateStart())
                && item1.getStatus().equals(item2.getStatus());
//                && item1.getFileTasks().equals(item2.getFileTasks());
    }

    //проверяет входит ли какое-либо значение в какой-либо указанный массив
    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr) {
        for (String item : Constants.Values.LESSONS) {
            if (inputStr.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private void editResume(MyResumeDTO editedResume) {
        editedResume.setId(myResumeItem.getId());
//        Completable.fromRunnable(() -> {
//            taskViewModel.update(editedTask);
//        })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
        resumeViewModel.update(editedResume);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("EditedResume", editedResume);
        setResult(Constants.Codes.EDIT_CODE, resultIntent);
        finish();
    }

    private void updateOnServer(MyResumeDTO editedResume) {

    }
}
