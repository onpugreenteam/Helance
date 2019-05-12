package com.ranpeak.ProjectX.activity.creating.creatingResume;

import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creating.LessonListFragment;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.networking.volley.Constants;
import com.ranpeak.ProjectX.networking.volley.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CreatingResumeActivity extends AppCompatActivity implements Activity {

    private final static int CREATING_RESUME_ACTIVITY = R.layout.activity_creating_resume;

    private EditText description;
    private TextView descriptionLength;
    private TextView lessonPicker;
    private final FragmentManager fm = getFragmentManager();
    private final LessonListFragment lessonListFragment = new LessonListFragment();
    private MyResumeViewModel resumeViewModel;
    private Button create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CREATING_RESUME_ACTIVITY);

        findViewById();
        onListener();
        toolbar();
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);

    }

    @Override
    public void findViewById() {
        lessonPicker = findViewById(R.id.creating_resume_lesson_list_in_resume);
        description = findViewById(R.id.creating_resume_description);
        descriptionLength = findViewById(R.id.creating_resume_description_length);
        create = findViewById(R.id.creating_resume_button);
    }

    @Override
    public void onListener() {
        lessonPicker.setOnClickListener(v -> lessonListFragment.show(fm, "Country lists"));
        create.setOnClickListener(view -> attemptCreatingResume());
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                descriptionLength.setText(description.getText().toString().length() + "/");
            }
        });
    }

    @Override
    public void onBackPressed() {
        // если пустых полей нет, то открывается диалог с потверждение закрытия окна
        if (!allFieldsEmpty()) {
            openDialog();
        }
        // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
        else super.onBackPressed();
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

                // если пустых полей нет, то открывается диалог с потверждение закрытия окна
                if (!allFieldsEmpty()) {
                    openDialog();
                }
                // если ни одно из полей не заполнено, то окно закрывается без открытия диалога
                else finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // открытие подтверждающего диалога перед закрытием окна
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(getString(R.string.confirm_exit))
                .setMessage(getString(R.string.cancel_creating_resume))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> finish())
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // если все поля не тронуты(ни одно из них не заполнено), то возвращает true
    private boolean allFieldsEmpty() {
        return !stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)
                && description.getText().toString().isEmpty();
    }

    //проверяет входит ли какое-либо значение в какой-либо указанный массив
    // check if selected lesson exists in Constants.Values.LESSONS
    private static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (String item : items) {
            if (inputStr.contains(item)) {
                return true;
            }
        }
        return false;
    }

    // устанавливает в поле LessonPicker выбранный пользователем предмет
    public void setLessonPicker(String lesson) {
        this.lessonPicker.setText(lesson);
    }


    // проверка всех полей на правильность
    // checking every field
    private void attemptCreatingResume() {
        // Reset errors.
        description.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!stringContainsItemFromList(lessonPicker.getText().toString(), Constants.Values.LESSONS)) {
            cancel = true;
        } else if (description.getText().toString().trim().isEmpty()) {
            cancel = true;
            focusView = description;
            description.setError(getString(R.string.error_field_required));
        }

        if (cancel) {
            Toast.makeText(getApplicationContext(), "feel all required fields", Toast.LENGTH_SHORT).show();
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            postResume();
//            Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void postResume() {

        final String text = description.getText().toString().trim();
        final String typeLesson = lessonPicker.getText().toString().trim();

        final DateFormat df = new SimpleDateFormat("d MMM yyyy");
        final String dateStart = df.format(Calendar.getInstance().getTime());
        final String status = "Active";
        final String views = "0";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.ADD_RESUME,
                response -> {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                    ResumeDTO resume = new ResumeDTO();
                    resume.setDateStart(dateStart);
                    resume.setOpportunities(text);
                    resume.setStatus(status);
                    resume.setSubject(typeLesson);
                    resume.setUserAvatar(String.valueOf(SharedPrefManager.getInstance(this).getUserAvatar()));
                    resume.setUserCountry(String.valueOf(SharedPrefManager.getInstance(this).getUserCountry()));
                    resume.setUserEmail(String.valueOf(SharedPrefManager.getInstance(this).getUserEmail()));
                    resume.setUserLogin(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
                    resume.setUserName(String.valueOf(SharedPrefManager.getInstance(this).getUserName()));
                    resume.setViews(views);
//                    Completable.fromRunnable(()->{

//                        resumeViewModel.insert(resume);
//                    })
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe();

                    finish();
                },
                error -> Toast.makeText(getApplicationContext(), "Please on Internet", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("opportunities", text);
                params.put("dateStart", dateStart);
                params.put("users", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserLogin()));
                params.put("subject", typeLesson);
                params.put("status", "Active");
                params.put("views", views);
                return params;
            }

        };
        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}
